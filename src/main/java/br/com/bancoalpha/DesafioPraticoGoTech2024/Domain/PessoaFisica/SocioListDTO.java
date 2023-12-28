package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridica;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record SocioListDTO(
        Long id,
        String nome,
        String cpf,
        String telefone,
        double percentualParticipacao,
        Long idConta) {
    public  SocioListDTO(Socio socio){
        this(socio.getId(), socio.getNome(), socio.getCpf(), socio.getTelefone(), socio.getPercentualParticipacao(), socio.getContaPessoaJuridica().getId());
    }
}
