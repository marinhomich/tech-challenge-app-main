package com.oficina.domain.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentValidatorTest {

    @Test
    public void testValidCPF() {
        assertTrue(DocumentValidator.isValidCPF("11144477735"));
        assertTrue(DocumentValidator.isValidCPF("111.444.777-35"));
    }

    @Test
    public void testInvalidCPF() {
        assertFalse(DocumentValidator.isValidCPF("12345678901"));
        assertFalse(DocumentValidator.isValidCPF("00000000000"));
        assertFalse(DocumentValidator.isValidCPF("11111111111"));
        assertFalse(DocumentValidator.isValidCPF(null));
        assertFalse(DocumentValidator.isValidCPF("123"));
    }

    @Test
    public void testValidCNPJ() {
        assertTrue(DocumentValidator.isValidCNPJ("11222333000181"));
        assertTrue(DocumentValidator.isValidCNPJ("11.222.333/0001-81"));
    }

    @Test
    public void testInvalidCNPJ() {
        assertFalse(DocumentValidator.isValidCNPJ("12345678901234"));
        assertFalse(DocumentValidator.isValidCNPJ("00000000000000"));
        assertFalse(DocumentValidator.isValidCNPJ("11111111111111"));
        assertFalse(DocumentValidator.isValidCNPJ(null));
        assertFalse(DocumentValidator.isValidCNPJ("123"));
    }

    @Test
    public void testValidDocument() {
        assertTrue(DocumentValidator.isValidDocument("11144477735"));
        assertTrue(DocumentValidator.isValidDocument("11222333000181"));
        assertFalse(DocumentValidator.isValidDocument("12345"));
        assertFalse(DocumentValidator.isValidDocument(null));
    }
}
