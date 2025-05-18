package ru.inno.javapro.paycore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.inno.javapro.paycore.exception.NotEnoughBalance;
import ru.inno.javapro.paycore.model.dto.ProductDto;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final ProductService productService;

    public String processPayment(Integer productId, Double amount) {
        ProductDto product = productService.getProduct(productId);
        if (product.getBalance() < amount) {
            throw new NotEnoughBalance(String.format("Недостаточно средств, продукт [%s] баланс [%s]",
                    product.getAccNumber(), product.getBalance()));
        }
        // логика исполнения платежа
        return String.format("баланс до операции %s, платеж принят", product.getBalance());
    }
}
