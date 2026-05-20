package com.example.frankenstein.application.service;

import com.example.frankenstein.application.port.in.BookUseCase;
import com.example.frankenstein.application.port.out.BookPersistencePort;
import com.example.frankenstein.dto.BookResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookApplicationService implements BookUseCase {

    private final BookPersistencePort bookPersistencePort;

    public BookApplicationService(BookPersistencePort bookPersistencePort) {
        this.bookPersistencePort = bookPersistencePort;
    }

    @Override
    public List<BookResponse> listAll() {
        // A aplicação devolve só os campos usados pelo front, sem expor a entidade JPA inteira.
        return bookPersistencePort.findAll()
            .stream()
            .map(book -> new BookResponse(book.getId(), book.getTitle()))
            .toList();
    }
}
