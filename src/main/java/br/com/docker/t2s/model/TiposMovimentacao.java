package br.com.docker.t2s.model;

import br.com.docker.t2s.model.enums.MovimentacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "tiposmovimentacao")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TiposMovimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private MovimentacaoEnum nome;

    @OneToMany(mappedBy = "tiposMovimentacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<br.com.docker.t2s.model.Movimentacao> movimentacao;

}
