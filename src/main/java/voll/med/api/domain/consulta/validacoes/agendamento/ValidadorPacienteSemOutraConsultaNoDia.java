package voll.med.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voll.med.api.domain.ValidacaoException;
import voll.med.api.domain.consulta.ConsultaRepository;
import voll.med.api.domain.consulta.DadosAgendamentoConsulta;

    @Component
    public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

        @Autowired
        private ConsultaRepository repository;
        public void validar(DadosAgendamentoConsulta dados) {
            var primeiroHorario = dados.data().withHour(7);
            var ultimoHorario = dados.data().withHour(18);
            var pacientePossuiOutraConsultaNoDia =
                    repository.existsByPacienteIdAndDataBetween(
                            dados.idPaciente(), primeiroHorario, ultimoHorario);
            if (pacientePossuiOutraConsultaNoDia) {
                throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia.");
            }
        }
    }
