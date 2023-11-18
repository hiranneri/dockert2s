package br.com.docker.t2s.controller.dtos.mappers.cliente;

import br.com.docker.t2s.controller.dtos.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.dtos.cliente.ClientePutRequestDTO;
import br.com.docker.t2s.controller.dtos.cliente.ClienteResponseDTO;
import br.com.docker.t2s.model.Cliente;
import org.mapstruct.factory.Mappers;

public abstract class ClienteMapper {

    public static ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);
    abstract Cliente toCliente(ClientePostRequestDTO clientePostRequestDTO);
    abstract Cliente toCliente(ClientePutRequestDTO clientePutRequestDTO);
    abstract ClienteResponseDTO toClienteResponse(Cliente cliente);
    abstract Cliente toCliente(ClienteResponseDTO cliente);

    String converterStatus(boolean status){
        if(status){
            return "Ativo";
        }else {
            return "Inativo";
        }
    }

    public static ClienteMapperImpl criarClienteMapper(){
        return new ClienteMapperImpl();
    }

}
