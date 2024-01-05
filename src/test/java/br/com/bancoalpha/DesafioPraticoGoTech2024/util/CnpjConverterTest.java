package br.com.bancoalpha.DesafioPraticoGoTech2024.util;

import br.com.bancoalpha.DesafioPraticoGoTech2024.util.CnpjConverter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CnpjConverterTest {

    @Test
    public void testFormatCnpj() {
        CnpjConverter cnpjConverter = new CnpjConverter();

        // Teste para um CNPJ válido
        String cnpjOriginal = "12345678901234";
        String cnpjFormatado = cnpjConverter.formatCnpj(cnpjOriginal);

        assertEquals("12.345.678/9012-34", cnpjFormatado, "Formato do CNPJ incorreto");

        // Teste para um CNPJ inválido (com menos de 14 dígitos)
        String cnpjInvalido = "123456789012";
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
                () -> cnpjConverter.formatCnpj(cnpjInvalido));
        assertEquals("CNPJ deve conter exatamente 14 dígitos.", exception1.getMessage(), "Exceção não lançada para CNPJ inválido");

        // Teste para um CNPJ inválido (com mais de 14 dígitos)
        String cnpjInvalido2 = "123456789012345";
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> cnpjConverter.formatCnpj(cnpjInvalido2));
        assertEquals("CNPJ deve conter exatamente 14 dígitos.", exception2.getMessage(), "Exceção não lançada para CNPJ inválido");
    }
}

