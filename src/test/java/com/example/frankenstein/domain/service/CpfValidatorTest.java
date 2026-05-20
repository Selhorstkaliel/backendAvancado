package com.example.frankenstein.domain.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CpfValidatorTest {

    private final CpfValidator cpfValidator = new CpfValidator();

    @Test
    void shouldAcceptCpfWith11Digits() {
        assertDoesNotThrow(() -> cpfValidator.validate("12345678901"));
        assertDoesNotThrow(() -> cpfValidator.validate("00000000000"));
    }

    @Test
    void shouldRejectInvalidCpf() {
        assertThrows(RuntimeException.class, () -> cpfValidator.validate(null));
        assertThrows(RuntimeException.class, () -> cpfValidator.validate("123"));
    }
}
