package br.com.bancoalpha.DesafioPraticoGoTech2024.Infra;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaJuridica.ContaPessoaJuridica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPessoaJuridicaRepository extends JpaRepository<ContaPessoaJuridica, Long> {
    ContaPessoaJuridica findBycnpj(String cnpj);
    List<ContaPessoaJuridica> findAll();
}
