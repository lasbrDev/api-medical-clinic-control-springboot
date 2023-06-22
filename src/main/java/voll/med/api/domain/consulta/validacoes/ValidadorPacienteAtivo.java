package voll.med.api.domain.consulta.validacoes;

import voll.med.api.domain.ValidacaoException;
import voll.med.api.domain.consulta.DadosAgendamentoConsulta;
import voll.med.api.domain.paciente.PacienteRepository;

    public class ValidadorPacienteAtivo {
        private PacienteRepository repository;
        public void validar(DadosAgendamentoConsulta dados) {
            var pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());
            if (!pacienteEstaAtivo) {
                throw new ValidacaoException("Consulta não poder ser agendada com paciente inativo.");
            }
        }
    }
