package com.example.frankenstein.domain.service;

import org.springframework.stereotype.Component;

@Component
public class CpfValidator {

    public void validate(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            throw new RuntimeException("CPF Inválido!");
        }
    }
}
