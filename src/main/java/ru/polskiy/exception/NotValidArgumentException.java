package ru.polskiy.exception;

public class NotValidArgumentException extends RuntimeException {

    public NotValidArgumentException(String message) {
        super("Не корректно введены данные.\n"+message);
    }
}
