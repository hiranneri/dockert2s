package br.com.docker.t2s.controller.http.mappers.conteiner;

import br.com.docker.t2s.controller.http.ConteinerRequestDTO;
import br.com.docker.t2s.controller.http.ConteinerResponseDTO;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.model.enums.Status;
import br.com.docker.t2s.model.enums.StatusConteiner;
import br.com.docker.t2s.model.enums.TipoConteiner;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConteinerMapperImpl implements ConteinerMapper {

    @Override
    public Conteiner toConteiner(ConteinerRequestDTO conteinerRequestDTO) {
        if ( conteinerRequestDTO == null ) {
            return null;
        }

        return Conteiner.builder()
                .id(conteinerRequestDTO.getId())
                .tipo(TipoConteiner.fromRequest(conteinerRequestDTO.getTipo()))
                .numero(conteinerRequestDTO.getNumero())
                .statusConteiner(StatusConteiner.fromRequest(conteinerRequestDTO.getStatusConteiner()))
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
                .cliente(conteiner.getCliente().getNome())
                .numeroConteiner(conteiner.getNumero())
                .tipo(conteiner.getTipo().toString())
                .statusConteiner(conteiner.getStatusConteiner().toString())
                .build();
    }

    @Override
    public Conteiner toConteiner(ConteinerResponseDTO conteinerResponseDTO) {
        if ( conteinerResponseDTO == null ) {
            return null;
        }

        return Conteiner.builder()
                .tipo(TipoConteiner.valueOf(conteinerResponseDTO.getTipo()))
                .statusConteiner(StatusConteiner.valueOf(conteinerResponseDTO.getStatusConteiner()))
                .status(Status.INATIVO)
                .build();
    }
}
