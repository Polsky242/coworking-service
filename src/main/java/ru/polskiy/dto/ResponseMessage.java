package ru.polskiy.dto;

/**
 * Represents a Response Message DTO containing a message.
 * This DTO is used to encapsulate messages for API responses.
 *
 * @param message The message to be encapsulated in the response.
 */
public record ResponseMessage(String message) {
}
