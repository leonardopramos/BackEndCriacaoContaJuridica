package br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica;

import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaFisica.Socio;
import java.util.List;
public record ContaPessoaJuridicaDTO(
        String id,
        String cnpj,
        String senha,
        double faturamentoMensal,
        String porte,
        Double capitalSocial,
        String dataDeAbertura,
        String fantasia,
        String naturezaJuridica,
        List<Socio> socios,
        Endereco endereco,
        List<Atividade> atividades,
        int agencia,
        int numeroConta,
        Status solicitacaoStatus
) {
    public ContaPessoaJuridicaDTO(ContaPessoaJuridica contaPessoaJuridica) {
        this(
                contaPessoaJuridica.getId(),
                contaPessoaJuridica.getCnpj(),
                contaPessoaJuridica.getSenha(),
                contaPessoaJuridica.getFaturamentoMensal(),
                contaPessoaJuridica.getPorte(),
                contaPessoaJuridica.getCapitalSocial(),
                contaPessoaJuridica.getDataDeAbertura(),
                contaPessoaJuridica.getFantasia(),
                contaPessoaJuridica.getNaturezaJuridica(),
                contaPessoaJuridica.getSocios(),
                contaPessoaJuridica.getEndereco(),
                contaPessoaJuridica.getAtividades(),
                contaPessoaJuridica.getAgencia(),
                contaPessoaJuridica.getNumeroConta(),
                contaPessoaJuridica.getSolicitacaoStatus()
        );
    }
}
