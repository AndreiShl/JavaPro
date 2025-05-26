package ru.inno.javapro.homework8.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.inno.javapro.homework8.exception.NotEnoughLimitException;
import ru.inno.javapro.homework8.model.Limit;
import ru.inno.javapro.homework8.repository.LimitRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimitService {

    @Value("${payment-limit.default-limit}")
    private Double defaultLimit;
    private final LimitRepository limitRepository;

    @Scheduled(cron = "${payment-limit.reset-limits}")
    public void resetAllLimit() {
        log.info("Reset all limits");
        limitRepository.setAllLimit(defaultLimit);
    }

    public Double getLimit(Integer userId) {
        return
                limitRepository
                        .findFirstByUserId(userId)
                        .orElseGet(() -> createUserLimit(userId))
                        .getLimit();
    }

    public Double increaseLimit(Integer userId, Double value) {
        Limit userLimit = limitRepository.findFirstByUserId(userId).orElseGet(() -> createUserLimit(userId));
        userLimit.setLimit(userLimit.getLimit() + value);
        limitRepository.save(userLimit);
        return userLimit.getLimit();
    }

    public Double decreaseLimit(Integer userId, Double value) {
        Limit userLimit = limitRepository.findFirstByUserId(userId).orElseGet(() -> createUserLimit(userId));
        if (userLimit.getLimit() < value) {
            throw new NotEnoughLimitException(String.format("limit for user %s is %s, not enough for payment %s",
                    userId, userLimit.getLimit(), value));
        }
        userLimit.setLimit(userLimit.getLimit() - value);
        limitRepository.save(userLimit);
        return userLimit.getLimit();
    }

    private Limit createUserLimit(Integer userId) {
        return limitRepository.save(new Limit(userId, defaultLimit));
    }
}
