package br.com.docker.t2s.controller.http.cliente;

import br.com.docker.t2s.model.enums.Status;
import lombok.Builder;

import java.util.Objects;


@Builder
public class ClienteResponseDTO {

    private String nome;
    private Status status;

    public ClienteResponseDTO(String nome, Status status) {
        this.nome = nome;
        this.status = status;
    }

    public ClienteResponseDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status statusConteiner) {
        this.status = statusConteiner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteResponseDTO that = (ClienteResponseDTO) o;
        return Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return "ClienteResponseDTO{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
