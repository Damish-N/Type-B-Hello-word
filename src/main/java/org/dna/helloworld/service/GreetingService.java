package org.dna.helloworld.service;

import org.dna.helloworld.exception.InvalidInputException;
import org.springframework.stereotype.Service;

/**
 * Business rules for building a greeting.
 */
@Service
public class GreetingService {

    private static final String GREETING_PREFIX = "Hello ";

    /**
     * @param name the raw name from the request
     * @return a greeting such as "Hello Alice"
     * @throws InvalidInputException if the name is missing, blank, or does not start with A–M
     */
    public String greet(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("Name is missing or blank");
        }

        String trimmedName = name.strip();
        char firstLetter = trimmedName.charAt(0);

        if (!isInFirstHalfOfAlphabet(firstLetter)) {
            throw new InvalidInputException("Name must start with a letter from A to M");
        }

        return GREETING_PREFIX + capitalizeFirstLetter(trimmedName);
    }

    /**
     * should have name starting a-m or A-M
     */
    private static boolean isInFirstHalfOfAlphabet(char c) {
        return (c >= 'A' && c <= 'M') || (c >= 'a' && c <= 'm');
    }


    private static String capitalizeFirstLetter(String value) {
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }
}
