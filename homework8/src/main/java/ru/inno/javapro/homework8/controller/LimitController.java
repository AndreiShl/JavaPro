package ru.inno.javapro.homework8.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.inno.javapro.homework8.service.LimitService;

@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/limit")
public class LimitController {
    private final LimitService limitService;

    @GetMapping("/get")
    public Double getLimit(@RequestParam("userId") Integer UserId) {
        return limitService.getLimit(UserId);
    }

    @GetMapping("/increase")
    public Double increaseLimit(
            @RequestParam("userId") Integer UserId,
            @RequestParam("value") Double value) {
        return limitService.increaseLimit(UserId, value);
    }

    @GetMapping("/decrease")
    public Double decreaseLimit(
            @RequestParam("userId") Integer UserId,
            @RequestParam("value") Double value) {
        return limitService.decreaseLimit(UserId, value);
    }
}
