package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.Endereco;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica.Socio;
import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
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

    @Column(name = "razao_social")
    private String razaoSocial;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Embedded
    private Endereco endereco;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "faturamento_mensal")
    private double faturamentoMensal;

    @Column(name = "ramo_atividade")
    private String ramoAtividade;

    @Column(name = "agencia")
    private int agencia;

    @Column(name = "numero_conta")
    private int numeroConta;

    @OneToMany(mappedBy = "contaPessoaJuridica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Socio> socios = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}

