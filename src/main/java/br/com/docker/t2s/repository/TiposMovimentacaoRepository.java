package br.com.docker.t2s.repository;

import br.com.docker.t2s.model.TipoMovimentacao;
import br.com.docker.t2s.model.enums.movimentacao.NomeMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TiposMovimentacaoRepository extends JpaRepository<TipoMovimentacao, Long> {

    Optional<TipoMovimentacao> findByNome(NomeMovimentacao nomeMovimentacao);
}
