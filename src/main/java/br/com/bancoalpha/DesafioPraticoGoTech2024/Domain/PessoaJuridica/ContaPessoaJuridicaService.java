package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica.Socio;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.Status;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.AgenciaGenerator;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.CnpjConverter;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.Exception.Handler;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.IPessoaJuridicaRepository;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.NumeroContaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ContaPessoaJuridicaService {
    @Autowired
    IPessoaJuridicaRepository repository;
    @Autowired
    NumeroContaGenerator numeroContaGenerator;
    @Autowired
    AgenciaGenerator agenciaGenerator;
    @Autowired
    CnpjConverter cnpjConverter;

    public void aprovaSolicitacao(Long id) {
        ContaPessoaJuridica contaPessoaJuridica = repository.findById(id)
                .orElseThrow(() -> new Handler.ContaNaoEncontradaException("Conta não encontrada"));
        contaPessoaJuridica.setSolicitacaoStatus(Status.APROVADA);
        repository.save(contaPessoaJuridica);
    }

    public void cancelaSolicitacao(Long id) {
        ContaPessoaJuridica contaPessoaJuridica = repository.findById(id)
                .orElseThrow(() -> new Handler.ContaNaoEncontradaException("Conta não encontrada"));
        contaPessoaJuridica.setSolicitacaoStatus(Status.CANCELADA);
        repository.save(contaPessoaJuridica);
    }

    public void persistePessoaJuridica(ContaPessoaJuridica contaPessoaJuridica) {
        configurarContaPessoaJuridica(contaPessoaJuridica);
        repository.save(contaPessoaJuridica);
    }


    public String acompanhamentoStatusSolicitacao(String cnpj) {
        String cnpjFormatado = converteCnpj(cnpj);
        ContaPessoaJuridica pessoaEncontrada = repository.findBycnpj(cnpjFormatado);
        return (pessoaEncontrada != null) ? pessoaEncontrada.retornaStatusConvertido() : "Pessoa não encontrada";
    }

    public boolean login(String cnpj, String senha) {
        String cnpjLogin= converteCnpj(cnpj);
        return repository.findAll().stream()
                .anyMatch(conta -> conta.getCnpj().equals(cnpjLogin) && conta.getSenha().equals(senha));
    }

    public String converteCnpj(String cnpj) {
        return cnpjConverter.formatCnpj(cnpj);
    }

    private void configurarContaPessoaJuridica(ContaPessoaJuridica contaPessoaJuridica) {
        contaPessoaJuridica.setSolicitacaoStatus(Status.EM_ANDAMENTO);
        configurarNumeroContaEAgencia(contaPessoaJuridica);
        associarContaAoSocios(contaPessoaJuridica);
    }

    private void configurarNumeroContaEAgencia(ContaPessoaJuridica contaPessoaJuridica) {
        contaPessoaJuridica.setNumeroConta(numeroContaGenerator.gerar());
        contaPessoaJuridica.setAgencia(agenciaGenerator.gerar());
    }

    private void associarContaAoSocios(ContaPessoaJuridica contaPessoaJuridica) {
        for (Socio socio : contaPessoaJuridica.getSocios()) {
            socio.setContaPessoaJuridica(contaPessoaJuridica);
        }
    }

}