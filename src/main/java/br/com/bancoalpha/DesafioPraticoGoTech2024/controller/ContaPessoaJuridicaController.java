package br.com.bancoalpha.DesafioPraticoGoTech2024.controller;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.EmailRequest;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica.Socio;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica.SocioService;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridicaResponseBody;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridicaService;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alpha")
public class ContaPessoaJuridicaController {
    @Autowired
    ContaPessoaJuridicaService contaPessoaJuridicaService;
    @Autowired
    SocioService socioService;

    @PostMapping("/create")
    public ResponseEntity<ContaPessoaJuridicaResponseBody> createContaPessoaJuridica(
            @RequestBody ContaPessoaJuridica contaPessoaJuridica) {
        contaPessoaJuridicaService.persistePessoaJuridica(contaPessoaJuridica);
        ContaPessoaJuridicaResponseBody responseBody = new ContaPessoaJuridicaResponseBody(contaPessoaJuridica);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String cnpj, @RequestParam String senha) {
        boolean loginSucesso = contaPessoaJuridicaService.login(cnpj, senha);
        return loginSucesso
                ? ResponseEntity.ok("Login bem sucedido.")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
    }

    @GetMapping("/all")
    public List<ContaPessoaJuridica> listarContasPessoaJuridica() {
        return contaPessoaJuridicaService.listarContasPessoaJuridica();
    }

    @PostMapping("/sendEmail/{cnpj}")
    public String sendEmail(@RequestBody EmailRequest emailRequest, @PathVariable String cnpj){
        return contaPessoaJuridicaService.enviarEmail(emailRequest, cnpj);
    }

    @PostMapping("/addsocio/{cnpj}")
    public ResponseEntity<Void> createSocio(@PathVariable String cnpj, @RequestBody Socio socio) throws Exception {
        socioService.adicionaNovoSocio(socio, cnpj);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/users/{cnpj}")
    public List<Socio> listUsers(@PathVariable String cnpj) {
        return contaPessoaJuridicaService.listUsers(cnpj);
    }
}