package br.com.docker.t2s.controller.http.mappers.cliente;

import br.com.docker.t2s.controller.http.ClienteRequestDTO;
import br.com.docker.t2s.controller.http.ClienteResponseDTO;
import br.com.docker.t2s.model.Cliente;

public class ClienteMapperImpl implements ClienteMapper{
    @Override
    public Cliente toCliente(ClienteRequestDTO clienteRequestDTO) {
        if ( clienteRequestDTO == null ) {
            return null;
        }

        return Cliente.builder()
                .id(clienteRequestDTO.getId())
                .nome(clienteRequestDTO.getNome())
                .build();

    }

    @Override
    public ClienteResponseDTO toClienteResponse(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        return ClienteResponseDTO.builder()
                .nome(cliente.getNome())
                .status(cliente.getStatus())
                .build();
    }

    @Override
    public Cliente toCliente(ClienteResponseDTO clienteResponseDTO) {
        if ( clienteResponseDTO == null ) {
            return null;
        }

        return Cliente.builder()
                .nome(clienteResponseDTO.getNome())
                .build();
    }
}
