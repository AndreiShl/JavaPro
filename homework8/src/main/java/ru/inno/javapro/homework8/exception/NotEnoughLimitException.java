package ru.inno.javapro.homework8.exception;

public class NotEnoughLimitException extends RuntimeException {
    public NotEnoughLimitException(String message) {
        super(message);
    }
}
