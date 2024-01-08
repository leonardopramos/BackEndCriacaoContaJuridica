package br.com.bancoalpha.DesafioPraticoGoTech2024.repository;

import br.com.bancoalpha.DesafioPraticoGoTech2024.model.PessoaJuridica.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAtividadesRepository extends JpaRepository<Atividade,Long> {
}
