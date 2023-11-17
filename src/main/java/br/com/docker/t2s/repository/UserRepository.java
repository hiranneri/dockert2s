package br.com.docker.t2s.repository;

import br.com.docker.t2s.model.UsuarioDockerT2S;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsuarioDockerT2S, Long> {

    Optional<UsuarioDockerT2S> findByUsernameAndStatus(String username, boolean status);
}
