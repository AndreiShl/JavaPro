package ru.inno.javapro.homework8.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import ru.inno.javapro.homework8.exception.NotEnoughLimitException;
import ru.inno.javapro.homework8.exception.ProcessPaymentException;
import ru.inno.javapro.homework8.exception.TooBigClientId;
import ru.inno.javapro.homework8.exception.UncorrectProductException;
import ru.inno.javapro.homework8.model.PaymentLimit;
import ru.inno.javapro.homework8.repository.LimitRepository;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimitPaymentService {

    @Value("${payment-limit.default-limit}")
    private Double defaultLimit;

    @Value("${payment-limit.max-user-id}")
    private Integer maxUserId;

    private final LimitRepository limitRepository;
    private final RestClient restClient;

    /**
     * Лимит изменяется в транзакции с уровнем изоляции REPEATABLE_READ
     * При возникновении ошибки при проведении платежа транзакция будет отменена
     * значение лимита будет восстановлено
     *
     * @param userId    идентификатор пользователя продукта
     * @param productId идентификатор продукта
     * @param amount    сумма платежа
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = ProcessPaymentException.class)
    public void processLimitPayment(Integer userId, Integer productId, Double amount) {
        if (userId > maxUserId) {
            throw new TooBigClientId(String.format("user id %s is too big, max userId is %s", userId, maxUserId));
        }
        PaymentLimit userLimit = limitRepository.findById(userId).orElseGet(() -> createUserLimit(userId));
        log.info("Limit for user {} is {}", userId, userLimit.getLimit());
        if (userLimit.getLimit() < amount) {
            throw new NotEnoughLimitException(String.format("limit for user %s is %s, not enough for payment %s",
                    userId, userLimit.getLimit(), amount));
        }
        Double newLimit = userLimit.getLimit() - amount;
        log.info("New limit for user {} is {}", userId, newLimit);
        userLimit.setLimit(newLimit);
        limitRepository.save(userLimit);
        log.info(processPayment(productId, amount));
    }

    @Scheduled(cron = "${payment-limit.reset-limits}")
    public void resetAllLimit() {
        log.info("Reset all limits");
        limitRepository.setAllLimit(defaultLimit);
    }

    private String processPayment(Integer productId, Double amount) {
        return
                restClient
                        .get()
                        .uri("/payment/process",
                                uriBuilder -> uriBuilder
                                        .queryParam("productId", productId)
                                        .queryParam("amount", amount)
                                        .build())
                        .retrieve()
                        .onStatus(httpStatusCode -> httpStatusCode.value() == 404, (req, res) -> {
                            throw new NoSuchElementException(new String(res.getBody().readAllBytes()));
                        })
                        .onStatus(httpStatusCode -> httpStatusCode.value() == 400, (req, res) -> {
                            throw new UncorrectProductException(new String(res.getBody().readAllBytes()));
                        })
                        .body(String.class);
    }

    private PaymentLimit createUserLimit(Integer userId) {
        return limitRepository.save(new PaymentLimit(userId, defaultLimit));
    }
}
