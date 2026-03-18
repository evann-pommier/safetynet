package com.openclassrooms.safetynet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.record.FirestationResponse;
import com.openclassrooms.safetynet.service.FirestationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FirestationController.class)
class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FirestationService firestationService;

    @Test
    void getPersonsByStation_shouldReturn200() throws Exception {
        when(firestationService.getPersonsByStation(3))
            .thenReturn(new FirestationResponse(List.of(), 0, 0));

        mockMvc.perform(get("/firestation").param("stationNumber", "3"))
               .andExpect(status().isOk());
    }

    @Test
    void addMapping_shouldReturn201_whenAdded() throws Exception {
        Firestation firestation = new Firestation("1509 Culver St", "3");
        when(firestationService.addMapping(any())).thenReturn(true);

        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firestation)))
               .andExpect(status().isCreated());
    }

    @Test
    void addMapping_shouldReturn409_whenAlreadyExists() throws Exception {
        Firestation firestation = new Firestation("1509 Culver St", "3");
        when(firestationService.addMapping(any())).thenReturn(false);

        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firestation)))
               .andExpect(status().isConflict());
    }

    @Test
    void updateMapping_shouldReturn200_whenUpdated() throws Exception {
        Firestation firestation = new Firestation("1509 Culver St", "3");
        when(firestationService.updateMapping(any())).thenReturn(true);

        mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firestation)))
               .andExpect(status().isOk());
    }

    @Test
    void updateMapping_shouldReturn404_whenNotFound() throws Exception {
        Firestation firestation = new Firestation("Unknown St", "9");
        when(firestationService.updateMapping(any())).thenReturn(false);

        mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firestation)))
               .andExpect(status().isNotFound());
    }

    @Test
    void deleteMapping_shouldReturn200_whenDeleted() throws Exception {
        when(firestationService.deleteMapping("1509 Culver St", "3")).thenReturn(true);

        mockMvc.perform(delete("/firestation")
                .param("address", "1509 Culver St")
                .param("station", "3"))
               .andExpect(status().isOk());
    }

    @Test
    void deleteMapping_shouldReturn404_whenNotFound() throws Exception {
        when(firestationService.deleteMapping("Unknown St", "9")).thenReturn(false);

        mockMvc.perform(delete("/firestation")
                .param("address", "Unknown St")
                .param("station", "9"))
               .andExpect(status().isNotFound());
    }
}