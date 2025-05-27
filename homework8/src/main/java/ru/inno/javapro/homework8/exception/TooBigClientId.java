package ru.inno.javapro.homework8.exception;

public class TooBigClientId extends RuntimeException{
    public TooBigClientId(String message) {
        super(message);
    }
}
