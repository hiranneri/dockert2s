package br.com.docker.t2s.controller.http.mappers.cliente;

import br.com.docker.t2s.controller.http.ClienteRequestDTO;
import br.com.docker.t2s.controller.http.ClienteResponseDTO;
import br.com.docker.t2s.model.Cliente;
import org.mapstruct.factory.Mappers;

public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);
    Cliente toCliente(ClienteRequestDTO clientePostRequestDTO);
    ClienteResponseDTO toClienteResponse(Cliente cliente);
    Cliente toCliente(ClienteResponseDTO cliente);
}
