package voll.med.api.domain.medico;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
    public class MedicoService {

        private static final Logger logger = LoggerFactory.getLogger(MedicoService.class);

        private final MedicoRepository repository;

        @Autowired
        public MedicoService(MedicoRepository repository) {
            this.repository = repository;
        }

        @Transactional
        public DadosListagemMedico cadastrarMedico(DadosCadastroMedico dados) {
            try {
                Medico medico = new Medico(dados);
                Medico savedMedico = repository.save(medico);
                logger.info("Médico cadastrado com sucesso. ID: {}", savedMedico.getId());
                return new DadosListagemMedico(savedMedico);
            } catch (Exception e) {
                logger.error("Erro ao cadastrar médico!", e);
                throw new RuntimeException("Erro ao cadastar médico!", e);
            }
        }
    }
