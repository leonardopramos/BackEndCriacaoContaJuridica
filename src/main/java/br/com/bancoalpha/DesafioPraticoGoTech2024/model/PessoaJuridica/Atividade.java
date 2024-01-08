package br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "atividades")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String text;
    private String tipo;
    @ManyToOne
    @JoinColumn(name = "conta_pessoa_juridica_id")
    @JsonBackReference
    private ContaPessoaJuridica contaPessoaJuridica;
}