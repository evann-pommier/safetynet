package com.openclassrooms.safetynet.model;

import jakarta.validation.constraints.NotNull;

public record Person(
		@NotNull(message = "Le prenom ne peut pas etre nul") String firstName, 
		@NotNull String lastName, 
		@NotNull String address, 
		@NotNull String city, 
		@NotNull String zip, 
		@NotNull String phone, 
		@NotNull String email
		
		) {}