package ru.inno.javapro.homework8.exception;

public class NotEnoughLimitException extends ProcessPaymentException {
    public NotEnoughLimitException(String message) {
        super(message);
    }
}
