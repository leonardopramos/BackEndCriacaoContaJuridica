package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain;

import lombok.Data;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String body;
}
