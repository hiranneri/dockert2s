package model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "tipomovimentacoes")
public class TipoMovimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoMovimentacaoEnum nome;

    @OneToMany(mappedBy = "tipoMovimentacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movimentacao> movimentacao;

    public TipoMovimentacao() {
    }

    public TipoMovimentacao(Long id, TipoMovimentacaoEnum nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMovimentacaoEnum getNome() {
        return nome;
    }

    public void setNome(TipoMovimentacaoEnum nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoMovimentacao tipoMovimentacao = (TipoMovimentacao) o;
        return Objects.equals(id, tipoMovimentacao.id) && Objects.equals(nome, tipoMovimentacao.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
