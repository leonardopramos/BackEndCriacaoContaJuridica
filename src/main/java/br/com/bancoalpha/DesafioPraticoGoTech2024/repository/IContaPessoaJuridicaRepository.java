package br.com.bancoalpha.DesafioPraticoGoTech2024.repository;

import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica.ContaPessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IContaPessoaJuridicaRepository extends JpaRepository<ContaPessoaJuridica, Long> {
    ContaPessoaJuridica findBycnpj(String cnpj);

    List<ContaPessoaJuridica> findAll();

    Optional<ContaPessoaJuridica> findById(String id);
}
