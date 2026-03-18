package com.openclassrooms.safetynet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicalRecordController.class)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MedicalRecordService service;

    private MedicalRecord john = new MedicalRecord("John", "Boyd", "03/06/1984",
            List.of("aznol:350mg"), List.of("nillacilan"));

    @Test
    void getAll_shouldReturn200() throws Exception {
        when(service.getAllMedicalRecords()).thenReturn(List.of(john));

        mockMvc.perform(get("/medicalRecord"))
               .andExpect(status().isOk());
    }

    @Test
    void add_shouldReturn201() throws Exception {
        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(john)))
               .andExpect(status().isCreated());
    }

    @Test
    void update_shouldReturn200_whenUpdated() throws Exception {
        when(service.updateMedicalRecord(any())).thenReturn(true);

        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(john)))
               .andExpect(status().isOk());
    }

    @Test
    void update_shouldReturn404_whenNotFound() throws Exception {
        when(service.updateMedicalRecord(any())).thenReturn(false);

        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(john)))
               .andExpect(status().isNotFound());
    }

    @Test
    void delete_shouldReturn200_whenDeleted() throws Exception {
        when(service.deleteMedicalRecord("John", "Boyd")).thenReturn(true);

        mockMvc.perform(delete("/medicalRecord")
                .param("firstName", "John")
                .param("lastName", "Boyd"))
               .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturn404_whenNotFound() throws Exception {
        when(service.deleteMedicalRecord("Unknown", "Person")).thenReturn(false);

        mockMvc.perform(delete("/medicalRecord")
                .param("firstName", "Unknown")
                .param("lastName", "Person"))
               .andExpect(status().isNotFound());
    }
}