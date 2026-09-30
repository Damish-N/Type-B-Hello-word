package org.dna.helloworld.dto;

/**
 * Error response body: {@code { "error": "Invalid Input" }}.
 */
public record ErrorResponse(String error) {
}
