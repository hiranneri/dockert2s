package br.com.docker.t2s.controller.http;

import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Builder
public class ClienteRequestDTO {

    Long id;
    @NotEmpty(message = "O campo nome é obrigatório")
    String nome;

    public ClienteRequestDTO() {
    }

    public ClienteRequestDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteRequestDTO that = (ClienteRequestDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "ClienteRequestDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
