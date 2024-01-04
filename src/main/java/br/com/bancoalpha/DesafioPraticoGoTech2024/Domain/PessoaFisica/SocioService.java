package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridica;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.CnpjConverter;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.Exception.Handler;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.IContaPessoaJuridicaRepository;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.ISocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SocioService {
    @Autowired
    ISocioRepository repository;
    @Autowired
    IContaPessoaJuridicaRepository contaPessoaJuridicaRepository;
    @Autowired
    CnpjConverter cnpjConverter;

    public String converteCnpj(String cnpj) {
        return cnpjConverter.formatCnpj(cnpj);
    }

    public void adicionaNovoSocio(Socio socio, String cnpj) throws Exception {
        try {
            String cnpjConvertido = converteCnpj(cnpj);
            ContaPessoaJuridica contaEncontrada = contaPessoaJuridicaRepository.findBycnpj(cnpjConvertido);
            socio.setContaPessoaJuridica(contaEncontrada);
            double percentualAtualDaConta = repository.findAll().stream()
                    .filter(s -> s.getContaPessoaJuridica().getId().equals(socio.getContaPessoaJuridica().getId()))
                    .mapToDouble(Socio::getPercentualParticipacao)
                    .sum();
            if ((socio.getPercentualParticipacao() + percentualAtualDaConta) > 100) {
                throw new Exception("Percentual inválido, tente novamente.");
            }
            socio.setId(gerarUUID());
            repository.save(socio);
        } catch (Exception e) {
            throw new Exception("Erro ao adicionar novo sócio: " + e.getMessage());
        }
    }


    private void validarPercentualParticipacao(Socio socio, ContaPessoaJuridica conta) {
        double percentualAtualDaConta = repository
                .findAll()
                .stream()
                .filter(s -> s.getContaPessoaJuridica().getId().equals(socio.getContaPessoaJuridica().getId()))
                .mapToDouble(Socio::getPercentualParticipacao)
                .sum();

        if ((socio.getPercentualParticipacao() + percentualAtualDaConta) > 100) {
            throw new Handler.PercentualParticipacaoInvalidoException("Percentual inválido, tente novamente.");
        }
    }
    private String gerarUUID() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString();
    }

}