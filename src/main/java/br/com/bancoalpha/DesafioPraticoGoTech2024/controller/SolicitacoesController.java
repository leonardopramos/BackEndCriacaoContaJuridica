package br.com.bancoalpha.DesafioPraticoGoTech2024.controller;

import br.com.bancoalpha.DesafioPraticoGoTech2024.service.ContaPessoaJuridicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitacao")
public class SolicitacoesController {
    @Autowired
    ContaPessoaJuridicaService contaPessoaJuridicaService;

    @GetMapping("/aprova/{cnpj}")
    public ResponseEntity<String> aprovaSolicitacao(@PathVariable String cnpj) {
        contaPessoaJuridicaService.aprovaSolicitacao(cnpj);
        return ResponseEntity.ok("Solicitação aprovada com sucesso.");
    }

    @GetMapping("/cancela/{cnpj}")
    public ResponseEntity<String> cancelaSolicitacao(@PathVariable String cnpj) {
        contaPessoaJuridicaService.cancelaSolicitacao(cnpj);
        return ResponseEntity.ok("Solicitação cancelada.");
    }

    @GetMapping("/status/{cnpj}")
    public ResponseEntity acompanhamentoSolicitacao(@PathVariable String cnpj){
        return ResponseEntity.ok(contaPessoaJuridicaService.acompanhamentoStatusSolicitacao(cnpj));
    }


}
