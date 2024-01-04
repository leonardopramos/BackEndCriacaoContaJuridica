package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.Status;

import java.util.UUID;


public record ContaPessoaJuridicaResponseBody(String id, String cnpj, int agencia, int numeroConta, Status solicitacaoStatus) {

    public  ContaPessoaJuridicaResponseBody(ContaPessoaJuridica contaPessoaJuridica){
        this(contaPessoaJuridica.getId(), contaPessoaJuridica.getCnpj(), contaPessoaJuridica.getAgencia(), contaPessoaJuridica.getNumeroConta(), contaPessoaJuridica.getSolicitacaoStatus());
    }
}
