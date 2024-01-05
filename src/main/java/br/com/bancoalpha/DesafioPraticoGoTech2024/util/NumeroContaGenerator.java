package br.com.bancoalpha.DesafioPraticoGoTech2024.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class NumeroContaGenerator {
    private Random random = new Random();
    private int numConta = 0;
    public int gerar() {
        int numConta = random.nextInt(9) + 1;

        for (int i = 1; i < 8; i++) {
            numConta = numConta * 10 + random.nextInt(10);
        }

        return numConta;
    }
}
