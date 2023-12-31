package br.com.docker.t2s.model;

import br.com.docker.t2s.model.enums.movimentacao.NomeMovimentacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tiposmovimentacao")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoMovimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private NomeMovimentacao nome;

    @OneToMany(mappedBy = "tipoMovimentacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movimentacao> movimentacao;

    @Column(nullable = false)
    private boolean status;

    @PrePersist
    public void prePersist() {
        this.status = true;
    }

    @Override
    public String toString() {
        return "TipoMovimentacao{" +
                "id=" + id +
                ", nome=" + nome +
                '}';
    }
}
