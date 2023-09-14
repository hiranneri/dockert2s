package br.com.docker.t2s.model;

import br.com.docker.t2s.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity(name="clientes")
@Builder
@Data
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Conteiner> conteiner;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Cliente() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        this.status = Status.ATIVO;
    }
}
