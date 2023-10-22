package br.com.docker.t2s.controller.dtos.mappers.conteiner;

import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerResponseDTO;
import br.com.docker.t2s.model.Conteiner;
import org.mapstruct.factory.Mappers;

public interface ConteinerMapper {

    ConteinerMapper INSTANCE = Mappers.getMapper(ConteinerMapper.class);
    Conteiner toConteiner(ConteinerPostRequestDTO conteinerPostRequestDTO);
    ConteinerResponseDTO toConteinerResponse(Conteiner conteiner);
    Conteiner toConteiner(ConteinerResponseDTO conteinerResponseDTO);
}
