package br.com.bancoalpha.DesafioPraticoGoTech2024.controller;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica.SocioService;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridicaService;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alpha")
public class ContaPessoaJuridicaController {
    @Autowired
    ContaPessoaJuridicaService contaPessoaJuridicaService;
    @Autowired
    SocioService socioService;

    @PostMapping("/create/contapessoajuridica")
    public ResponseEntity createContaPessoaJuridica(@RequestBody ContaPessoaJuridica contaPessoaJuridica){
        contaPessoaJuridicaService.persistePessoaJuridica(contaPessoaJuridica);
        return ResponseEntity.ok("Solicitação enviada com sucesso!");
    }

}