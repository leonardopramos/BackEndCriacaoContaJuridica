package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.Endereco;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica.Socio;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pessoa_juridica")
public class ContaPessoaJuridica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cnpj")
    private String cnpj;
    @Embedded
    private Endereco endereco;
    @Column(name = "faturamento_mensal")
    private double faturamentoMensal;
    @Column(name = "agencia")
    private int agencia;
    @Column(name = "numero_conta")
    private int numeroConta;
    @OneToMany(mappedBy = "contaPessoaJuridica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Socio> socios = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    private String senha;
    public String retornaStatusConvertido() {
        return switch (this.status) {
            case CANCELADA -> "Cancelada";
            case APROVADA -> "Aprovada.";
            default -> "Em andamento.";
        };
    }
}