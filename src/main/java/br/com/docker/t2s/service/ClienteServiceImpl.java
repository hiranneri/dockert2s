package br.com.docker.t2s.service;

import br.com.docker.t2s.controller.http.ClienteRequestDTO;
import br.com.docker.t2s.controller.http.ClienteResponseDTO;
import br.com.docker.t2s.controller.http.mappers.cliente.ClienteMapper;
import br.com.docker.t2s.exceptions.BadRequestException;
import br.com.docker.t2s.model.Cliente;
import br.com.docker.t2s.model.enums.Status;
import br.com.docker.t2s.repository.ClienteRepository;
import br.com.docker.t2s.service.abstracao.ClienteService;
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
    @Override
    public ClienteResponseDTO criar(ClienteRequestDTO clienteHTTPDTO) {
        Cliente cliente = ClienteMapper.INSTANCE.toCliente(clienteHTTPDTO);
        return ClienteMapper.INSTANCE.toClienteResponse(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponseDTO buscarPeloID(Long id){
        return buscarPeloIDOuLancarExcecaoNaoEncontrado(id);
    }

    public ClienteResponseDTO buscarPeloIDOuLancarExcecaoNaoEncontrado(Long id) {
        return ClienteMapper.INSTANCE.toClienteResponse(clienteRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Cliente não encontrado")));
    }

    @Override
    public ClienteResponseDTO editar(ClienteRequestDTO clienteHttp) {
        buscarPeloIDOuLancarExcecaoNaoEncontrado(clienteHttp.getId());
        Cliente cliente = ClienteMapper.INSTANCE.toCliente(clienteHttp);
        return ClienteMapper.INSTANCE.toClienteResponse(clienteRepository.save(cliente));

    }

    /**
     * Para termos relatórios mais concisos, os registros salvos serão apenas desativados
     *
     * @param id dados do cliente que se deseja desativar
     * @return clienteHTTPDTO
     */
    @Override
    public ClienteResponseDTO desativar(Long id) {
        ClienteResponseDTO clienteLocalizado = buscarPeloIDOuLancarExcecaoNaoEncontrado(id);
        Cliente cliente = ClienteMapper.INSTANCE.toCliente(clienteLocalizado);
        cliente.setId(id);
        cliente.setStatus(Status.INATIVO);
        clienteRepository.save(cliente);
        return clienteLocalizado;
    }

    @Override
    public ClienteResponseDTO buscarPeloNome(String nome) {
        return ClienteMapper.INSTANCE.toClienteResponse(clienteRepository.findByNome(nome)
                .orElseThrow(() -> new BadRequestException("Cliente não encontrado")));
    }

    public Cliente buscarClienteCompleto(String nome) {
        return clienteRepository.findByNome(nome).orElseThrow(() -> new BadRequestException("Cliente não encontrado"));
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
