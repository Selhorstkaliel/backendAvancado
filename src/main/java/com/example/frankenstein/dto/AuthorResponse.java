package com.example.frankenstein.dto;

import java.util.List;

public record AuthorResponse(
    Long id,
    String name,
    String cpf,
    Double annualIncome,
    List<BookResponse> books
) {}
