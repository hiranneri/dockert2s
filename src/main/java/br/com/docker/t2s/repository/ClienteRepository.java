package br.com.docker.t2s.repository;

import br.com.docker.t2s.model.Cliente;
import br.com.docker.t2s.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByNomeAndStatus(String nome, Status status);
    Optional<Cliente> findByIdAndStatus(Long id, Status status);
}
