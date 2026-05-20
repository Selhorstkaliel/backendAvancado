package com.example.frankenstein.adapter.out.persistence;

import com.example.frankenstein.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorJpaRepository extends JpaRepository<Author, Long> {
    @Query("select distinct a from Author a left join fetch a.books")
    List<Author> findAllWithBooks();
}
