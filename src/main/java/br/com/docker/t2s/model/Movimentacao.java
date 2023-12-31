package br.com.docker.t2s.model;

import br.com.docker.t2s.repository.dto.RelatorioMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@RelatorioMapper
@Entity
@Table(name = "movimentacoes")
@Builder
@Data
@AllArgsConstructor
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "dthr_inicio")
    private LocalDateTime dataHoraInicio;

    @Column(unique = true, name = "dthr_fim")
    private LocalDateTime dataHoraFim;

    @Column(nullable = false, unique = true, name = "dthr_updated")
    private LocalDateTime updatedAt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tipomovimentacao_id")
    private TipoMovimentacao tipoMovimentacao;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "conteiner_id", nullable = false)
    private Conteiner conteiner;

    @Column(nullable = false)
    private boolean status;

    @PrePersist
    public void prePersist() {
        if(this.dataHoraInicio == null) {
            this.dataHoraInicio = LocalDateTime.now();
        }
        this.updatedAt = LocalDateTime.now();
        this.status = true;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Movimentacao() {
    }

    @Override
    public String toString() {
        return "Movimentacao{" +
                "id=" + id +
                ", dataHoraInicio=" + dataHoraInicio +
                ", dataHoraFim=" + dataHoraFim +
                ", updatedAt=" + updatedAt +
                ", tipoMovimentacao=" + tipoMovimentacao +
                ", conteiner=" + conteiner +
                ", status=" + status +
                '}';
    }
}
