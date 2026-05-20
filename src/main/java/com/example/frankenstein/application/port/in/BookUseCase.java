package com.example.frankenstein.application.port.in;

import com.example.frankenstein.dto.BookResponse;

import java.util.List;

public interface BookUseCase {
    List<BookResponse> listAll();
}
