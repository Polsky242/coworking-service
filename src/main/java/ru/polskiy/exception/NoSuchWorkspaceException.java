package ru.polskiy.exception;

/**
 * Exception thrown to indicate that a requested workspace does not exist.
 * Extends RuntimeException to ensure it can be thrown without explicit handling.
 */
public class NoSuchWorkspaceException extends RuntimeException {

    /**
     * Constructs a NoSuchWorkspaceException with a specific error message.
     *
     * @param message The message indicating the identifier or details of the non-existent workspace.
     */
    public NoSuchWorkspaceException(String message) {
        super("Workspace " + message + " doesn't exist");
    }
}

