package br.com.bancoalpha.DesafioPraticoGoTech2024.service;

import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaFisica.Socio;
import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica.ContaPessoaJuridica;
import br.com.bancoalpha.DesafioPraticoGoTech2024.exception.Handler;
import br.com.bancoalpha.DesafioPraticoGoTech2024.repository.IContaPessoaJuridicaRepository;
import br.com.bancoalpha.DesafioPraticoGoTech2024.repository.ISocioRepository;
import br.com.bancoalpha.DesafioPraticoGoTech2024.util.CnpjConverter;
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

    protected String gerarUUID() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString();
    }

}