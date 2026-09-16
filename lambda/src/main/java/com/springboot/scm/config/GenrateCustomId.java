package com.springboot.scm.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GenrateCustomId {

	
	private String generateId() {

	    String datePart = LocalDate.now()
	            .format(DateTimeFormatter.ofPattern("yyyy-MMM-dd"))
	            .toUpperCase();

	    String characters =
	            "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	    Random random = new Random();

	    StringBuilder randomPart = new StringBuilder();

	    for (int i = 0; i < 4; i++) {
	        randomPart.append(
	                characters.charAt(
	                        random.nextInt(characters.length())
	                )
	        );
	    }

	    return "ART-" + datePart + "-" + randomPart;
	}
}
