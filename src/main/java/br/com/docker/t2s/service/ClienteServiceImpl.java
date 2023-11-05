package br.com.docker.t2s.service;

import br.com.docker.t2s.controller.dtos.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.dtos.cliente.ClientePutRequestDTO;
import br.com.docker.t2s.controller.dtos.cliente.ClienteResponseDTO;
import br.com.docker.t2s.controller.dtos.mappers.cliente.ClienteMapper;
import br.com.docker.t2s.exceptions.responsehandler.BadRequestException;
import br.com.docker.t2s.model.Cliente;
import br.com.docker.t2s.model.enums.Status;
import br.com.docker.t2s.repository.ClienteRepository;
import br.com.docker.t2s.service.interfaces.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final Status statusAtivo = Status.ATIVO;

    @Override
    public ClienteResponseDTO criar(ClientePostRequestDTO clienteRequestDTO) {
        Cliente cliente = ClienteMapper.INSTANCE.toCliente(clienteRequestDTO);
        return ClienteMapper.INSTANCE.toClienteResponse(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponseDTO buscarPeloID(Long id){
        return buscarPeloIDOuLancarExcecaoNaoEncontrado(id);
    }

    public ClienteResponseDTO buscarPeloIDOuLancarExcecaoNaoEncontrado(Long id) {
        return ClienteMapper.INSTANCE.toClienteResponse(clienteRepository.findByIdAndStatus(id, statusAtivo)
                .orElseThrow(() -> new BadRequestException("Cliente não encontrado")));
    }

    @Override
    public ClienteResponseDTO editar(ClientePutRequestDTO clientePutRequestDTO) {
        buscarPeloIDOuLancarExcecaoNaoEncontrado(clientePutRequestDTO.getId());

        Cliente cliente = ClienteMapper.INSTANCE.toCliente(clientePutRequestDTO);

        return ClienteMapper.INSTANCE.toClienteResponse(clienteRepository.save(cliente));

    }

    /**
     * Para termos relatórios mais concisos, os registros salvos serão apenas desativados
     *
     * @param id dados do cliente que se deseja desativar
     * @return clienteResponseDTO
     */
    @Override
    public ClienteResponseDTO desativar(Long id) {
        ClienteResponseDTO clienteLocalizado = buscarPeloIDOuLancarExcecaoNaoEncontrado(id);

        Cliente cliente = ClienteMapper.INSTANCE.toCliente(clienteLocalizado);
        cliente.setId(id);
        cliente.setStatus(Status.INATIVO);

        clienteRepository.save(cliente);

        return ClienteMapper.INSTANCE.toClienteResponse(cliente);
    }

    @Override
    public ClienteResponseDTO buscarPeloNome(String nome) {
        return ClienteMapper.INSTANCE.toClienteResponse(clienteRepository.findByNomeAndStatus(nome,statusAtivo)
                .orElseThrow(() -> new BadRequestException("Cliente não encontrado")));
    }

    public Cliente buscarClienteCompleto(String nome) {
        return clienteRepository.findByNomeAndStatus(nome, statusAtivo).orElseThrow(() -> new BadRequestException("Cliente não encontrado"));
    }

    @Override
    public List<ClienteResponseDTO> buscarTodos() {
        List<Cliente> clientesLocalizados = clienteRepository.findAll();

        List<ClienteResponseDTO> clientesResponse = new ArrayList<>();

        for(Cliente cliente: clientesLocalizados){
            ClienteResponseDTO clienteResponse = ClienteMapper.INSTANCE.toClienteResponse(cliente);
            clientesResponse.add(clienteResponse);
        }
        return clientesResponse;
    }

    @Override
    public Page<ClienteResponseDTO> buscarTodos(Pageable pageable){
        List<Cliente> clientesLocalizados = clienteRepository.findAll(pageable).getContent();
        List<ClienteResponseDTO> clientesResponse = new ArrayList<>();

        for(Cliente cliente: clientesLocalizados){
            ClienteResponseDTO clienteResponse = ClienteMapper.INSTANCE.toClienteResponse(cliente);
            clientesResponse.add(clienteResponse);
        }

        return new PageImpl<>(clientesResponse);
    }
}
