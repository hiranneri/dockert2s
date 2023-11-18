package br.com.docker.t2s.controller.dtos.cliente;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;


@Builder
@Data
public class ClienteResponseDTO {

    private String nome;
    private String status;


}
