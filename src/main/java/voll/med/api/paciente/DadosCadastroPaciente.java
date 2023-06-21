package voll.med.api.paciente;

import voll.med.api.endereco.DadosEndereco;

    public record DadosCadastroPaciente(
        String nome, String cpf, String email, String telefone, DadosEndereco endereco) {
    }
