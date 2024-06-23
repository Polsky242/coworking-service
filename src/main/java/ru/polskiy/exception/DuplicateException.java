package ru.polskiy.exception;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String message) {
        super("workspace "+ message+" booked");
    }
}
