package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica;

import java.util.UUID;

public record SocioListDTO(
        String id,
        String nome,
        String cpf,
        String telefone,
        double percentualParticipacao,
        String idConta) {
    public  SocioListDTO(Socio socio){
        this(socio.getId(), socio.getNome(), socio.getCpf(), socio.getTelefone(), socio.getPercentualParticipacao(), socio.getContaPessoaJuridica().getId());
    }
}
