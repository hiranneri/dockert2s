package br.com.docker.t2s.controller.http.mappers.conteiner;

import br.com.docker.t2s.controller.http.ConteinerRequestDTO;
import br.com.docker.t2s.controller.http.ConteinerResponseDTO;
import br.com.docker.t2s.model.Conteiner;
import org.mapstruct.factory.Mappers;

public interface ConteinerMapper {

    ConteinerMapper INSTANCE = Mappers.getMapper(ConteinerMapper.class);
    Conteiner toConteiner(ConteinerRequestDTO conteinerRequestDTO);
    ConteinerResponseDTO toConteinerResponse(Conteiner conteiner);
    Conteiner toConteiner(ConteinerResponseDTO conteinerResponseDTO);
}
