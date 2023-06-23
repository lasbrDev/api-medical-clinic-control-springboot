package voll.med.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voll.med.api.domain.ValidacaoException;
import voll.med.api.domain.consulta.ConsultaRepository;
import voll.med.api.domain.consulta.DadosAgendamentoConsulta;

    @Component
    public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

        @Autowired
        private ConsultaRepository repository;

        public void validar(DadosAgendamentoConsulta dados) {
            var medicoPossuiOutraConsultaNoMesmoHorario =
                    repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
            if (medicoPossuiOutraConsultaNoMesmoHorario) {
                throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário.");
            }
        }
    }
