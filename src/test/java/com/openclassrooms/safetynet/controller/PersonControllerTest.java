package com.openclassrooms.safetynet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;
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

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    private Person john = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@email.com");

    @Test
    void getAllPersons_shouldReturn200() throws Exception {
        when(personService.getAllPersons()).thenReturn(List.of(john));

        mockMvc.perform(get("/person"))
               .andExpect(status().isOk());
    }

    @Test
    void addPerson_shouldReturn201_whenAdded() throws Exception {
        when(personService.addPerson(any())).thenReturn(true);

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(john)))
               .andExpect(status().isCreated());
    }

    @Test
    void addPerson_shouldReturn409_whenAlreadyExists() throws Exception {
        when(personService.addPerson(any())).thenReturn(false);

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(john)))
               .andExpect(status().isConflict());
    }

    @Test
    void updatePerson_shouldReturn200_whenUpdated() throws Exception {
        when(personService.updatePerson(any())).thenReturn(true);

        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(john)))
               .andExpect(status().isOk());
    }

    @Test
    void updatePerson_shouldReturn404_whenNotFound() throws Exception {
        when(personService.updatePerson(any())).thenReturn(false);

        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(john)))
               .andExpect(status().isNotFound());
    }

    @Test
    void deletePerson_shouldReturn200_whenDeleted() throws Exception {
        when(personService.deletePerson("John", "Boyd")).thenReturn(true);

        mockMvc.perform(delete("/person")
                .param("firstName", "John")
                .param("lastName", "Boyd"))
               .andExpect(status().isOk());
    }

    @Test
    void deletePerson_shouldReturn404_whenNotFound() throws Exception {
        when(personService.deletePerson("Unknown", "Person")).thenReturn(false);

        mockMvc.perform(delete("/person")
                .param("firstName", "Unknown")
                .param("lastName", "Person"))
               .andExpect(status().isNotFound());
    }
}