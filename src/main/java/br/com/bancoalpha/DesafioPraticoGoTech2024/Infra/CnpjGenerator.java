package br.com.bancoalpha.DesafioPraticoGoTech2024.Infra;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class CnpjGenerator {
    public String gerarCNPJ() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            builder.append(new Random().nextInt(10));
        }
        builder.append("0001");
        String cnpjParcial = builder.toString();
        int digito1 = calcularDigitoVerificador(cnpjParcial, 12);
        int digito2 = calcularDigitoVerificador(cnpjParcial + digito1, 13);
        builder.append(digito1).append(digito2);
        return builder.toString();
    }
    private static int calcularDigitoVerificador(String cnpjParcial, int peso) {
        int soma = 0;
        for (int i = 0; i < cnpjParcial.length(); i++) {
            int digito = Character.getNumericValue(cnpjParcial.charAt(i));
            soma += digito * peso--;
            if (peso == 1) {
                peso = 9;
            }
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }
}
