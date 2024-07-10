package ru.polskiy.dto;

/**
 * Represents an Exception Response DTO containing a message.
 * This DTO is used to encapsulate error messages for API responses.
 *
 * @param message The message describing the exception response.
 */
public record ExceptionResponse(String message) {
}
