package ru.inno.javapro.homework8.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.inno.javapro.homework8.service.LimitPaymentService;

@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/limitPayment")
public class LimitPaymentController {
    private final LimitPaymentService limitPaymentService;

    @GetMapping("limit-payment")
    public void processLimitPayment(
            @RequestParam("userId") Integer userId,
            @RequestParam("productId") Integer productId,
            @RequestParam("amount") Double amount) {
        limitPaymentService.processLimitPayment(userId, productId, amount);
    }
}
