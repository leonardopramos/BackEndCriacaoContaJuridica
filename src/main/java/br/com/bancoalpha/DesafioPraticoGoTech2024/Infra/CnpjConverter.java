package br.com.bancoalpha.DesafioPraticoGoTech2024.Infra;

import org.springframework.stereotype.Component;

@Component
public class CnpjConverter {
    public String formatCnpj(String cnpj) {
        if (cnpj.length() != 14) {
            throw new IllegalArgumentException("CNPJ deve conter exatamente 14 dígitos.");
        }

        return String.format("%s.%s.%s/%s-%s",
                cnpj.substring(0, 2),
                cnpj.substring(2, 5),
                cnpj.substring(5, 8),
                cnpj.substring(8, 12),
                cnpj.substring(12)
        );
    }
}
