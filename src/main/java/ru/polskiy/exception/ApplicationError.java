package ru.polskiy.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ApplicationError {
    /**
     * The HTTP status code of the error.
     */
    private int status;

    /**
     * The error message describing the issue.
     */
    private String message;

    /**
     * The timestamp when the error occurred.
     * Defaults to the current date and time if not explicitly set.
     */
    @Builder.Default
    private Date timestamp = new Date();
}
