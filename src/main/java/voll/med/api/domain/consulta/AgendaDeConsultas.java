package voll.med.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import voll.med.api.domain.ValidacaoException;
import voll.med.api.domain.medico.Medico;
import voll.med.api.domain.medico.MedicoRepository;
import voll.med.api.domain.paciente.PacienteRepository;

@Service
    public class AgendaDeConsultas {

        @Autowired
        ConsultaRepository consultaRepository;

        @Autowired
        private MedicoRepository medicoRepository;

        @Autowired
        private PacienteRepository pacienteRepository;
        public void agendar(DadosAgendamentoConsulta dados) {
            if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
                throw new ValidacaoException("Id do médico informado não existe");
            }
            if (!pacienteRepository.existsById(dados.idPaciente())) {
                throw new ValidacaoException("Id do paciente informado não existe");
            }
            var medico = escolherMedic(dados);
            var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
            var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        }

        private Medico escolherMedic(DadosAgendamentoConsulta dados) {
            if (dados.idMedico() != null) {
                return medicoRepository.getReferenceById(dados.idMedico());
            }

            if (dados.especialidade() == null) {
                throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido");
            }

            return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
        }

        public void cancelar(DadosCancelamentoConsulta dados) {
            if (!consultaRepository.existsById(dados.idConsulta())) {
                throw new ValidacaoException("Id da consulta informado não existe.");
            }

            var consulta = consultaRepository.getReferenceById(dados.idConsulta());
            consulta.cancelar(dados.motivo());
        }
    }
