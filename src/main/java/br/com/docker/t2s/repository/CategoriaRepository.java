package br.com.docker.t2s.repository;

import br.com.docker.t2s.model.Categoria;
import br.com.docker.t2s.model.enums.TipoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Categoria findByNome(TipoCategoria nome);
}
