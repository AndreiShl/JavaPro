package ru.inno.javapro.paycore.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.inno.javapro.paycore.exception.NotEnoughBalance;
import ru.inno.javapro.paycore.model.dto.ErrorInfo;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class PaymentExceptionHandler {

    @ExceptionHandler(NotEnoughBalance.class)
    public ErrorInfo notEnoughBalance(NotEnoughBalance e) {
        return new ErrorInfo("NotEnoughBalance", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorInfo noSuchElementException(NoSuchElementException e) {
        return new ErrorInfo("NoSuchElementException", e.getMessage());
    }
}
