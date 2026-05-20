package com.example.frankenstein.adapter.out.persistence;

import com.example.frankenstein.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<Book, Long> {
}
