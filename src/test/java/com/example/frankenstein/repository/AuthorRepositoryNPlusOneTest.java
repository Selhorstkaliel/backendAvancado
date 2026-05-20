package com.example.frankenstein.repository;

import com.example.frankenstein.adapter.out.persistence.AuthorJpaRepository;
import com.example.frankenstein.model.Author;
import com.example.frankenstein.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class AuthorRepositoryNPlusOneTest {

    @Autowired
    private AuthorJpaRepository authorRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        if (authorRepository.count() > 0) {
            return;
        }

        for (int i = 1; i <= 50; i++) {
            Author author = new Author();
            author.setName("Autor " + i);
            author.setCpf(String.format("%011d", i));
            author.setAnnualIncome(30000d + i);
            java.util.List<Book> books = new java.util.ArrayList<>();
            for (int j = 1; j <= 3; j++) {
                Book book = new Book();
                book.setTitle("Livro " + i + "-" + j);
                book.setAuthor(author);
                books.add(book);
            }
            author.setBooks(books);
            authorRepository.save(author);
        }

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void shouldLoadAuthorsAndBooksWithSingleQueryUsingFetchJoin() {
        Statistics statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        statistics.clear();
        entityManager.clear();

        var authors = authorRepository.findAllWithBooks();

        assertEquals(50, authors.size());
        assertEquals(1, statistics.getPrepareStatementCount());
    }

    @Test
    void shouldShowNPlusOneBehaviorWhenUsingDefaultFindAllAndTraversingBooks() {
        Statistics statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        statistics.clear();
        entityManager.clear();

        var authors = authorRepository.findAll();
        authors.forEach(a -> a.getBooks().size());

        assertTrue(statistics.getPrepareStatementCount() > 1);
    }
}
