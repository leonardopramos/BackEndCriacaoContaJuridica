package br.com.bancoalpha.DesafioPraticoGoTech2024.Infra;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPessoaJuridicaRepository extends JpaRepository<ContaPessoaJuridica, Long> {

}
