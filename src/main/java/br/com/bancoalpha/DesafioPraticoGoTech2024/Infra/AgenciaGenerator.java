package br.com.bancoalpha.DesafioPraticoGoTech2024.Infra;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AgenciaGenerator {
    private Random random = new Random();
    private int numAgencia = 0;
    public int gerar() {
        for (int i = 0; i < 4; i++) {
            numAgencia = numAgencia * 10 + random.nextInt(10);
        }
        return numAgencia;
    }
}
