package br.com.docker.t2s.service.interfaces;

import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPutRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerResponseDTO;
import br.com.docker.t2s.model.Conteiner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConteinerService {

    ConteinerResponseDTO criar(ConteinerPostRequestDTO conteinerRequestDTO);
    ConteinerResponseDTO editar(ConteinerPutRequestDTO conteiner);

    ConteinerResponseDTO deletar(Long id);
    ConteinerResponseDTO buscarPeloNumero(String numero);
    ConteinerResponseDTO buscarPeloId(Long id);
    Conteiner buscarConteinerCompletoPeloNumero(String numero);

    List<ConteinerResponseDTO> buscarTodos();

    Page<ConteinerResponseDTO> buscarTodos(Pageable pageable);
}
