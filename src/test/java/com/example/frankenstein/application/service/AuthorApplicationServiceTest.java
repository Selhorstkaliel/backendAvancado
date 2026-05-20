package com.example.frankenstein.application.service;

import com.example.frankenstein.application.port.out.AuthorPersistencePort;
import com.example.frankenstein.domain.service.CpfValidator;
import com.example.frankenstein.domain.service.IncomeTaxCalculator;
import com.example.frankenstein.dto.AuthorRequest;
import com.example.frankenstein.dto.BookRequest;
import com.example.frankenstein.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorApplicationServiceTest {

    @Mock
    private AuthorPersistencePort authorPersistencePort;

    private AuthorApplicationService authorApplicationService;

    @BeforeEach
    void setUp() {
        authorApplicationService = new AuthorApplicationService(
                authorPersistencePort,
                new CpfValidator(),
                new IncomeTaxCalculator()
        );
    }

    @Test
    void shouldRejectInvalidCpf() {
        AuthorRequest request = new AuthorRequest("A", "123", 1000d, List.of());

        assertThrows(RuntimeException.class, () -> authorApplicationService.create(request));
    }

    @Test
    void shouldApplyTaxRulesBeforeSaving() {
        AuthorRequest request = new AuthorRequest("A", "12345678901", 60000d, List.of(new BookRequest("Livro A")));

        when(authorPersistencePort.save(org.mockito.ArgumentMatchers.any(Author.class)))
                .thenAnswer(invocation -> {
                    Author a = invocation.getArgument(0);
                    a.setId(1L);
                    return a;
                });

        authorApplicationService.create(request);

        ArgumentCaptor<Author> captor = ArgumentCaptor.forClass(Author.class);
        verify(authorPersistencePort).save(captor.capture());
        assertEquals(51000d, captor.getValue().getAnnualIncome());
    }
}
