package com.example.frankenstein.application.port.out;

import com.example.frankenstein.model.Author;

import java.util.List;

public interface AuthorPersistencePort {
    List<Author> findAllWithBooks();

    Author save(Author author);

    void deleteById(Long id);
}
