package ru.polskiy.dto;

/**
 * Represents a Token Response DTO containing a token string.
 * This DTO is used to encapsulate authentication tokens in response payloads.
 *
 * @param token The authentication token.
 */
public record TokenResponse (String token){
}
