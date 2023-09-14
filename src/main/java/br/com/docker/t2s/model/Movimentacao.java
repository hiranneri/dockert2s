package br.com.docker.t2s.model;

import br.com.docker.t2s.model.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "movimentacoes")
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
    @JoinColumn(name = "tiposmovimentacao_id")
    private TiposMovimentacao tiposMovimentacao;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "conteiner_id", nullable = false)
    private Conteiner conteiner;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    public void prePersist() {
        if(this.dataHoraInicio == null) {
            this.dataHoraInicio = LocalDateTime.now();
        }
        this.updatedAt = LocalDateTime.now();
        this.status = Status.ATIVO;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Movimentacao() {
    }
}
