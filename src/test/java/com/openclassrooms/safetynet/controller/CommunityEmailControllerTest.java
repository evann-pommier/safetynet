package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.service.CommunityEmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommunityEmailController.class)
class CommunityEmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommunityEmailService service;

    @Test
    void getEmails_shouldReturn200() throws Exception {
        when(service.getEmailsByCity("Culver"))
            .thenReturn(List.of("john@email.com", "jacob@email.com"));

        mockMvc.perform(get("/communityEmail").param("city", "Culver"))
               .andExpect(status().isOk());
    }

    @Test
    void getEmails_shouldReturnEmptyList_whenCityNotFound() throws Exception {
        when(service.getEmailsByCity("Unknown"))
            .thenReturn(List.of());

        mockMvc.perform(get("/communityEmail").param("city", "Unknown"))
               .andExpect(status().isOk())
               .andExpect(content().json("[]"));
    }
}