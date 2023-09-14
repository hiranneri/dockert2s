package br.com.docker.t2s.service.abstracao;

import br.com.docker.t2s.controller.http.ClienteRequestDTO;
import br.com.docker.t2s.controller.http.ClienteResponseDTO;
import br.com.docker.t2s.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {

    ClienteResponseDTO criar(ClienteRequestDTO cliente);

    ClienteResponseDTO editar(ClienteRequestDTO cliente);

    ClienteResponseDTO desativar(Long id);

    ClienteResponseDTO buscarPeloNome(String nome);

    Cliente buscarClienteCompleto(String nome);

    List<ClienteResponseDTO> buscarTodos();

    ClienteResponseDTO buscarPeloID(Long id);

    Page<ClienteResponseDTO> buscarTodos(Pageable pageable);
}
