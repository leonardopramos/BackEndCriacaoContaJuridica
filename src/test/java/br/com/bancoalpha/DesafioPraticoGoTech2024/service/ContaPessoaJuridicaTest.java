package br.com.bancoalpha.DesafioPraticoGoTech2024.service;

import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaFisica.Socio;
import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica.ContaPessoaJuridica;
import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica.Status;
import br.com.bancoalpha.DesafioPraticoGoTech2024.repository.IContaPessoaJuridicaRepository;
import br.com.bancoalpha.DesafioPraticoGoTech2024.repository.ISocioRepository;
import br.com.bancoalpha.DesafioPraticoGoTech2024.util.AgenciaGenerator;
import br.com.bancoalpha.DesafioPraticoGoTech2024.util.CnpjConverter;
import br.com.bancoalpha.DesafioPraticoGoTech2024.util.NumeroContaGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

class ContaPessoaJuridicaServiceTest {

    @Mock
    private IContaPessoaJuridicaRepository contaRepository;

    @Mock
    private ISocioRepository socioRepository;

    @Mock
    private NumeroContaGenerator numeroContaGenerator;

    @Mock
    private AgenciaGenerator agenciaGenerator;

    @Mock
    private CnpjConverter cnpjConverter;

    @InjectMocks
    private ContaPessoaJuridicaService contaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void persistePessoaJuridica_deveConfigurarContaEAssociarSocios() {
        // Configuração do cenário
        ContaPessoaJuridica contaPessoaJuridica = new ContaPessoaJuridica();

        when(numeroContaGenerator.gerar()).thenReturn(123);
        when(agenciaGenerator.gerar()).thenReturn(456);

        // Executa o método sob teste
        contaService.persistePessoaJuridica(contaPessoaJuridica);

        // Verificações
        assertNotNull(contaPessoaJuridica.getId());
        assertEquals(Status.EM_ANDAMENTO, contaPessoaJuridica.getSolicitacaoStatus());
        assertEquals(123, contaPessoaJuridica.getNumeroConta());
        assertEquals(456, contaPessoaJuridica.getAgencia());
        verify(contaRepository, times(1)).save(contaPessoaJuridica);
    }

    @Test
    void acompanhamentoStatusSolicitacao_deveRetornarStatusFormatado() {
        // Configuração do cenário
        String cnpj = "12345678901234";
        ContaPessoaJuridica contaPessoaJuridica = new ContaPessoaJuridica();
        contaPessoaJuridica.setSolicitacaoStatus(Status.APROVADA);

        when(cnpjConverter.formatCnpj(cnpj)).thenReturn(cnpj);
        when(contaRepository.findBycnpj(cnpj)).thenReturn(contaPessoaJuridica);

        // Executa o método sob teste
        String resultado = contaService.acompanhamentoStatusSolicitacao(cnpj);

        // Verificações
        assertEquals("Aprovada.", resultado);
    }
    @Test
    void listUsers_deveRetornarListaDeSociosCorreta() {
        // Configuração do cenário
        String cnpj = "12345678901234";
        ContaPessoaJuridica contaPessoaJuridica = new ContaPessoaJuridica();
        contaPessoaJuridica.setId("1");
        when(contaService.findByCnpj(cnpj)).thenReturn(contaPessoaJuridica);

        List<Socio> socios = Arrays.asList(
                new Socio("1", "Socio1", "123", "email1@test.com", "123456", 10.0, contaPessoaJuridica),
                new Socio("2", "Socio2", "456", "email2@test.com", "789012", 20.0, contaPessoaJuridica)
        );
        when(socioRepository.findAllByContaPessoaJuridicaId(contaPessoaJuridica.getId())).thenReturn(socios);

        // Executa o método sob teste
        List<Socio> resultado = contaService.listUsers(cnpj);

        // Verificações
        assertEquals(socios.size(), resultado.size());
    }


}

