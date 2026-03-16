package com.openclassrooms.safetynet.record;

import java.util.List;

public record FireResponse(String firstName, String lastName, String phone, int age, List<String> medications,
		List<String> allergies, List<String> stations) {

}
