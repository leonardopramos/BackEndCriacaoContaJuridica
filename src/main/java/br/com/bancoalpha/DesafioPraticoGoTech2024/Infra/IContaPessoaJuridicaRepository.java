package br.com.bancoalpha.DesafioPraticoGoTech2024.Infra;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IContaPessoaJuridicaRepository extends JpaRepository<ContaPessoaJuridica, Long> {
    ContaPessoaJuridica findBycnpj(String cnpj);

    List<ContaPessoaJuridica> findAll();

    Optional<ContaPessoaJuridica> findById(String id);
}
