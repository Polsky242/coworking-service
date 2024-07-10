package ru.polskiy.exception;

/**
 * Custom exception indicating validation parameters error.
 * This exception is thrown to indicate that validation parameters failed during an operation.
 */
public class ValidationParametersException extends RuntimeException {

    /**
     * Constructs a new ValidationParametersException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public ValidationParametersException(String message) {
        super(message);
    }
}
