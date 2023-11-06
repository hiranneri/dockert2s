package br.com.docker.t2s.controller.dtos.mappers.conteiner;

import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerResponseDTO;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.model.enums.Status;
import br.com.docker.t2s.model.enums.StatusConteiner;
import br.com.docker.t2s.model.enums.TipoConteiner;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConteinerMapperImpl implements ConteinerMapper {

    @Override
    public Conteiner toConteiner(ConteinerPostRequestDTO conteinerPostRequestDTO) {
        if ( conteinerPostRequestDTO == null ) {
            return null;
        }

        return Conteiner.builder()
                .id(conteinerPostRequestDTO.getId())
                .tipo(TipoConteiner.fromRequest(conteinerPostRequestDTO.getTipo()))
                .numero(conteinerPostRequestDTO.getNumero())
                .statusConteiner(StatusConteiner.fromRequest(conteinerPostRequestDTO.getStatus()))
                .status(Status.ATIVO)
                .build();
    }
    @Override
    public ConteinerResponseDTO toConteinerResponse(Conteiner conteiner) {
        if ( conteiner == null ) {
            log.info("Não foi informado os dados do conteiner");
            return null;
        }

        return ConteinerResponseDTO.builder()
                .nomeCliente(conteiner.getCliente().getNome())
                .numeroConteiner(conteiner.getNumero())
                .tipo(conteiner.getTipo().toString())
                .categoria(conteiner.getCategoria().getNome().toString())
                .statusConteiner(conteiner.getStatusConteiner().toString())
                .status(conteiner.getStatus().toString())
                .build();
    }
}
