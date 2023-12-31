package voll.med.api.domain.medico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import voll.med.api.domain.consulta.Consulta;
import voll.med.api.domain.endereco.DadosEndereco;
import voll.med.api.domain.paciente.DadosCadastroPaciente;
import voll.med.api.domain.paciente.Paciente;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

    @DataJpaTest
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    @ActiveProfiles("test")
    class MedicoRepositoryTest {

        @Autowired
        private MedicoRepository medicoRepository;

        @Autowired
        private TestEntityManager em;

        @Test
        @DisplayName("Deveria devolver null quando único médico cadastrado não está disponível na data.")
        void escolherMedicoAleatorioLivreNaDataCenario1() {
            var proximaSegundaAs10 =
                    LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

            var medico =
                    cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
            var paciente = cadastrarPaciente("Paciente","00000000000", "paciente@email.com");
            cadastrarConsulta(medico, paciente, proximaSegundaAs10);

            var medicoLivre =
                    medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
            assertThat(medicoLivre).isNull();
        }

        @Test
        @DisplayName("Deveria devolver médico quando ele estiver disponível na data.")
        void escolherMedicoAleatorioNaDataCenario2() {
            var proximaSegundaAs10 =
                    LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
            var medico =
                    cadastrarMedico("Medico","medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
            var medicoLivre =
                    medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
            assertThat(medicoLivre).isEqualTo(medico);
        }

        private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
            em.persist(new Consulta(null, medico, paciente, data, null));
        }

        private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
            var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
            em.persist(medico);
            return medico;
        }

        private Paciente cadastrarPaciente(String nome, String cpf, String email) {
            var paciente = new Paciente(dadosPaciente(nome, cpf, email));
            em.persist(paciente);
            return paciente;
        }

        private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
            return new DadosCadastroMedico(
                    nome,
                    email,
                    "61999999999",
                    crm,
                    especialidade,
                    dadosEndereco()
            );
        }

        private DadosCadastroPaciente dadosPaciente(String nome, String cpf, String email) {
            return new DadosCadastroPaciente(
                    nome,
                    cpf,
                    email,
                   "61999999999",
                   dadosEndereco()
            );
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