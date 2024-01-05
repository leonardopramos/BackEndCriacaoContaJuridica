package br.com.bancoalpha.DesafioPraticoGoTech2024.util;

import br.com.bancoalpha.DesafioPraticoGoTech2024.util.AgenciaGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AgenciaGeneratorTest {

    @Test
    public void testGerarNumeroAgencia() {
        AgenciaGenerator agenciaGenerator = new AgenciaGenerator();
        int numeroAgencia = agenciaGenerator.gerar();
        assertTrue(numeroAgencia >= 0 && numeroAgencia < 10000, "Número de agência fora do intervalo esperado");
    }
    @Test
    public void testGerarNumerosAgenciaDiferentes() {
        AgenciaGenerator agenciaGenerator = new AgenciaGenerator();
        int numeroAgencia1 = agenciaGenerator.gerar();
        int numeroAgencia2 = agenciaGenerator.gerar();
        assertTrue(numeroAgencia1 != numeroAgencia2, "Números de agência não são diferentes");
    }
}