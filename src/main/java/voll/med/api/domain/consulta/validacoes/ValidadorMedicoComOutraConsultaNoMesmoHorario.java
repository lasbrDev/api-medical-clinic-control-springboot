package voll.med.api.domain.consulta.validacoes;

import voll.med.api.domain.ValidacaoException;
import voll.med.api.domain.consulta.ConsultaRepository;
import voll.med.api.domain.consulta.DadosAgendamentoConsulta;

    public class ValidadorMedicoComOutraConsultaNoMesmoHorario {

        private ConsultaRepository repository;

        public void validar(DadosAgendamentoConsulta dados) {
            var medicoPossuiOutraConsultaNoMesmoHorario =
                    repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
            if (medicoPossuiOutraConsultaNoMesmoHorario) {
                throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário.");
            }
        }
    }
