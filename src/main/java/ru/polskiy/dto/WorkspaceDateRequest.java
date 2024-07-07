package ru.polskiy.dto;

/**
 * Represents a request DTO containing day, month, and year for workspace date information.
 * This DTO is used to encapsulate date details related to workspace operations.
 *
 * @param day   The day of the date.
 * @param month The month of the date.
 * @param year  The year of the date.
 */
public record WorkspaceDateRequest(
        int day,
        int month,
        int year
) {
}