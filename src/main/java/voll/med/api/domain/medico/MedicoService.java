package voll.med.api.domain.medico;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

    @Service
    public class MedicoService {

        private static final Logger logger = LoggerFactory.getLogger(MedicoService.class);

        private final MedicoRepository repository;

        @Autowired
        public MedicoService(MedicoRepository repository) {
            this.repository = repository;
        }
        public Medico cadastrarMedico(DadosCadastroMedico dados) {
            Medico medico = new Medico(dados);
            Medico savedMedico = repository.save(medico);
            logger.info("Médico cadastrado com sucesso. ID: {}", savedMedico.getId());
            return savedMedico;
        }
    }
