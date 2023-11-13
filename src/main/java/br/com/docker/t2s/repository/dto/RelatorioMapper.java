package br.com.docker.t2s.repository.dto;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;

@SqlResultSetMapping(
        name = "ResultadoRelatorioDTOMapping",
        classes = @ConstructorResult(
                targetClass = ResultadoRelatorioDTO.class,
                columns = {
                        @ColumnResult(name = "cliente", type = String.class),
                        @ColumnResult(name = "tipoMovimentacao", type = String.class),
                        @ColumnResult(name = "totalMovimentacoes", type = Long.class)
                }
        )
)

@SqlResultSetMapping(
        name = "SummaryMapping",
        classes = @ConstructorResult(
                targetClass = SumarioDTO.class,
                columns = {
                        @ColumnResult(name = "nomeCategoria", type = String.class),
                        @ColumnResult(name = "totalCategoria", type = Long.class)
                }
        )
)
public @interface RelatorioMapper {
}
