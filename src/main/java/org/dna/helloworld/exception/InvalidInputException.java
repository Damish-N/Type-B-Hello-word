package org.dna.helloworld.exception;

/**
 * Thrown when the supplied name does not satisfy the greeting rules.
 */
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}
