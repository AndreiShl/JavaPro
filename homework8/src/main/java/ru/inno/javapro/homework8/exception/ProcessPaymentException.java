package ru.inno.javapro.homework8.exception;

public class ProcessPaymentException extends RuntimeException {
    public ProcessPaymentException(String message) {
        super(message);
    }
}
