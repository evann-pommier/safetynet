package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.record.FloodResponse;
import com.openclassrooms.safetynet.service.FloodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FloodController.class)
class FloodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FloodService floodService;

    @Test
    void getFlood_shouldReturn200() throws Exception {
        when(floodService.getFloodByStations(List.of(3)))
            .thenReturn(new FloodResponse(Map.of()));

        mockMvc.perform(get("/flood/stations").param("stations", "3"))
               .andExpect(status().isOk());
    }

    @Test
    void getFlood_shouldReturn200_withMultipleStations() throws Exception {
        when(floodService.getFloodByStations(List.of(1, 2)))
            .thenReturn(new FloodResponse(Map.of()));

        mockMvc.perform(get("/flood/stations")
                .param("stations", "1", "2"))
               .andExpect(status().isOk());
    }
}