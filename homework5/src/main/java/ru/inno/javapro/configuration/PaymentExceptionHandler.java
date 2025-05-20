package ru.inno.javapro.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.inno.javapro.exception.ProductNotFoundException;
import ru.inno.javapro.model.dto.ErrorInfo;

@RestControllerAdvice
public class PaymentExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfo productNotFound(ProductNotFoundException e) {
        return new ErrorInfo("productNotFound", e.getMessage());
    }
}
