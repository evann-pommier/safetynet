package com.openclassrooms.safetynet.controller;

import static org.mockito.ArgumentMatchers.anyInt;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.openclassrooms.safetynet.record.FirestationResponse;
import com.openclassrooms.safetynet.record.PersonResponse;
import com.openclassrooms.safetynet.service.FirestationService;

@WebMvcTest(FirestationController.class)
class FirestationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FirestationService firestationService;

	@Test
	void testGetPersonsByStation() throws Exception {
		// Mock du service
		FirestationResponse response = new FirestationResponse(
				List.of(new PersonResponse("John", "Boyd", "1509 Culver St", "841-874-6512")), 1, 0);
		Mockito.when(firestationService.getPersonsByStation(anyInt())).thenReturn(response);

		mockMvc.perform(get("/firestation").param("stationNumber", "1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.persons[0].firstName").value("John"))
				.andExpect(jsonPath("$.adultCount").value(1));
	}
}