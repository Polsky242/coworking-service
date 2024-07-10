package ru.polskiy.dto;

/**
 * Represents a success response DTO containing a message.
 * This DTO is used to encapsulate success messages for response payloads.
 *
 * @param message The success message.
 */
public record SuccessResponse(String message) {
}
