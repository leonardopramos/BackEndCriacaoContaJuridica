package br.com.bancoalpha.DesafioPraticoGoTech2024.Infra;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AgenciaGenerator {
    private Random random = new Random();

    public int gerar() {
        int numeroAgencia = random.nextInt(10000);
        return Math.max(numeroAgencia, 0);
    }
}
