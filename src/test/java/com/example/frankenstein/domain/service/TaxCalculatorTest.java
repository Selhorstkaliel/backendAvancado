package com.example.frankenstein.domain.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaxCalculatorTest {

    private final IncomeTaxCalculator incomeTaxCalculator = new IncomeTaxCalculator();

    @Test
    void shouldApplyHigherTaxRateWhenIncomeGreaterThan50k() {
        assertEquals(51000.0, incomeTaxCalculator.applyBusinessRule(60000.0));
    }

    @Test
    void shouldApplyLowerTaxRateWhenIncomeLessOrEqual50k() {
        assertEquals(46500.0, incomeTaxCalculator.applyBusinessRule(50000.0));
    }
}
