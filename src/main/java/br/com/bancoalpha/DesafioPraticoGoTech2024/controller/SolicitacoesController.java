package br.com.bancoalpha.DesafioPraticoGoTech2024.controller;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridicaService;
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

    @GetMapping("/aprova/{id}")
    public ResponseEntity aprovaSolicitacao(@PathVariable Long id){
        contaPessoaJuridicaService.aprovaSolicitacao(id);
        return ResponseEntity.ok("Solicitação Aprovada com sucesso!");
    }

    @GetMapping("/cancela/{id}")
    public ResponseEntity cancelaSolicitacao(@PathVariable Long id){
        contaPessoaJuridicaService.cancelaSolicitacao(id);
        return ResponseEntity.ok("Solicitação Cancelada.");
    }
    @GetMapping("/status/{cnpj}")
    public ResponseEntity acompanhamentoSolicitacao(@PathVariable String cnpj){
        return ResponseEntity.ok("Status atual é: " + contaPessoaJuridicaService.acompanhamentoStatusSolicitacao(contaPessoaJuridicaService.converteCnpj(cnpj)));
    }
}
