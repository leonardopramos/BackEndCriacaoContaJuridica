package br.com.bancoalpha.DesafioPraticoGoTech2024.service;

import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaFisica.Socio;
import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica.ContaPessoaJuridica;
import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica.Endereco;
import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica.Status;
import br.com.bancoalpha.DesafioPraticoGoTech2024.repository.IContaPessoaJuridicaRepository;
import br.com.bancoalpha.DesafioPraticoGoTech2024.repository.ISocioRepository;
import br.com.bancoalpha.DesafioPraticoGoTech2024.util.CnpjConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SocioServiceTest {

    @Mock
    private ISocioRepository socioRepository;

    @Mock
    private IContaPessoaJuridicaRepository contaPessoaJuridicaRepository;

    @Mock
    private CnpjConverter cnpjConverter;

    @Spy
    @InjectMocks
    private SocioService socioService;

    @Test
    void adicionaNovoSocio_comPercentualValido_deveSalvarSocio() throws Exception {
        // Configuração do cenário
        ContaPessoaJuridica contaPessoaJuridica = criarContaPessoaJuridica();
        Socio socio = new Socio("1", "NomeSocio", "123.456.789-09", "socio@email.com", "123456789", 20.0, contaPessoaJuridica);
        String cnpj = "12345678901234";
        when(cnpjConverter.formatCnpj(cnpj)).thenReturn(cnpj);
        when(contaPessoaJuridicaRepository.findBycnpj(cnpj)).thenReturn(contaPessoaJuridica);
        when(socioRepository.findAll()).thenReturn(criarListaSociosExistente());

        // Executa o método sob teste
        socioService.adicionaNovoSocio(socio, cnpj);

        // Verificações
        verify(socioRepository, times(1)).save(socio);
    }

    @Test
    void adicionaNovoSocio_comPercentualInvalido_deveLancarException() {
        // Configuração do cenário
        ContaPessoaJuridica contaPessoaJuridica = criarContaPessoaJuridica();
        Socio socio = new Socio("1", "NomeSocio", "123.456.789-09", "socio@email.com", "123456789", 80.0, contaPessoaJuridica);
        String cnpj = "12345678901234";
        when(cnpjConverter.formatCnpj(cnpj)).thenReturn(cnpj);
        when(contaPessoaJuridicaRepository.findBycnpj(cnpj)).thenReturn(contaPessoaJuridica);
        when(socioRepository.findAll()).thenReturn(criarListaSociosExistente());

        // Executa o método sob teste e verifica a exceção
        assertThrows(Exception.class, () -> socioService.adicionaNovoSocio(socio, cnpj));
    }

    @Test
    void adicionaNovoSocio_chamaGerarUUID_deveGerarUUID() throws Exception {
        // Configuração do cenário
        ContaPessoaJuridica contaPessoaJuridica = criarContaPessoaJuridica();
        Socio socio = new Socio("1", "NomeSocio", "123.456.789-09", "socio@email.com", "123456789", 20.0, contaPessoaJuridica);
        String cnpj = "12345678901234";
        when(cnpjConverter.formatCnpj(cnpj)).thenReturn(cnpj);
        when(contaPessoaJuridicaRepository.findBycnpj(cnpj)).thenReturn(contaPessoaJuridica);
        when(socioRepository.findAll()).thenReturn(criarListaSociosExistente());

        // Executa o método sob teste
        socioService.adicionaNovoSocio(socio, cnpj);

        // Verificações
        verify(socioRepository, times(1)).save(socio);
        verify(socioService, times(1)).gerarUUID();
    }

    // Métodos auxiliares para criar objetos fictícios

    private ContaPessoaJuridica criarContaPessoaJuridica() {
        return new ContaPessoaJuridica("1", "12345678901234", new Endereco("Rua Teste", "123", "Apto 4", "Bairro Teste", "Cidade Teste", "SP", "12345678", "Brasil"), 100000.0, 1234, 56789, new ArrayList<>(), Status.EM_ANDAMENTO, "senha");
    }

    private List<Socio> criarListaSociosExistente() {
        List<Socio> socios = new ArrayList<>();
        // Adicione socios fictícios à lista conforme necessário
        socios.add(new Socio("2", "NomeSocio2", "987.654.321-09", "socio2@email.com", "987654321", 30.0, criarContaPessoaJuridica()));
        socios.add(new Socio("3", "NomeSocio3", "111.222.333-44", "socio3@email.com", "111222333", 50.0, criarContaPessoaJuridica()));
        return socios;
    }
}

