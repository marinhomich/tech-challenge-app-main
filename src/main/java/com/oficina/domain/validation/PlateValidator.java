package com.oficina.domain.validation;

import java.util.regex.Pattern;

public class PlateValidator {

    private static final Pattern OLD_PLATE_PATTERN = Pattern.compile("^[A-Z]{3}-?[0-9]{4}$");
    private static final Pattern NEW_PLATE_PATTERN = Pattern.compile("^[A-Z]{3}-?[0-9][A-Z][0-9]{2}$");

    public static boolean isValidPlate(String plate) {
        if (plate == null) return false;
        String cleaned = plate.toUpperCase().trim();
        return OLD_PLATE_PATTERN.matcher(cleaned).matches() || 
               NEW_PLATE_PATTERN.matcher(cleaned).matches();
    }
}
