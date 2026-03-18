package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.service.PhoneAlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PhoneAlertController.class)
class PhoneAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneAlertService service;

    @Test
    void getPhones_shouldReturn200() throws Exception {
        when(service.getPhonesByStation(3))
            .thenReturn(List.of("841-874-6512"));

        mockMvc.perform(get("/phoneAlert").param("firestation", "3"))
               .andExpect(status().isOk());
    }

    @Test
    void getPhones_shouldReturnEmptyList_whenNoResidents() throws Exception {
        when(service.getPhonesByStation(99))
            .thenReturn(List.of());

        mockMvc.perform(get("/phoneAlert").param("firestation", "99"))
               .andExpect(status().isOk())
               .andExpect(content().json("[]"));
    }
}