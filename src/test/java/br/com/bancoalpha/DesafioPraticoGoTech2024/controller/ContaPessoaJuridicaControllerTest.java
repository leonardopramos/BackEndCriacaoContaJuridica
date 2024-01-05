package br.com.bancoalpha.DesafioPraticoGoTech2024.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaFisica.Socio;
import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica.*;
import br.com.bancoalpha.DesafioPraticoGoTech2024.service.ContaPessoaJuridicaService;
import br.com.bancoalpha.DesafioPraticoGoTech2024.service.SocioService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContaPessoaJuridicaControllerTest {
    @Test
    void testCreateContaPessoaJuridica() {
        ContaPessoaJuridicaService contaPessoaJuridicaService = mock(ContaPessoaJuridicaService.class);
        ContaPessoaJuridicaController controller = new ContaPessoaJuridicaController();
        controller.contaPessoaJuridicaService = contaPessoaJuridicaService;

        doNothing().when(contaPessoaJuridicaService).persistePessoaJuridica(any(ContaPessoaJuridica.class));
        ResponseEntity<ContaPessoaJuridicaDTO> responseEntity = controller.createContaPessoaJuridica(new ContaPessoaJuridica());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ArgumentCaptor<ContaPessoaJuridica> contaCaptor = ArgumentCaptor.forClass(ContaPessoaJuridica.class);
        verify(contaPessoaJuridicaService, times(1)).persistePessoaJuridica(contaCaptor.capture());
        ContaPessoaJuridica capturedConta = contaCaptor.getValue();
        assertNotNull(capturedConta);
    }
    @Test
    void testLogin() {
        // Configurar objetos mock
        ContaPessoaJuridicaService contaPessoaJuridicaService = mock(ContaPessoaJuridicaService.class);
        ContaPessoaJuridicaController controller = new ContaPessoaJuridicaController();
        controller.contaPessoaJuridicaService = contaPessoaJuridicaService;

        // Configurar comportamento do serviço
        when(contaPessoaJuridicaService.login(anyString(), anyString())).thenReturn(true);

        // Executar método
        ResponseEntity<String> responseEntity = controller.login("cnpj", "senha");

        // Verificar resultado
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Login bem sucedido.", responseEntity.getBody());

        // Verificar se o método correto foi chamado no serviço
        verify(contaPessoaJuridicaService, times(1)).login(eq("cnpj"), eq("senha"));
    }
    @Test
    void testListarContasPessoaJuridica() {
        // Configurar objetos mock
        ContaPessoaJuridicaService contaPessoaJuridicaService = mock(ContaPessoaJuridicaService.class);
        ContaPessoaJuridicaController controller = new ContaPessoaJuridicaController();
        controller.contaPessoaJuridicaService = contaPessoaJuridicaService;

        // Criar dados fictícios
        Endereco endereco1 = new Endereco("Rua A", "123", "Complemento A", "Bairro A", "Cidade A", "Estado A", "12345-678", "Brasil");
        Endereco endereco2 = new Endereco("Rua B", "456", "Complemento B", "Bairro B", "Cidade B", "Estado B", "98765-432", "Brasil");

        ContaPessoaJuridica conta1 = new ContaPessoaJuridica("1", "12345678901234", endereco1, 50000.0, 123, 4567, new ArrayList<>(), Status.APROVADA, "senha1");
        ContaPessoaJuridica conta2 = new ContaPessoaJuridica("2", "56789012345678", endereco2, 75000.0, 456, 7890, new ArrayList<>(), Status.EM_ANDAMENTO, "senha2");

        // Configurar comportamento do serviço
        List<ContaPessoaJuridica> contasEsperadas = Arrays.asList(conta1, conta2);
        when(contaPessoaJuridicaService.listarContasPessoaJuridica()).thenReturn(contasEsperadas);

        // Executar método
        List<ContaPessoaJuridica> result = controller.listarContasPessoaJuridica();

        // Verificar resultado
        assertNotNull(result);
        assertEquals(contasEsperadas.size(), result.size());

        // Verificar se o método correto foi chamado no serviço
        verify(contaPessoaJuridicaService, times(1)).listarContasPessoaJuridica();

        // Adicione mais asserções conforme necessário para validar os dados das contas retornadas
        assertEquals(conta1, result.get(0));
        assertEquals(conta2, result.get(1));
    }
    @Test
    void testSendEmail() {
        // Configurar objetos mock
        ContaPessoaJuridicaService contaPessoaJuridicaService = mock(ContaPessoaJuridicaService.class);
        ContaPessoaJuridicaController controller = new ContaPessoaJuridicaController();
        controller.contaPessoaJuridicaService = contaPessoaJuridicaService;

        // Criar dados fictícios
        String cnpj = "12345678901234";
        EmailRequest emailRequest = new EmailRequest("Para","Assunto", "Corpo do e-mail");

        // Configurar comportamento do serviço
        when(contaPessoaJuridicaService.enviarEmail(emailRequest, cnpj)).thenReturn("E-mail enviado com sucesso.");

        // Executar método
        String result = controller.sendEmail(emailRequest, cnpj);

        // Verificar resultado
        assertEquals("E-mail enviado com sucesso.", result);

        // Verificar se o método correto foi chamado no serviço
        verify(contaPessoaJuridicaService, times(1)).enviarEmail(emailRequest, cnpj);
    }
    @Test
    void testCreateSocio() throws Exception {
        // Configurar objetos mock
        ContaPessoaJuridicaService contaPessoaJuridicaService = mock(ContaPessoaJuridicaService.class);
        SocioService socioService = mock(SocioService.class);
        ContaPessoaJuridicaController controller = new ContaPessoaJuridicaController();
        controller.contaPessoaJuridicaService = contaPessoaJuridicaService;
        controller.socioService = socioService;

        // Criar dados fictícios
        String cnpj = "12345678901234";
        Socio socio = new Socio("1",
                "Fulano de Tal",
                "123.456.789-09",
                "fulano@example.com",
                "(11) 1234-5678",
                10.5,
                new ContaPessoaJuridica()
                );

        // Configurar comportamento do serviço
        doNothing().when(socioService).adicionaNovoSocio(socio, cnpj);

        // Executar método
        ResponseEntity<Void> responseEntity = controller.createSocio(cnpj, socio);

        // Verificar resultado
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verificar se o método correto foi chamado no serviço
        verify(socioService, times(1)).adicionaNovoSocio(socio, cnpj);
    }



}
