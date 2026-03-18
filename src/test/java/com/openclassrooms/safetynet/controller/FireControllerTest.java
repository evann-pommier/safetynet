package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.record.FireResponse;
import com.openclassrooms.safetynet.service.FireService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FireController.class)
class FireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireService fireService;

    @Test
    void getFireInfo_shouldReturn200() throws Exception {
        when(fireService.getFireInfoByAddress("1509 Culver St"))
            .thenReturn(List.of(new FireResponse("John", "Boyd", "841-874-6512", 40,
                    List.of("aznol:350mg"), List.of("nillacilan"), List.of("3"))));

        mockMvc.perform(get("/fire").param("address", "1509 Culver St"))
               .andExpect(status().isOk());
    }

    @Test
    void getFireInfo_shouldReturnEmptyList_whenNoResidents() throws Exception {
        when(fireService.getFireInfoByAddress("Unknown St"))
            .thenReturn(List.of());

        mockMvc.perform(get("/fire").param("address", "Unknown St"))
               .andExpect(status().isOk())
               .andExpect(content().json("[]"));
    }
}