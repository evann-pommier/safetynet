package com.openclassrooms.safetynet.record;

import java.util.List;

public record FirestationResponse(List<PersonResponse> persons, int adultCount, int childCount) {
}