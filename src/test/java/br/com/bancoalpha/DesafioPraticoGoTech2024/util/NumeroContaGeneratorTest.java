package br.com.bancoalpha.DesafioPraticoGoTech2024.util;

import br.com.bancoalpha.DesafioPraticoGoTech2024.util.NumeroContaGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NumeroContaGeneratorTest {

    @Test
    public void testGerarNumeroConta() {
        NumeroContaGenerator numeroContaGenerator = new NumeroContaGenerator();

        // Teste para verificar se o número de conta está no intervalo esperado
        int numeroConta = numeroContaGenerator.gerar();
        assertTrue(numeroConta >= 1000000 && numeroConta <= 99999999,
                "Número de conta fora do intervalo esperado");

        // Teste para verificar se chamadas consecutivas geram números de conta diferentes
        int novoNumeroConta = numeroContaGenerator.gerar();
        assertNotEquals(numeroConta, novoNumeroConta, "Números de conta consecutivos não são diferentes");
    }
}

