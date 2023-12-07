package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica;


import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica.Socio;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.Status;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.CnpjGenerator;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.IPessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaPessoaJuridicaService {
    @Autowired
    IPessoaJuridicaRepository repository;
    @Autowired
    CnpjGenerator cnpjGenerator;

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
        double aux = 0;
        contaPessoaJuridica.setStatus(Status.EM_ANDAMENTO);
        contaPessoaJuridica.setCnpj(cnpjGenerator.gerarCNPJ());
        for(Socio s : contaPessoaJuridica.getSocios()){
            s.setContaPessoaJuridica(contaPessoaJuridica);
            aux += s.getPercentualParticipacao();
        }
        if(aux >100) throw new RuntimeException();
        //criar erro personalizado
        repository.save(contaPessoaJuridica);
    }

    public String acompanhamentoStatusSolicitacao(Long id) {
        Optional<ContaPessoaJuridica> pessoaEncontrada = repository.findById(id);
        if (pessoaEncontrada.isPresent()) {
            ContaPessoaJuridica contaPessoaJuridica = pessoaEncontrada.get();
            return String.valueOf(contaPessoaJuridica.getStatus());
        }
        return "Pessoa não encontrada";
    }
}
