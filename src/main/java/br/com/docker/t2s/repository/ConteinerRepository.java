package br.com.docker.t2s.repository;

import br.com.docker.t2s.model.Conteiner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConteinerRepository extends JpaRepository<Conteiner, Long> {

    Optional<Conteiner> findByNumeroAndStatus(String numero, boolean status);
}
