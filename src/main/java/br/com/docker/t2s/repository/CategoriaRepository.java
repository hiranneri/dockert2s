package br.com.docker.t2s.repository;

import br.com.docker.t2s.model.Categoria;
import br.com.docker.t2s.model.enums.TipoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNome(TipoCategoria nome);
}
