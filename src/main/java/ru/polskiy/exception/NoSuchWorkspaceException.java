package ru.polskiy.exception;

public class NoSuchWorkspaceException extends RuntimeException {

    public NoSuchWorkspaceException(String message) {
        super("Workspace "+message+" doesn't exist");
    }
}
