package ru.inno.javapro.paycore.exception;

public class NotEnoughBalance extends RuntimeException {
    public NotEnoughBalance(String message) {
        super(message);
    }
}
