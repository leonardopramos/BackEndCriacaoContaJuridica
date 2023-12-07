package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridica;
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
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @CPF
    private String cpf;
    @Email
    private String email;
    private double percentualParticipacao;
    @ManyToOne
    @JoinColumn(name = "conta_pessoa_juridica_id")
    private ContaPessoaJuridica contaPessoaJuridica;
}