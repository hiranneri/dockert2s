package br.com.docker.t2s.service.interfaces;

import br.com.docker.t2s.controller.http.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.http.cliente.ClientePutRequestDTO;
import br.com.docker.t2s.controller.http.cliente.ClienteResponseDTO;
import br.com.docker.t2s.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteService {

    ClienteResponseDTO criar(ClientePostRequestDTO cliente);

    ClienteResponseDTO editar(ClientePutRequestDTO cliente);

    ClienteResponseDTO desativar(Long id);

    ClienteResponseDTO buscarPeloNome(String nome);

    Cliente buscarClienteCompleto(String nome);

    List<ClienteResponseDTO> buscarTodos();

    ClienteResponseDTO buscarPeloID(Long id);

    Page<ClienteResponseDTO> buscarTodos(Pageable pageable);
}
