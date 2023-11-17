package br.com.docker.t2s.model;

import br.com.docker.t2s.model.enums.StatusConteiner;
import br.com.docker.t2s.model.enums.TipoCategoria;
import br.com.docker.t2s.model.enums.TipoConteiner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "conteineres")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Conteiner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero; // Código

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoConteiner tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConteiner statusConteiner;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCategoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "conteiner", fetch = FetchType.LAZY)
    private List<Movimentacao> movimentacao;

    @Column(nullable = false)
    private boolean status;

    @PrePersist
    public void prePersist() {
        this.status = true;
    }

    @Override
    public String toString() {
        return "Conteiner{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", tipo=" + tipo +
                ", cliente=" + cliente +
                ", status=" + status +
                '}';
    }
}
