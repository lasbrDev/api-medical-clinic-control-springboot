package voll.med.api.medico;

import voll.med.api.endereco.DadosEndereco;

    public record DadosCadastroMedico(
        String nome, String email, String telefone, String crm,
        Especialidade especialidade, DadosEndereco endereco) {
    }
