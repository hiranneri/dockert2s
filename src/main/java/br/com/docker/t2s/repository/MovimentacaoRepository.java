package br.com.docker.t2s.repository;

import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.model.Movimentacao;
import br.com.docker.t2s.model.TipoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    Optional<Movimentacao> findByDataHoraInicio(LocalDateTime dataHoraInicio);

    boolean existsByTipoMovimentacaoAndConteiner(TipoMovimentacao tipoMovimentacao, Conteiner conteiner);

}
