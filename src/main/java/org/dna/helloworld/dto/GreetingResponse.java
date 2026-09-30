package org.dna.helloworld.dto;

/**
 * Successful response body: {@code { "message": "Hello Alice" }}.
 */
public record GreetingResponse(String message) {
}
