package br.com.docker.t2s.controller.http.mappers.cliente;

import br.com.docker.t2s.controller.http.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.http.cliente.ClientePutRequestDTO;
import br.com.docker.t2s.controller.http.cliente.ClienteResponseDTO;
import br.com.docker.t2s.model.Cliente;
import org.mapstruct.factory.Mappers;

public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);
    Cliente toCliente(ClientePostRequestDTO clientePostRequestDTO);
    Cliente toCliente(ClientePutRequestDTO clientePutRequestDTO);
    ClienteResponseDTO toClienteResponse(Cliente cliente);
    Cliente toCliente(ClienteResponseDTO cliente);
}
