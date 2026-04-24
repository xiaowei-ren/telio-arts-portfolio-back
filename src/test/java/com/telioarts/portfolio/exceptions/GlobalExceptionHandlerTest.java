package com.telioarts.portfolio.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
   public void shouldReturn404WhenResourceNotFound() throws Exception {
        mockMvc.perform(get("/test/not-found")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Projet introuvable"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void shouldReturn400WhenReservationIsInvalid() throws Exception {
        mockMvc.perform(get("/test/invalid-res")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Créneau déjà pris"));
    }

    @Test
    public void shouldReturn500OnGenericException() throws Exception {
        mockMvc.perform(get("/api/non-existent-path-that-causes-error")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists());
            }


}