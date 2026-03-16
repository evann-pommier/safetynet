package com.openclassrooms.safetynet.record;

import java.util.List;

public record ChildAlertResponse(String firstName, String lastName, int age, List<PersonResponse> householdMembers) {
}