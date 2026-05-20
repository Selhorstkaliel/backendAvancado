package com.example.frankenstein.security;

import com.example.frankenstein.adapter.out.persistence.AuthorJpaRepository;
import com.example.frankenstein.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorJpaRepository authorRepository;

    private Long authorId;

    @BeforeEach
    void setup() {
        authorRepository.deleteAll();
        Author author = new Author();
        author.setName("Teste");
        author.setCpf("12345678901");
        author.setAnnualIncome(1000d);
        authorId = authorRepository.save(author).getId();
    }

    @Test
    void shouldReturnUnauthorizedWhenDeleteWithoutToken() throws Exception {
        mockMvc.perform(delete("/api/v1/authors/{id}", authorId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnForbiddenWhenDeleteWithNonAdminToken() throws Exception {
        String token = loginAndGetToken("user", "user123");

        mockMvc.perform(delete("/api/v1/authors/{id}", authorId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldAllowDeleteWhenAdminToken() throws Exception {
        String token = loginAndGetToken("admin", "admin123");

        mockMvc.perform(delete("/api/v1/authors/{id}", authorId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    private String loginAndGetToken(String username, String password) throws Exception {
        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return response.replace("{\"token\":\"", "").replace("\"}", "");
    }
}
