package ru.inno.javapro.homework8.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.inno.javapro.homework8.exception.ProcessPaymentException;
import ru.inno.javapro.homework8.exception.TooBigClientId;
import ru.inno.javapro.homework8.model.dto.ErrorInfo;

@RestControllerAdvice
public class PaymentExceptionHandler {

    /**
     * Обработка исключений выполнения платежа
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProcessPaymentException.class)
    public ErrorInfo processPaymentError(ProcessPaymentException e) {
        return new ErrorInfo("process payment has got error", e.getMessage());
    }

    /**
     * Обработка исключений обработки лимита
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TooBigClientId.class)
    public ErrorInfo processLimitError(TooBigClientId e) {
        return new ErrorInfo("limit payment has got error", e.getMessage());
    }
}
