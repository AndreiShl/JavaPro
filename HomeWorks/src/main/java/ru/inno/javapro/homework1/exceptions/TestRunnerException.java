package ru.inno.javapro.homework1.exceptions;

public class TestRunnerException extends RuntimeException{
    public TestRunnerException(Throwable cause) {
        super(cause);
    }

    public TestRunnerException(String message) {
        super(message);
    }
}
