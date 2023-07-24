package model;

import utils.DataUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "movimentacoes")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String datahorainicio;
    @Column(unique = true)
    private String datahorafim;
    @Column(nullable = false, unique = true)
    private String updated_At;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoMovimentacao_id")
    private TipoMovimentacao tipoMovimentacao;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "conteiner_id", nullable = false)
    private Conteiner conteiner;

    @PrePersist
    public void prePersist() {
        this.datahorainicio = DataUtils.dataHoraAtual();
        this.updated_At = DataUtils.dataHoraAtual();
    }

    @PreUpdate
    public void preUpdate() {
        this.updated_At = DataUtils.dataHoraAtual();
    }

    public Movimentacao() {
    }

    public Movimentacao(Long id, String datahorafim, TipoMovimentacao tipoMovimentacao) {
        this.id = id;
        this.datahorafim = datahorafim;
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatahorainicio() {
        return datahorainicio;
    }

    public void setDatahorainicio(String datahorainicio) {
        this.datahorainicio = datahorainicio;
    }

    public String getUpdated_At() {
        return updated_At;
    }

    public void setUpdated_At(String updated_At) {
        this.updated_At = updated_At;
    }

    public String getDatahorafim() {
        return datahorafim;
    }

    public void setDatahorafim(String datahorafim) {
        this.datahorafim = datahorafim;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public Conteiner getConteiner() {
        return conteiner;
    }

    public void setConteiner(Conteiner conteiner) {
        this.conteiner = conteiner;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    @Override
    public String toString() {
        return "Movimentacao{" +
                "id=" + id +
                ", createdAt=" + datahorainicio +
                ", updatedAt=" + updated_At +
                ", endupAt=" + datahorafim +
                ", tipoMovimentacao=" + tipoMovimentacao +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movimentacao that = (Movimentacao) o;
        return Objects.equals(id, that.id) && Objects.equals(datahorainicio, that.datahorainicio) && Objects.equals(updated_At, that.updated_At) && Objects.equals(datahorafim, that.datahorafim) && Objects.equals(tipoMovimentacao, that.tipoMovimentacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datahorainicio, updated_At, datahorafim, tipoMovimentacao);
    }
}
