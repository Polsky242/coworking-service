package ru.polskiy.exception;

public class RegisterException extends RuntimeException {

    public RegisterException(String message) {
        super("Ошибка регистрации.\n "+message);
    }
}
