package br.com.bancoalpha.DesafioPraticoGoTech2024.Infra;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AgenciaGenerator {
    public int gerar() {
        Random random = new Random();
        int numAgencia = 0;
        for (int i = 0; i < 4; i++) {
            numAgencia = numAgencia * 10 + random.nextInt(10);
        }
        return numAgencia;
    }
}
