package br.com.docker.t2s.service.abstracao;

import br.com.docker.t2s.controller.http.ConteinerRequestDTO;
import br.com.docker.t2s.controller.http.ConteinerResponseDTO;
import br.com.docker.t2s.model.Conteiner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConteinerService {

    ConteinerResponseDTO criar(ConteinerRequestDTO conteinerRequestDTO);
    ConteinerResponseDTO editar(ConteinerRequestDTO conteiner);

    ConteinerResponseDTO deletar(Long id);
    ConteinerResponseDTO buscarPeloNumero(String numero);
    ConteinerResponseDTO buscarPeloId(Long id);
    Conteiner buscarConteinerCompletoPeloNumero(String numero);

    List<ConteinerResponseDTO> buscarTodos();

    Page<ConteinerResponseDTO> buscarTodos(Pageable pageable);
}
