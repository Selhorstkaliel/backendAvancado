package com.example.frankenstein.application.port.out;

import com.example.frankenstein.model.Book;

import java.util.List;

public interface BookPersistencePort {
    List<Book> findAll();
}
