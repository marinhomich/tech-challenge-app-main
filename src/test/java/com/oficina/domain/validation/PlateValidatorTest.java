package com.oficina.domain.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlateValidatorTest {

    @Test
    public void testValidOldPlate() {
        assertTrue(PlateValidator.isValidPlate("ABC-1234"));
        assertTrue(PlateValidator.isValidPlate("ABC1234"));
        assertTrue(PlateValidator.isValidPlate("XYZ-9999"));
    }

    @Test
    public void testValidNewPlate() {
        assertTrue(PlateValidator.isValidPlate("ABC-1D23"));
        assertTrue(PlateValidator.isValidPlate("ABC1D23"));
        assertTrue(PlateValidator.isValidPlate("XYZ-2E45"));
    }

    @Test
    public void testInvalidPlate() {
        assertFalse(PlateValidator.isValidPlate("AB-1234"));
        assertFalse(PlateValidator.isValidPlate("ABCD-1234"));
        assertFalse(PlateValidator.isValidPlate("123-4567"));
        assertFalse(PlateValidator.isValidPlate(null));
        assertFalse(PlateValidator.isValidPlate(""));
        assertFalse(PlateValidator.isValidPlate("ABC"));
    }
}
