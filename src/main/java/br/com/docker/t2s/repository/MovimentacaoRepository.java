package br.com.docker.t2s.repository;

import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.model.Movimentacao;
import br.com.docker.t2s.model.TipoMovimentacao;
import br.com.docker.t2s.repository.dto.ResultadoRelatorioDTO;
import br.com.docker.t2s.repository.dto.SumarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    Optional<Movimentacao> findByDataHoraInicio(LocalDateTime dataHoraInicio);
    Optional<Movimentacao> findByTipoMovimentacaoAndConteiner(TipoMovimentacao tipoMovimentacao, Conteiner conteiner);

    // ***************************************************
    // Relatórios
    // ***************************************************
    @Query(value = "SELECT c.nome as cliente,\n" +
            "ti.nome as tipoMovimentacao,\n" +
            "COUNT(*) as totalMovimentacoes\n" +
            "from tiposmovimentacao ti\n" +
            "inner join movimentacoes m on ti.id = m.tipomovimentacao_id \n" +
            "inner join conteineres co on m.conteiner_id  = co.id  \n" +
            "inner join clientes c on co.cliente_id = c.id\n" +
            "WHERE m.status = 'ATIVO'\n" +
            "GROUP BY c.nome, ti.id", nativeQuery = true)
    List<ResultadoRelatorioDTO> obterTotalAgrupadoPorClienteETipo();


    @Query(value = "SELECT nome as nomeCategoria, COUNT(ca.id) as totalCategoria\n" +
            "from conteineres co\n" +
            "inner join categorias ca \n" +
            "on ca.id = co.categoria_id\n" +
            "GROUP by ca.id ;", nativeQuery = true)
    List<SumarioDTO> obterTotalAgrupadoPorTipoMovimentacao();


}
