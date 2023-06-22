package voll.med.api.domain.consulta.validacoes;

import voll.med.api.domain.ValidacaoException;
import voll.med.api.domain.consulta.DadosAgendamentoConsulta;
import voll.med.api.domain.medico.MedicoRepository;

    public class ValidadorMedicoAtivo {

        private MedicoRepository repository;

        public void validar(DadosAgendamentoConsulta dados) {
            if (dados.idMedico() == null) {
                return;
            }

            var medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
            if (!medicoEstaAtivo) {
                throw new ValidacaoException("Consulta não pode ser agendada com médico inativo.");
            }
        }
    }
