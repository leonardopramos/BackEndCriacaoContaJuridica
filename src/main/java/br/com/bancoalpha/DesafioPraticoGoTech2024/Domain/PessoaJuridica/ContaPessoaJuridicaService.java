package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica;


import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica.Socio;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.Status;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.AgenciaGenerator;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.CnpjConverter;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.IPessoaJuridicaRepository;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.NumeroContaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<ContaPessoaJuridica> pessoaEncontrada = repository.findById(id);
        if (pessoaEncontrada.isPresent()) {
            ContaPessoaJuridica contaPessoaJuridica = pessoaEncontrada.get();
            contaPessoaJuridica.setStatus(Status.APROVADA);

            repository.save(contaPessoaJuridica);
        }
        //lançar erro
    }

    public void cancelaSolicitacao(Long id) {
        Optional<ContaPessoaJuridica> pessoaEncontrada = repository.findById(id);
        if (pessoaEncontrada.isPresent()) {
            ContaPessoaJuridica contaPessoaJuridica = pessoaEncontrada.get();
            contaPessoaJuridica.setStatus(Status.CANCELADA);
            repository.save(contaPessoaJuridica);
        }
        //lançar erro
    }

    public void persistePessoaJuridica(ContaPessoaJuridica contaPessoaJuridica) {
        System.out.println(contaPessoaJuridica.toString());
        contaPessoaJuridica.setStatus(Status.EM_ANDAMENTO);
        contaPessoaJuridica.setNumeroConta(numeroContaGenerator.gerar());
        contaPessoaJuridica.setAgencia(agenciaGenerator.gerar());
        for(Socio s : contaPessoaJuridica.getSocios()){
            s.setContaPessoaJuridica(contaPessoaJuridica);
        }
        //criar erro personalizado
        repository.save(contaPessoaJuridica);
    }

    public String acompanhamentoStatusSolicitacao(String aux) {
        ContaPessoaJuridica pessoaEncontrada = repository.findBycnpj(aux);

        if (pessoaEncontrada!= null) {
            return pessoaEncontrada.retornaStatusConvertido();
        }
        return "Pessoa não encontrada";
    }

    public boolean login(String cnpj, String senha) {
        List<ContaPessoaJuridica> list = repository.findAll();
        for(int i = 0; i < list.size(); i++){
            if (list.get(i).getCnpj().equals(cnpj) && list.get(i).getSenha().equals(senha)) return true;
        }
        return false;
    }
    public String converteCnpj(String cnpj){
        return cnpjConverter.formatCnpj(cnpj);
    }
}
