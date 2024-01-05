package br.com.bancoalpha.DesafioPraticoGoTech2024.controller;

import br.com.bancoalpha.DesafioPraticoGoTech2024.service.ContaPessoaJuridicaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitacoesControllerTest {

    @Mock
    private ContaPessoaJuridicaService contaPessoaJuridicaService;

    @InjectMocks
    private SolicitacoesController solicitacoesController;

    @Test
    void aprovaSolicitacao_deveRetornarOkQuandoAprovadoComSucesso() {
        String cnpj = "12345678901234";

        // Simula que a aprovação foi bem-sucedida (pode ser ajustado conforme a sua implementação)
        doNothing().when(contaPessoaJuridicaService).aprovaSolicitacao(cnpj);

        ResponseEntity<String> response = solicitacoesController.aprovaSolicitacao(cnpj);

        assertEquals("Solicitação aprovada com sucesso.", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(contaPessoaJuridicaService, times(1)).aprovaSolicitacao(cnpj);
    }

    @Test
    void cancelaSolicitacao_deveRetornarOkQuandoCanceladoComSucesso() {
        String cnpj = "12345678901234";

        // Simula que o cancelamento foi bem-sucedido (pode ser ajustado conforme a sua implementação)
        doNothing().when(contaPessoaJuridicaService).cancelaSolicitacao(cnpj);

        ResponseEntity<String> response = solicitacoesController.cancelaSolicitacao(cnpj);

        assertEquals("Solicitação cancelada.", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(contaPessoaJuridicaService, times(1)).cancelaSolicitacao(cnpj);
    }

    @Test
    void acompanhamentoSolicitacao_deveRetornarStatusAcompanhamento() {
        String cnpj = "12345678901234";
        String statusEsperado = "Aprovada.";
        when(contaPessoaJuridicaService.acompanhamentoStatusSolicitacao(cnpj)).thenReturn(statusEsperado);

        ResponseEntity<String> response = solicitacoesController.acompanhamentoSolicitacao(cnpj);

        assertEquals(statusEsperado, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(contaPessoaJuridicaService, times(1)).acompanhamentoStatusSolicitacao(cnpj);
    }
}

