package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.EmailRequest;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.Exception.Handler;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.ISocioRepository;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica.Socio;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.Status;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.AgenciaGenerator;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.CnpjConverter;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.IContaPessoaJuridicaRepository;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.NumeroContaGenerator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
public class ContaPessoaJuridicaService {
    @Autowired
    private IContaPessoaJuridicaRepository repository;
    @Autowired
    private ISocioRepository socioRepository;
    @Autowired
    private NumeroContaGenerator numeroContaGenerator;
    @Autowired
    private AgenciaGenerator agenciaGenerator;
    @Autowired
    private CnpjConverter cnpjConverter;
    @Autowired
    private JavaMailSender javaMailSender;


    public void aprovaSolicitacao(String cnpj) {
        String cnpjFormatado = converteCnpj(cnpj);
        ContaPessoaJuridica contaEncontrada = repository.findBycnpj(cnpjFormatado);
        ContaPessoaJuridica contaPessoaJuridica = repository.findById(contaEncontrada.getId())
                .orElseThrow(() -> new Handler.ContaNaoEncontradaException("Conta não encontrada"));
        contaPessoaJuridica.setSolicitacaoStatus(Status.APROVADA);
        repository.save(contaPessoaJuridica);
    }

    public void cancelaSolicitacao(String cnpj) {
        String cnpjFormatado = converteCnpj(cnpj);
        ContaPessoaJuridica contaEncontrada = repository.findBycnpj(cnpjFormatado);
        ContaPessoaJuridica contaPessoaJuridica = repository.findById(contaEncontrada.getId())
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
        ContaPessoaJuridica contaEncontrada = repository.findBycnpj(cnpjFormatado);
        return (contaEncontrada != null) ? contaEncontrada.retornaStatusConvertido() : "Pessoa não encontrada";
    }

    public boolean login(String cnpj, String senha) {
        String cnpjLogin = converteCnpj(cnpj);
        ContaPessoaJuridica contaEncontrada = repository.findBycnpj(cnpjLogin);

        return contaEncontrada != null && BCrypt.checkpw(senha, contaEncontrada.getSenha());
    }


    public String converteCnpj(String cnpj) {
        return cnpjConverter.formatCnpj(cnpj);
    }

    private void configurarContaPessoaJuridica(ContaPessoaJuridica contaPessoaJuridica) {
        contaPessoaJuridica.setId(gerarUUID());
        contaPessoaJuridica.setSolicitacaoStatus(Status.EM_ANDAMENTO);
        hashSenha(contaPessoaJuridica);
        configurarNumeroContaEAgencia(contaPessoaJuridica);
        associarContaAoSocios(contaPessoaJuridica);
    }

    private void hashSenha(ContaPessoaJuridica contaPessoaJuridica) {
        String salt = BCrypt.gensalt();
        String hashSenha = BCrypt.hashpw(contaPessoaJuridica.getSenha(), salt);
        contaPessoaJuridica.setSenha(hashSenha);
    }

    private String gerarUUID() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString();
    }

    private void configurarNumeroContaEAgencia(ContaPessoaJuridica contaPessoaJuridica) {
        contaPessoaJuridica.setNumeroConta(numeroContaGenerator.gerar());
        contaPessoaJuridica.setAgencia(agenciaGenerator.gerar());
    }

    private void associarContaAoSocios(ContaPessoaJuridica contaPessoaJuridica) {
        contaPessoaJuridica.getSocios()
                .forEach(socio -> {
                    socio.setId(gerarUUID());
                    socio.setContaPessoaJuridica(contaPessoaJuridica);
                });
    }

    private ContaPessoaJuridica findByCnpj(String cnpj) {
        String cnpjFormatado = converteCnpj(cnpj);
        return repository.findBycnpj(cnpjFormatado);
    }

    private List<Socio> findSociosByConta(ContaPessoaJuridica conta) {
        return socioRepository.findAllByContaPessoaJuridicaId(conta.getId());
    }


    public List<ContaPessoaJuridica> listarContasPessoaJuridica() {
        return repository.findAll();
    }

    public String enviarEmail(EmailRequest requisicaoEmail, String cnpj) {
        try {
            ContaPessoaJuridica conta = findByCnpj(cnpj);

            List<Socio> socios = findSociosByConta(conta);

            if (!socios.isEmpty()) {
                enviarEmailParaSocios(requisicaoEmail, conta, socios);
                return "E-mail enviado com sucesso para todos os sócios!";
            } else {
                return "Nenhum sócio encontrado para enviar e-mail.";
            }
        } catch (Exception e) {
            return "Erro ao enviar e-mail: " + e.getMessage();
        }
    }

    private void enviarEmailParaSocios(EmailRequest requisicaoEmail, ContaPessoaJuridica conta, List<Socio> socios) {
        for (Socio socio : socios) {
            String corpoEmail = criarCorpoEmail(requisicaoEmail.getBody(), conta, socio);

            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setTo(socio.getEmail());
            mensagem.setSubject(requisicaoEmail.getSubject());
            mensagem.setText(corpoEmail);
            javaMailSender.send(mensagem);
        }
    }

    private String criarCorpoEmail(String modeloCorpoEmail, ContaPessoaJuridica conta, Socio socio) {
        return modeloCorpoEmail
                .replace("[CNPJ]", conta.getCnpj())
                .replace("[Número da Agência]", String.valueOf(conta.getAgencia()))
                .replace("[Número da Conta]", String.valueOf(conta.getNumeroConta()))
                .replace("[Nome do Cliente]", socio.getNome());
    }

    public List<Socio> listUsers(String cnpj) {
        try {
            ContaPessoaJuridica contaPessoaJuridica = findByCnpj(cnpj);
            return socioRepository.findAllByContaPessoaJuridicaId(contaPessoaJuridica.getId());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}