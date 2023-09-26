package br.com.docker.t2s.service;

import br.com.docker.t2s.controller.http.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.http.conteiner.ConteinerPutRequestDTO;
import br.com.docker.t2s.controller.http.conteiner.ConteinerResponseDTO;
import br.com.docker.t2s.controller.http.mappers.conteiner.ConteinerMapper;
import br.com.docker.t2s.exceptions.BadRequestException;
import br.com.docker.t2s.model.Categoria;
import br.com.docker.t2s.model.Cliente;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.model.enums.Status;
import br.com.docker.t2s.model.enums.TipoCategoria;
import br.com.docker.t2s.repository.CategoriaRepository;
import br.com.docker.t2s.repository.ConteinerRepository;
import br.com.docker.t2s.service.abstracao.ClienteService;
import br.com.docker.t2s.service.abstracao.ConteinerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConteinerServiceImpl implements ConteinerService {

    private final ConteinerRepository conteinerRepository;
    private final ClienteService clienteService;
    private final CategoriaRepository categoriaRepository;

    @Override
    public ConteinerResponseDTO criar(ConteinerPostRequestDTO conteinerRequestDTO) {
        Conteiner conteiner = ConteinerMapper.INSTANCE.toConteiner(conteinerRequestDTO);
        Categoria categoria = pesquisarCategoria(conteinerRequestDTO.getCategoria());
        Cliente cliente = clienteService.buscarClienteCompleto(conteinerRequestDTO.getCliente());

        conteiner.setCategoria(categoria);
        conteiner.setCliente(cliente);
        Conteiner conteinerSalvo = conteinerRepository.save(conteiner);

        ConteinerResponseDTO conteinerResponse = ConteinerMapper.INSTANCE.toConteinerResponse(conteinerSalvo);
        conteinerResponse.setCategoria(conteinerRequestDTO.getCategoria());

        return conteinerResponse;
    }

    private Categoria pesquisarCategoria(String nome) {
        return categoriaRepository.findByNome(TipoCategoria.valueOf(nome));
    }

    @Override
    public ConteinerResponseDTO editar(ConteinerPutRequestDTO conteinerRequestDTO) {
        Conteiner conteinerLocalizado = buscarPeloIDOuLancarExcecaoNaoEncontrado(conteinerRequestDTO.getId());

        Cliente clienteLocalizado = clienteService.buscarClienteCompleto(conteinerRequestDTO.getCliente());
        Categoria categoriaLocalizada = pesquisarCategoria(conteinerRequestDTO.getCategoria());

        conteinerLocalizado.setCliente(clienteLocalizado);
        conteinerLocalizado.setCategoria(categoriaLocalizada);

        return ConteinerMapper.INSTANCE.toConteinerResponse(conteinerRepository.save(conteinerLocalizado));
    }

    private Conteiner buscarPeloIDOuLancarExcecaoNaoEncontrado(Long id){
        return conteinerRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Conteiner não localizado"));

    }

    @Override
    public ConteinerResponseDTO deletar(Long id) {
        Conteiner conteinerLocalizado = pesquisarConteinerCompleto(id);

        conteinerLocalizado.setId(id);
        conteinerLocalizado.setStatus(Status.INATIVO);

        return ConteinerMapper.INSTANCE.toConteinerResponse(conteinerRepository.save(conteinerLocalizado));
    }

    private Conteiner pesquisarConteinerCompleto(Long id){
        return conteinerRepository.findById(id).orElseThrow(()-> new BadRequestException("Conteiner não encontrado"));
    }

    @Override
    public ConteinerResponseDTO buscarPeloNumero(String numero) {
        Conteiner conteinerLocalizado = conteinerRepository.findByNumero(numero).orElseThrow(()->
                new BadRequestException("Conteiner não foi localizado"));
        return ConteinerMapper.INSTANCE.toConteinerResponse(conteinerLocalizado);
    }

    @Override
    public ConteinerResponseDTO buscarPeloId(Long id) {
        return ConteinerMapper.INSTANCE.toConteinerResponse(buscarPeloIDOuLancarExcecaoNaoEncontrado(id));
    }

    @Override
    public List<ConteinerResponseDTO> buscarTodos() {
        List<Conteiner> conteineres = conteinerRepository.findAll();
        List<ConteinerResponseDTO> conteineresResponse = new ArrayList<>();

        for(Conteiner conteiner: conteineres){
            conteineresResponse.add(ConteinerMapper.INSTANCE.toConteinerResponse(conteiner));
        }
        return conteineresResponse;
    }

    public Page<ConteinerResponseDTO> buscarTodos(Pageable page) {
        List<Conteiner> conteineresLocalizados = conteinerRepository.findAll(page).getContent();
        List<ConteinerResponseDTO> conteineresResponse = new ArrayList<>();

        for(Conteiner conteiner: conteineresLocalizados){
            conteineresResponse.add(ConteinerMapper.INSTANCE.toConteinerResponse(conteiner));
        }

        return new PageImpl<>(conteineresResponse);
    }

    @Override
    public Conteiner buscarConteinerCompletoPeloNumero(String numero) {
        return conteinerRepository.findByNumero(numero).orElseThrow(()-> new BadRequestException("Conteiner não foi localizado"));
    }
}
