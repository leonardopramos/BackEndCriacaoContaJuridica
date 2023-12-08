package br.com.bancoalpha.DesafioPraticoGoTech2024.Infra;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class NumeroContaGenerator {
    public int gerar() {
        Random random = new Random();
        int numConta = 0;
        while (numConta == 0) {
            numConta = random.nextInt(10);
        }
        for (int i = 1; i < 9; i++) {
            numConta = numConta * 10 + random.nextInt(10);
        }
        return numConta;
    }
}
