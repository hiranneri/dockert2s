package br.com.docker.t2s.controller.dtos.mappers.conteiner;

import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerResponseDTO;
import br.com.docker.t2s.controller.dtos.mappers.cliente.ClienteMapperImpl;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.service.ConteinerServiceImpl;

public abstract class ConteinerMapper {
    abstract Conteiner toConteiner(ConteinerPostRequestDTO conteinerPostRequestDTO);
    abstract ConteinerResponseDTO toConteinerResponse(Conteiner conteiner);

    String converterStatus(boolean status){
        if(status){
            return "Ativo";
        }else {
            return "Inativo";
        }
    }

    public static ConteinerMapperImpl criarConteinerMapper(){
        return new ConteinerMapperImpl();
    }
}
