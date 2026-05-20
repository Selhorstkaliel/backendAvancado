package com.example.frankenstein.domain.service;

import org.springframework.stereotype.Component;

@Component
public class IncomeTaxCalculator {

    public double applyBusinessRule(Double annualIncome) {
        // A regra de cálculo agora fica separada da aplicação e do HTTP.
        if (annualIncome == null) {
            return 0.0;
        }
        if (annualIncome > 50000) {
            return annualIncome * 0.85;
        }
        return annualIncome * 0.93;
    }
}
