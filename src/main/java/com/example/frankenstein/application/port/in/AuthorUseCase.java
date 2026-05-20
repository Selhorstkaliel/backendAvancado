package com.example.frankenstein.application.port.in;

import com.example.frankenstein.dto.AuthorRequest;
import com.example.frankenstein.dto.AuthorResponse;

import java.util.List;

public interface AuthorUseCase {
    List<AuthorResponse> listAll();

    AuthorResponse create(AuthorRequest request);

    void delete(Long id);
}
