package com.openclassrooms.safetynet.record;

import java.util.List;

public record PersonInfoResponse(String firstName, String lastName, String address, int age, String email,
		List<String> medications, List<String> allergies) {

}
