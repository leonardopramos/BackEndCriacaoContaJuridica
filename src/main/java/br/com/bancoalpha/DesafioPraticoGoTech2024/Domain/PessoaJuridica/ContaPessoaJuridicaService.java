package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.EmailRequest;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica.SocioListDTO;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.ISocioRepository;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica.Socio;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.Status;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.AgenciaGenerator;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.CnpjConverter;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.Exception.Handler;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.IPessoaJuridicaRepository;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.NumeroContaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ContaPessoaJuridicaService {
    @Autowired
    private IPessoaJuridicaRepository repository;
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

    public String retornaNome(String cnpj) {
        String cnpjFormatado = converteCnpj(cnpj);
        ContaPessoaJuridica contaEncontrada = repository.findBycnpj(cnpjFormatado);
        if (contaEncontrada != null) {
            Socio socioEncontrado = socioRepository.findByContaPessoaJuridicaId(contaEncontrada.getId())
                    .orElse(null);
            return (socioEncontrado != null) ? socioEncontrado.getNome() : "Sócio não encontrado";
        } else {
            return "Conta não encontrada";
        }
    }

    public List<ContaPessoaJuridica> listarContasPessoaJuridica() {
        return repository.findAll();
    }

    public String sendEmail(EmailRequest emailRequest, String cnpj) {
        String cnpjFormatado = converteCnpj(cnpj);
        ContaPessoaJuridica contaEncontrada = repository.findBycnpj(cnpjFormatado);

        if (contaEncontrada != null) {
            List<Socio> sociosEncontrados = socioRepository.findAllByContaPessoaJuridicaId(contaEncontrada.getId());

            if (!sociosEncontrados.isEmpty()) {
                try {
                    for (Socio socioEncontrado : sociosEncontrados) {
                        String corpoEmail = emailRequest.getBody()
                                .replace("[CNPJ]", contaEncontrada.getCnpj())
                                .replace("[Número da Agência]", String.valueOf(contaEncontrada.getAgencia()))
                                .replace("[Número da Conta]", String.valueOf(contaEncontrada.getNumeroConta()))
                                .replace("[Nome do Cliente]", socioEncontrado.getNome());

                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setTo(socioEncontrado.getEmail());
                        message.setSubject(emailRequest.getSubject());
                        message.setText(corpoEmail);
                        javaMailSender.send(message);
                    }
                    return "E-mail enviado com sucesso para todos os sócios!";
                } catch (Exception e) {
                    return "Erro ao enviar e-mail: " + e.getMessage();
                }
            } else {
                return "Nenhum sócio encontrado para enviar e-mail.";
            }
        } else {
            return "Conta não encontrada";
        }
    }

    public List<Socio> listUsers(String cnpj) {
        List<Socio> list = new ArrayList<>();
        String cnpjFormatado = converteCnpj(cnpj);
        ContaPessoaJuridica contaPessoaJuridica = repository.findBycnpj(cnpjFormatado);
        for (Socio s :
                socioRepository.findAll()) {
            if (s.getContaPessoaJuridica().getId().equals(contaPessoaJuridica.getId())) {
                list.add(s);
            }
        }
        return list;
    }
}