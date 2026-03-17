package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.openclassrooms.safetynet.record.ChildAlertResponse;
import com.openclassrooms.safetynet.record.PersonResponse;
import com.openclassrooms.safetynet.service.ChildAlertService;

@WebMvcTest(ChildAlertController.class)
class ChildAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChildAlertService childAlertService;

    @Test
    void shouldReturnChildrenByAddress() throws Exception {

        ChildAlertResponse child = new ChildAlertResponse(
                "Tenley",
                "Boyd",
                11,
                List.of(
                        new PersonResponse("John", "Boyd", "1509 Culver St", "841-874-6512")
                )
        );

        Mockito.when(childAlertService.getChildrenByAddress(anyString()))
                .thenReturn(List.of(child));

        mockMvc.perform(get("/childAlert")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Tenley"))
                .andExpect(jsonPath("$[0].age").value(11))
                .andExpect(jsonPath("$[0].householdMembers[0].firstName").value("John"));
    }
}