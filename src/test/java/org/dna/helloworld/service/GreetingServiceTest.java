package org.dna.helloworld.service;


import org.dna.helloworld.exception.InvalidInputException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GreetingServiceTest {

    private final GreetingService greetingService = new GreetingService();

    @ParameterizedTest(name = "\"{0}\" -> \"{1}\"")
    @CsvSource({
            "alice,    Hello Alice",
            "Alice,    Hello Alice",
            "bob,      Hello Bob",
            "a,        Hello A",
            "A,        Hello A",
            "m,        Hello M",
            "Mike,     Hello Mike",
            "McDonald, Hello McDonald"
    })
    void greetsNamesStartingWithAToM(String name, String expectedGreeting) {
        assertThat(greetingService.greet(name)).isEqualTo(expectedGreeting);
    }

    @Test
    void trimsSurroundingWhitespaceBeforeValidating() {
        assertThat(greetingService.greet("  alice\t")).isEqualTo("Hello Alice");
    }

    @ParameterizedTest(name = "rejects \"{0}\"")
    @ValueSource(strings = {"n", "N", "nina", "Nina", "zoe", "Z", "  zoe"})
    void rejectsNamesStartingWithNToZ(String name) {
        assertInvalid(name);
    }

    @ParameterizedTest(name = "rejects missing/blank \"{0}\"")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void rejectsMissingOrBlankNames(String name) {
        assertInvalid(name);
    }

    @ParameterizedTest(name = "rejects non A-Z first character \"{0}\"")
    @ValueSource(strings = {
            "1alice",       // digit
            "_alice",       // symbol
            "-bob",         // symbol
            "@",            // symbol only
            "éric",         // accented letter, not English alphabet
            "Ålice",        // accented letter
            "İlker",        // Turkish dotted I lower-cases to ASCII 'i'
            "\u212Aevin",   // Kelvin sign lower-cases to ASCII 'k'
            "😀alice"       // surrogate pair
    })
    void rejectsNamesNotStartingWithAnEnglishLetter(String name) {
        assertInvalid(name);
    }

    private void assertInvalid(String name) {
        assertThatThrownBy(() -> greetingService.greet(name))
                .isInstanceOf(InvalidInputException.class);
    }
}
