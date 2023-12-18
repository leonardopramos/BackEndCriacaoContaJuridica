package br.com.bancoalpha.DesafioPraticoGoTech2024.controller;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica.SocioService;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridicaResponseBody;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridicaService;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridica;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ContaPessoaJuridicaResponseBody> createContaPessoaJuridica(
            @RequestBody ContaPessoaJuridica contaPessoaJuridica) {
        contaPessoaJuridicaService.persistePessoaJuridica(contaPessoaJuridica);
        ContaPessoaJuridicaResponseBody responseBody = new ContaPessoaJuridicaResponseBody(contaPessoaJuridica);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String cnpj, @RequestParam String senha) {
        boolean loginSucesso = contaPessoaJuridicaService.login(cnpj, senha);
        return loginSucesso
                ? ResponseEntity.ok("Login bem sucedido.")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
    }
    @GetMapping("/nomeCliente/{cnpj}")
    public ResponseEntity retornaNomeCliente(@PathVariable String cnpj){
        return ResponseEntity.ok(contaPessoaJuridicaService.retornaNome(cnpj));
    }
}