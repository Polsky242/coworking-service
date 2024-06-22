package ru.polskiy.exception;

public class AuthorizeException extends RuntimeException {

    public AuthorizeException(String message) {
        super("Ошибка авторизации.\n"+message);
    }
}
