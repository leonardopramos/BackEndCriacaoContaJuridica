package br.com.bancoalpha.DesafioPraticoGoTech2024.Domain.PessoaFisica;

import br.com.bancoalpha.DesafioPraticoGoTech2024.Infra.ISocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocioService {
    @Autowired
    ISocioRepository repository;
    public void persisteSocio(Socio socio) {
        repository.save(socio);
    }
}
