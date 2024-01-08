package br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica;

import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaFisica.Socio;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contas_pj")
public class ContaPessoaJuridica {
    @Id
    @Column(name = "id", length = 36)
    private String id;
    @Column(name = "cnpj")
    private String cnpj;
    private String senha;
    @Column(name = "faturamento_mensal")
    private double faturamentoMensal;
    private String porte;
    @Column(name = "capital_social")
    private Double capitalSocial;
    @Column(name="data_de_abertura")
    private String dataDeAbertura;
    private String fantasia;
    @Column(name = "natureza_juridica")
    private String naturezaJuridica;
    @OneToMany(mappedBy = "contaPessoaJuridica", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Socio> socios = new ArrayList<>();
    @Embedded
    private Endereco endereco;
    @OneToMany(mappedBy = "contaPessoaJuridica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atividade> atividades = new ArrayList<>();
    @Column(name = "agencia")
    private int agencia;
    @Column(name = "numero_conta")
    private int numeroConta;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_solicitacao")
    private Status solicitacaoStatus;
    public String retornaStatusConvertido() {
        return switch (this.solicitacaoStatus) {
            case CANCELADA -> "Cancelada";
            case APROVADA -> "Aprovada.";
            default -> "Em andamento.";
        };
    }
}