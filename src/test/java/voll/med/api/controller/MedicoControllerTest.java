package voll.med.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import voll.med.api.domain.endereco.DadosEndereco;
import voll.med.api.domain.endereco.Endereco;
import voll.med.api.domain.medico.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

    @SpringBootTest
    @AutoConfigureMockMvc
    @AutoConfigureJsonTesters
    class MedicoControllerTest {

        @Autowired
        private MockMvc mvc;

        @MockBean
        private MedicoRepository repository;

        @Autowired
        private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

        @Autowired
        private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;

        @Test
        @DisplayName("Deveria devolver código http 400 quando informações estão inválidas")
        @WithMockUser
        void cadastrar_cenario1() throws Exception {
            var response = mvc
                    .perform(post("/medicos")).andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @DisplayName("Deveria devolver código http 200 quando informações estão válidas.")
        @WithMockUser
        void cadastrar_cenario2() throws Exception {
            var dadosCadastro = new DadosCadastroMedico(
                    "Medico",
                    "medico@voll.med",
                    "61999999999",
                    "123456",
                    Especialidade.CARDIOLOGIA,
                    dadosEndereco());

            when(repository.save(any())).thenReturn(new Medico(dadosCadastro));

            var reponse = mvc
                    .perform(post("/medicos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dadosCadastroMedicoJson.write(dadosCadastro).getJson()))
                    .andReturn().getResponse();

            var dadosDetalhamento = new DadosDetalhamentoMedico(
                    null,
                    dadosCadastro.nome(),
                    dadosCadastro.crm(),
                    dadosCadastro.email(),
                    dadosCadastro.telefone(),
                    dadosCadastro.especialidade(),
                    new Endereco(dadosCadastro.endereco())
            );

            var jsonEsperado = dadosDetalhamentoMedicoJson.write(dadosDetalhamento).getJson();

            assertThat(reponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(reponse.getContentAsString()).isEqualTo(jsonEsperado);
        }

        private DadosEndereco dadosEndereco() {
            return new DadosEndereco(
                    "rua xpto",
                    null,
                    null,
                    "00000000",
                    "bairro",
                    "Brasilia",
                    "DF"
            );
        }
    }