package br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaFisica;

import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica.ContaPessoaJuridica;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "Socios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socio {
    @Id@Column(name = "id", length = 36)
    private String id;
    private String nome;
    @CPF
    private String cpf;
    @Email
    private String email;
    private String telefone;
    private double percentualParticipacao;
    @ManyToOne
    @JoinColumn(name = "conta_pessoa_juridica_id")
    @JsonBackReference
    private ContaPessoaJuridica contaPessoaJuridica;
}