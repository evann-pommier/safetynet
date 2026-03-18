package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.record.PersonInfoResponse;
import com.openclassrooms.safetynet.service.PersonInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonInfoController.class)
class PersonInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonInfoService service;

    @Test
    void getInfo_shouldReturn200() throws Exception {
        when(service.getByLastName("Boyd"))
            .thenReturn(List.of(new PersonInfoResponse("John", "Boyd",
                    "1509 Culver St", 40, "john@email.com",
                    List.of("aznol:350mg"), List.of("nillacilan"))));

        mockMvc.perform(get("/personInfo").param("lastName", "Boyd"))
               .andExpect(status().isOk());
    }

    @Test
    void getInfo_shouldReturnEmptyList_whenNoMatch() throws Exception {
        when(service.getByLastName("Unknown"))
            .thenReturn(List.of());

        mockMvc.perform(get("/personInfo").param("lastName", "Unknown"))
               .andExpect(status().isOk())
               .andExpect(content().json("[]"));
    }
}