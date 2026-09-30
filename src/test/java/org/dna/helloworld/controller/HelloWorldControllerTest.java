package org.dna.helloworld.controller;


import org.dna.helloworld.service.GreetingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloWorldController.class)
@Import(GreetingService.class)
class HelloWorldControllerTest {

    private static final String ENDPOINT = "/hello-world";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returns200WithGreetingForNameInFirstHalf() throws Exception {
        mockMvc.perform(get(ENDPOINT).param("name", "alice"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Hello Alice"))
                .andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    void returns400ForNameInSecondHalf() throws Exception {
        mockMvc.perform(get(ENDPOINT).param("name", "zoe"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Invalid Input"))
                .andExpect(jsonPath("$.message").doesNotExist());
    }

    @Test
    void returns400WhenNameParameterIsMissing() throws Exception {
        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void returns400WhenNameParameterIsEmpty() throws Exception {
        mockMvc.perform(get(ENDPOINT).param("name", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void returns400WhenNameParameterIsBlank() throws Exception {
        mockMvc.perform(get(ENDPOINT).param("name", "   "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void returns400WhenNameStartsWithNonLetter() throws Exception {
        mockMvc.perform(get(ENDPOINT).param("name", "1alice"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }
}
