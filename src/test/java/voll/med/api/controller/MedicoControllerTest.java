package voll.med.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
    @AutoConfigureMockMvc
    @AutoConfigureJsonTesters
    class MedicoControllerTest {

        @Autowired
        private MockMvc mvc;

        @Test
        @DisplayName("Deveria devolver código http 400 quando informações estão inválidas")
        @WithMockUser
        void cadastrar_cenario1() throws Exception {
            var response = mvc.perform(post("/medicos")).andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }