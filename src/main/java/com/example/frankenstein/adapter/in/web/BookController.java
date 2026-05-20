package com.example.frankenstein.adapter.in.web;

import com.example.frankenstein.application.port.in.BookUseCase;
import com.example.frankenstein.dto.BookResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookUseCase bookUseCase;

    public BookController(BookUseCase bookUseCase) {
        this.bookUseCase = bookUseCase;
    }

    @GetMapping
    public List<BookResponse> listAll() {
        // O front Angular consome exatamente esta rota para montar a tela de livros.
        return bookUseCase.listAll();
    }
}
