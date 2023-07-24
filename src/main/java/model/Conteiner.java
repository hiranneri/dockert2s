package model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "conteineres")
public class Conteiner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String numero; // Código
    @Column(nullable = false)
    private String tipo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToOne(mappedBy = "conteiner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Movimentacao movimentacao;

    public Conteiner() {
    }

    public Conteiner(Long id, String numero, String tipo, Status status, Cliente cliente,
                     Categoria categoria) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.status = status;
        this.cliente = cliente;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Movimentacao getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(Movimentacao movimentacao) {
        this.movimentacao = movimentacao;
    }

    @Override
    public String toString() {
        return "Conteiner{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", tipo='" + tipo + '\'' +
                ", status='" + status + '\'' +
                ", cliente=" + cliente +
                ", categoria=" + categoria +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conteiner conteiner = (Conteiner) o;
        return Objects.equals(id, conteiner.id) && Objects.equals(numero, conteiner.numero) &&
                Objects.equals(tipo, conteiner.tipo) && Objects.equals(status, conteiner.status) &&
                Objects.equals(cliente, conteiner.cliente) && Objects.equals(categoria, conteiner.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero, tipo, status, cliente, categoria);
    }
}
