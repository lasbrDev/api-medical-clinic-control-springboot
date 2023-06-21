package voll.med.api.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import voll.med.api.paciente.DadosCadastroPaciente;

@RestController
    @RequestMapping("/pacientes")
    public class PacienteController {

        public void cadastrar(@RequestBody DadosCadastroPaciente dados) {
            System.out.println(dados);
        }
    }
