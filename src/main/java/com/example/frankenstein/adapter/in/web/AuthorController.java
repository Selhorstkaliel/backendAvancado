package com.example.frankenstein.adapter.in.web;

import com.example.frankenstein.application.port.in.AuthorUseCase;
import com.example.frankenstein.dto.AuthorRequest;
import com.example.frankenstein.dto.AuthorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/authors", "/api/v1/authors"})
public class AuthorController {

    private final AuthorUseCase authorUseCase;

    public AuthorController(AuthorUseCase authorUseCase) {
        this.authorUseCase = authorUseCase;
    }

    @GetMapping
    public List<AuthorResponse> listAll() {
        return authorUseCase.listAll();
    }

    @PostMapping
    public AuthorResponse save(@RequestBody AuthorRequest request) {
        return authorUseCase.create(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
