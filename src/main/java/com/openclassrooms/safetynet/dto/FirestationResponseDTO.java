package com.openclassrooms.safetynet.dto;

import lombok.Data;
import java.util.List;

@Data
public class FirestationResponseDTO {
    private List<PersonDTO> persons;
    private int adultCount;
    private int childCount;
}