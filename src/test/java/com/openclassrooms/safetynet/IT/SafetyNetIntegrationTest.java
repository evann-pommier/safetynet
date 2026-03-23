package com.openclassrooms.safetynet.IT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.SafetyNetData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests d'intégration de l'application SafetyNet.
 * Charge le contexte Spring complet et teste les flux de bout en bout.
 * Les données du fichier data.json sont sauvegardées avant les tests
 * et restaurées automatiquement après.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SafetyNetIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SafetyNetData originalData;

    /**
     * Sauvegarde l'état original du fichier data.json avant tous les tests.
     */
    @BeforeAll
    void backupData() throws Exception {
        File file = ResourceUtils.getFile("classpath:data.json");
        originalData = objectMapper.readValue(file, SafetyNetData.class);
    }

    /**
     * Restaure le fichier data.json dans son état original après tous les tests.
     */
    @AfterAll
    void restoreData() throws Exception {
        File file = ResourceUtils.getFile("classpath:data.json");
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, originalData);
    }

    // ============================================================
    // TESTS DES ENDPOINTS D'ALERTE
    // ============================================================

    @Test
    @Order(1)
    void firestation_shouldReturnPersonsAndCounts() throws Exception {
        mockMvc.perform(get("/firestation").param("stationNumber", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.persons", not(empty())))
                .andExpect(jsonPath("$.adultCount", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.childCount", greaterThanOrEqualTo(0)));
    }

    @Test
    @Order(2)
    void childAlert_shouldReturnChildren() throws Exception {
        mockMvc.perform(get("/childAlert").param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].firstName", notNullValue()))
                .andExpect(jsonPath("$[0].age", lessThanOrEqualTo(18)));
    }

    @Test
    @Order(3)
    void childAlert_shouldReturnEmpty_whenNoChildren() throws Exception {
        mockMvc.perform(get("/childAlert").param("address", "29 15th St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    @Order(4)
    void phoneAlert_shouldReturnPhones() throws Exception {
        mockMvc.perform(get("/phoneAlert").param("firestation", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    @Order(5)
    void fire_shouldReturnResidentsWithMedicalInfo() throws Exception {
        mockMvc.perform(get("/fire").param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].firstName", notNullValue()))
                .andExpect(jsonPath("$[0].phone", notNullValue()))
                .andExpect(jsonPath("$[0].age", greaterThan(0)))
                .andExpect(jsonPath("$[0].stations", not(empty())));
    }

    @Test
    @Order(6)
    void flood_shouldReturnHouseholdsGroupedByAddress() throws Exception {
        mockMvc.perform(get("/flood/stations").param("stations", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.households", notNullValue()));
    }

    @Test
    @Order(7)
    void personInfo_shouldReturnPersonDetails() throws Exception {
        mockMvc.perform(get("/personInfo").param("lastName", "Boyd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].lastName", equalToIgnoringCase("Boyd")))
                .andExpect(jsonPath("$[0].email", notNullValue()));
    }

    @Test
    @Order(8)
    void communityEmail_shouldReturnEmails() throws Exception {
        mockMvc.perform(get("/communityEmail").param("city", "Culver"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    // ============================================================
    // TESTS CRUD PERSON
    // ============================================================

    @Test
    @Order(10)
    void person_shouldAddUpdateAndDelete() throws Exception {
        Person person = new Person("Integration", "Test",
                "123 Test St", "Culver", "97451", "000-000-0000", "integration@test.com");

        // POST — ajout
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Person added"));

        // POST — doublon
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isConflict());

        // PUT — mise à jour
        Person updated = new Person("Integration", "Test",
                "999 Updated St", "Culver", "97451", "111-111-1111", "updated@test.com");
        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(content().string("Person updated"));

        // DELETE — suppression
        mockMvc.perform(delete("/person")
                .param("firstName", "Integration")
                .param("lastName", "Test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Person deleted"));

        // DELETE — introuvable
        mockMvc.perform(delete("/person")
                .param("firstName", "Integration")
                .param("lastName", "Test"))
                .andExpect(status().isNotFound());
    }

    // ============================================================
    // TESTS CRUD FIRESTATION
    // ============================================================

    @Test
    @Order(20)
    void firestation_shouldAddUpdateAndDelete() throws Exception {
        Firestation firestation = new Firestation("999 Integration St", "9");

        // POST — ajout
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firestation)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Mapping added"));

        // POST — doublon
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firestation)))
                .andExpect(status().isConflict());

        // PUT — mise à jour
        mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firestation)))
                .andExpect(status().isOk())
                .andExpect(content().string("Mapping updated"));

        // DELETE — suppression
        mockMvc.perform(delete("/firestation")
                .param("address", "999 Integration St")
                .param("station", "9"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mapping deleted"));

        // DELETE — introuvable
        mockMvc.perform(delete("/firestation")
                .param("address", "999 Integration St")
                .param("station", "9"))
                .andExpect(status().isNotFound());
    }

    // ============================================================
    // TESTS CRUD MEDICAL RECORD
    // ============================================================

    @Test
    @Order(30)
    void medicalRecord_shouldAddUpdateAndDelete() throws Exception {
        MedicalRecord record = new MedicalRecord("Integration", "Test",
                "01/01/1990", List.of("aznol:350mg"), List.of("peanut"));

        // POST — ajout
        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Medical record added"));

        // PUT — mise à jour
        MedicalRecord updated = new MedicalRecord("Integration", "Test",
                "01/01/1990", List.of("newMed:100mg"), List.of());
        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(content().string("Medical record updated"));

        // DELETE — suppression
        mockMvc.perform(delete("/medicalRecord")
                .param("firstName", "Integration")
                .param("lastName", "Test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Medical record deleted"));

        // DELETE — introuvable
        mockMvc.perform(delete("/medicalRecord")
                .param("firstName", "Integration")
                .param("lastName", "Test"))
                .andExpect(status().isNotFound());
    }

    // ============================================================
    // TESTS CAS D'ERREUR
    // ============================================================

    @Test
    @Order(40)
    void person_shouldReturn404_whenUpdatingUnknownPerson() throws Exception {
        Person unknown = new Person("Unknown", "Person",
                "123 St", "Culver", "97451", "000-000-0000", "unknown@test.com");

        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unknown)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(41)
    void firestation_shouldReturn404_whenDeletingUnknownMapping() throws Exception {
        mockMvc.perform(delete("/firestation")
                .param("address", "Unknown St")
                .param("station", "99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(42)
    void medicalRecord_shouldReturn404_whenUpdatingUnknownRecord() throws Exception {
        MedicalRecord unknown = new MedicalRecord("Unknown", "Person",
                "01/01/2000", List.of(), List.of());

        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unknown)))
                .andExpect(status().isNotFound());
    }
}