package br.com.docker.t2s.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="clientes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Conteiner> conteiner;

    @Column(nullable = false)
    private boolean status;

    @PrePersist
    public void prePersist() {
        this.status = true;
    }

    public boolean isStatusAtivo() {
        return status;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", status=" + status +
                '}';
    }
}
