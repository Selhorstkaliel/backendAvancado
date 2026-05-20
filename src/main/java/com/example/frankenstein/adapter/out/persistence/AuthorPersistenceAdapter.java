package com.example.frankenstein.adapter.out.persistence;

import com.example.frankenstein.application.port.out.AuthorPersistencePort;
import com.example.frankenstein.model.Author;
import com.example.frankenstein.adapter.out.persistence.AuthorJpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorPersistenceAdapter implements AuthorPersistencePort {

    private final AuthorJpaRepository authorRepository;

    public AuthorPersistenceAdapter(AuthorJpaRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAllWithBooks() {
        return authorRepository.findAllWithBooks();
    }

    @Override
    public Author save(@NonNull Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteById(@NonNull Long id) {
        authorRepository.deleteById(id);
    }
}
