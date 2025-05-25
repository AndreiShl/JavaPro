package ru.inno.javapro.homework8.exception;

public class UncorrectProductException extends ProcessPaymentException{
    public UncorrectProductException(String message) {
        super(message);
    }
}
