package com.example.frankenstein.adapter.out.persistence;

import com.example.frankenstein.application.port.out.BookPersistencePort;
import com.example.frankenstein.model.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookPersistenceAdapter implements BookPersistencePort {

    private final BookJpaRepository bookJpaRepository;

    public BookPersistenceAdapter(BookJpaRepository bookJpaRepository) {
        this.bookJpaRepository = bookJpaRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookJpaRepository.findAll();
    }
}
