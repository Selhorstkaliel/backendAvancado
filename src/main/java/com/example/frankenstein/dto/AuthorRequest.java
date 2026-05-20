package com.example.frankenstein.dto;

import java.util.List;

public record AuthorRequest(
    String name,
    String cpf,
    Double annualIncome,
    List<BookRequest> books
) {}
