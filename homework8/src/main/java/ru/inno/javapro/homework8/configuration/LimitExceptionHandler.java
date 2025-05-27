package ru.inno.javapro.homework8.configuration;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.inno.javapro.homework8.exception.TooBigClientId;
import ru.inno.javapro.homework8.model.dto.ErrorInfo;

import java.util.stream.Collectors;

@RestControllerAdvice
public class LimitExceptionHandler {

    /**
     * Обработка исключений обработки лимита
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TooBigClientId.class)
    public ErrorInfo processLimitError(TooBigClientId e) {
        return new ErrorInfo("limit payment has got error", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorInfo processLimitError(ConstraintViolationException e) {
        return new ErrorInfo("constraint violation",
                e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(";")));
    }


}
