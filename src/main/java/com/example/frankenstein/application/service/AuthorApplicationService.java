package com.example.frankenstein.application.service;

import com.example.frankenstein.application.port.in.AuthorUseCase;
import com.example.frankenstein.application.port.out.AuthorPersistencePort;
import com.example.frankenstein.domain.service.CpfValidator;
import com.example.frankenstein.domain.service.IncomeTaxCalculator;
import com.example.frankenstein.dto.AuthorRequest;
import com.example.frankenstein.dto.AuthorResponse;
import com.example.frankenstein.dto.BookRequest;
import com.example.frankenstein.dto.BookResponse;
import com.example.frankenstein.model.Author;
import com.example.frankenstein.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorApplicationService implements AuthorUseCase {

    private final AuthorPersistencePort authorPersistencePort;
    private final CpfValidator cpfValidator;
    private final IncomeTaxCalculator incomeTaxCalculator;

    public AuthorApplicationService(AuthorPersistencePort authorPersistencePort,
                                    CpfValidator cpfValidator,
                                    IncomeTaxCalculator incomeTaxCalculator) {
        this.authorPersistencePort = authorPersistencePort;
        this.cpfValidator = cpfValidator;
        this.incomeTaxCalculator = incomeTaxCalculator;
    }

    @Override
    public List<AuthorResponse> listAll() {
        return authorPersistencePort.findAllWithBooks()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public AuthorResponse create(AuthorRequest request) {
        cpfValidator.validate(request.cpf());

        Author author = new Author();
        author.setName(request.name());
        author.setCpf(request.cpf());
        author.setAnnualIncome(incomeTaxCalculator.applyBusinessRule(request.annualIncome()));

        if (request.books() != null) {
            List<Book> bookList = request.books().stream().map(bookRequest -> {
                Book book = new Book();
                book.setTitle(bookRequest.title());
                book.setAuthor(author);
                return book;
            }).toList();
            author.setBooks(bookList);
        }

        Author saved = authorPersistencePort.save(author);
        return toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        authorPersistencePort.deleteById(id);
    }

    private AuthorResponse toResponse(Author author) {
        List<BookResponse> books = author.getBooks()
                .stream()
                .map(book -> new BookResponse(book.getId(), book.getTitle()))
                .toList();

        return new AuthorResponse(
                author.getId(),
                author.getName(),
                author.getCpf(),
                author.getAnnualIncome(),
                books
        );
    }
}
