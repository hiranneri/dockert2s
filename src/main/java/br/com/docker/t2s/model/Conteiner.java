package br.com.docker.t2s.model;

import br.com.docker.t2s.model.enums.Status;
import br.com.docker.t2s.model.enums.StatusConteiner;
import br.com.docker.t2s.model.enums.TipoConteiner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "conteineres")
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "conteiner", fetch = FetchType.LAZY)
    private List<Movimentacao> movimentacao;

    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    public void prePersist() {
        this.status = Status.ATIVO;
    }

}
