package br.com.docker.t2s.controller.http.mappers.cliente;

import br.com.docker.t2s.controller.http.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.http.cliente.ClientePutRequestDTO;
import br.com.docker.t2s.controller.http.cliente.ClienteResponseDTO;
import br.com.docker.t2s.model.Cliente;

public class ClienteMapperImpl implements ClienteMapper{
    @Override
    public Cliente toCliente(ClientePostRequestDTO clienteRequestDTO) {
        if ( clienteRequestDTO == null ) {
            return null;
        }

        return Cliente.builder()
                .nome(clienteRequestDTO.getNome())
                .build();

    }

    @Override
    public Cliente toCliente(ClientePutRequestDTO clientePutRequestDTO) {
        if ( clientePutRequestDTO == null ) {
            return null;
        }

        return Cliente.builder()
                .id(clientePutRequestDTO.getId())
                .nome(clientePutRequestDTO.getNome())
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
