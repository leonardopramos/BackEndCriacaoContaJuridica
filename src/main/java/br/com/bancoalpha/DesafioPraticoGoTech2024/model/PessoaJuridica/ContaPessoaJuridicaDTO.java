package br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica;


public record ContaPessoaJuridicaDTO(String id, String cnpj, int agencia, int numeroConta, Status solicitacaoStatus) {

    public ContaPessoaJuridicaDTO(ContaPessoaJuridica contaPessoaJuridica){
        this(contaPessoaJuridica.getId(), contaPessoaJuridica.getCnpj(), contaPessoaJuridica.getAgencia(), contaPessoaJuridica.getNumeroConta(), contaPessoaJuridica.getSolicitacaoStatus());
    }
}
