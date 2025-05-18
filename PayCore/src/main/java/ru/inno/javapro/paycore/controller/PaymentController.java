package ru.inno.javapro.paycore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.inno.javapro.paycore.model.dto.ProductDto;
import ru.inno.javapro.paycore.service.PaymentService;
import ru.inno.javapro.paycore.service.ProductService;

import java.util.List;

@Slf4j
@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payment")
public class PaymentController {
    private final ProductService productService;
    private final PaymentService paymentService;

    @GetMapping("/client/product")
    public List<ProductDto> getClientProduct(@RequestParam("userId") Integer clientId) {
        log.info("getClientProduct {}", productService.getUserProducts(clientId));
        return productService.getUserProducts(clientId);
    }

    @GetMapping("/process")
    public String processPayment(
            @RequestParam("productId") Integer productId,
            @RequestParam("amount") Double amount) {
        return paymentService.processPayment(productId, amount);
    }
}
