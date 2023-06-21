package voll.med.api.endereco;

    public record DadosEndereco(
            String logradouro, String numero, String complemento, String cep, String bairro,
            String cidade, String uf) {
    }
