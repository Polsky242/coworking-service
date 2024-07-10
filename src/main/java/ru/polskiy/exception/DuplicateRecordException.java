package ru.polskiy.exception;

/**
 * Custom exception indicating a duplicate record error.
 * This exception is thrown to indicate that an operation tried to create or insert a record
 * that already exists in the system and violates uniqueness constraints.
 */
public class DuplicateRecordException extends RuntimeException {

    /**
     * Constructs a new DuplicateRecordException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public DuplicateRecordException(String message) {
        super(message);
    }
}