package br.com.docker.t2s.repository;

import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.model.Movimentacao;
import br.com.docker.t2s.model.TipoMovimentacao;
import br.com.docker.t2s.repository.dto.ResultadoRelatorioDTO;
import br.com.docker.t2s.repository.dto.SumarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    Optional<Movimentacao> findByDataHoraInicioAndStatus(LocalDateTime dataHoraInicio, boolean status);
    Optional<Movimentacao> findByTipoMovimentacaoAndConteinerAndStatus(TipoMovimentacao tipoMovimentacao,
                                                                       Conteiner conteiner,
                                                                       boolean status);

    // ***************************************************
    // Relatórios
    // ***************************************************
    String QUERY_PADRAO = "SELECT c.nome as cliente, ti.nome as tipoMovimentacao,COUNT(*) as totalMovimentacoes \n" +
            "from tiposmovimentacao ti\n" +
            "inner join movimentacoes m on ti.id = m.tipomovimentacao_id \n" +
            "inner join conteineres co on m.conteiner_id  = co.id  \n" +
            "inner join clientes c on co.cliente_id = c.id\n" +
            "WHERE m.status = 1\n" +
            "GROUP BY c.nome, ti.id \n";

    @Query(value = QUERY_PADRAO +"ORDER BY c.nome ASC", nativeQuery = true)
    List<ResultadoRelatorioDTO> obterTotalAgrupadoPorClienteETipoOrdenadoPorClienteASC();

    @Query(value = QUERY_PADRAO +"ORDER BY tipoMovimentacao ASC", nativeQuery = true)
    List<ResultadoRelatorioDTO> obterTotalAgrupadoPorClienteETipoOrdenadoPorTipoMovimentacaoASC();

    @Query(value = QUERY_PADRAO +"ORDER BY c.nome DESC", nativeQuery = true)
    List<ResultadoRelatorioDTO> obterTotalAgrupadoPorClienteETipoOrdenadoPorClienteDESC();

    @Query(value = QUERY_PADRAO +"ORDER BY tipoMovimentacao DESC", nativeQuery = true)
    List<ResultadoRelatorioDTO> obterTotalAgrupadoPorClienteETipoOrdenadoPorTipoMovimentacaoDESC();

    @Query(value = "SELECT categoria as nomeCategoria, COUNT(c.id) as totalCategoria " +
            "from movimentacoes m\n" +
            "inner join conteineres c \n" +
            "on m.conteiner_id = c.id \n" +
            "WHERE m.status = 1\n" +
            "GROUP by c.id;", nativeQuery = true)
    List<SumarioDTO> obterTotalAgrupadoPorTipoMovimentacao();


}
