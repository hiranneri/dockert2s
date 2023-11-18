package br.com.docker.t2s.service;

import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPutRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerResponseDTO;
import br.com.docker.t2s.controller.dtos.mappers.conteiner.ConteinerMapper;
import br.com.docker.t2s.exceptions.responsehandler.BadRequestException;
import br.com.docker.t2s.model.Cliente;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.model.enums.StatusConteiner;
import br.com.docker.t2s.model.enums.TipoCategoria;
import br.com.docker.t2s.model.enums.TipoConteiner;
import br.com.docker.t2s.repository.ConteinerRepository;
import br.com.docker.t2s.service.interfaces.ClienteService;
import br.com.docker.t2s.service.interfaces.ConteinerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConteinerServiceImpl implements ConteinerService {

    private final ConteinerRepository conteinerRepository;
    private final ClienteService clienteService;

    @Override
    public ConteinerResponseDTO criar(ConteinerPostRequestDTO conteinerRequestDTO) {
        Conteiner conteiner = ConteinerMapper.criarConteinerMapper().toConteiner(conteinerRequestDTO);
        TipoCategoria tipoCategoria = pesquisarCategoria(conteinerRequestDTO.getCategoria());
        Cliente cliente = clienteService.buscarClienteCompleto(conteinerRequestDTO.getNomeCliente());

        conteiner.setCategoria(tipoCategoria);
        conteiner.setCliente(cliente);

        Conteiner conteinerSalvo = conteinerRepository.save(conteiner);

        ConteinerResponseDTO conteinerResponse = ConteinerMapper.criarConteinerMapper().toConteinerResponse(conteinerSalvo);
        conteinerResponse.setCategoria(conteinerRequestDTO.getCategoria());

        return conteinerResponse;
    }

    private TipoCategoria pesquisarCategoria(String nome) {
        return TipoCategoria.paraTipoCategoria(nome);
    }

    @Override
    public ConteinerResponseDTO editar(ConteinerPutRequestDTO conteinerInformado) {

        Cliente clienteLocalizado = clienteService.buscarClienteCompleto(conteinerInformado.getNomeCliente());
        TipoCategoria tipoCategoria = pesquisarCategoria(conteinerInformado.getCategoria());
        buscarPeloIDOuLancarExcecaoNaoEncontrado(conteinerInformado.getId());

        Conteiner conteinerASerEditado = Conteiner.builder()
                .id(conteinerInformado.getId())
                .categoria(tipoCategoria)
                .cliente(clienteLocalizado)
                .numero(conteinerInformado.getNumero())
                .tipo(TipoConteiner.fromRequest(conteinerInformado.getTipo()))
                .statusConteiner(StatusConteiner.valueOf(conteinerInformado.getStatus()))
                .status(true)
                .build();


        return ConteinerMapper.criarConteinerMapper().toConteinerResponse(conteinerRepository.save(conteinerASerEditado));
    }

    private Conteiner buscarPeloIDOuLancarExcecaoNaoEncontrado(Long id){
        return conteinerRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Conteiner não localizado"));

    }

    @Override
    public ConteinerResponseDTO deletar(Long id) {
        Conteiner conteinerLocalizado = pesquisarConteinerCompleto(id);

        conteinerLocalizado.setId(id);
        conteinerLocalizado.setStatus(false);

        return ConteinerMapper.criarConteinerMapper().toConteinerResponse(conteinerRepository.save(conteinerLocalizado));
    }

    private Conteiner pesquisarConteinerCompleto(Long id){
        return conteinerRepository.findById(id).orElseThrow(()-> new BadRequestException("Conteiner não encontrado"));
    }

    @Override
    public ConteinerResponseDTO buscarPeloNumero(String numero) {
        Conteiner conteinerLocalizado = conteinerRepository.findByNumeroAndStatus(numero,true ).orElseThrow(()->
                new BadRequestException("Conteiner não foi localizado"));
        return ConteinerMapper.criarConteinerMapper().toConteinerResponse(conteinerLocalizado);
    }

    @Override
    public ConteinerResponseDTO buscarPeloId(Long id) {
        return ConteinerMapper.criarConteinerMapper().toConteinerResponse(buscarPeloIDOuLancarExcecaoNaoEncontrado(id));
    }

    @Override
    public List<ConteinerResponseDTO> buscarTodos() {
        List<Conteiner> conteineres = conteinerRepository.findAll();
        List<ConteinerResponseDTO> conteineresResponse = new ArrayList<>();

        for(Conteiner conteiner: conteineres){
            conteineresResponse.add(ConteinerMapper.criarConteinerMapper().toConteinerResponse(conteiner));
        }
        return conteineresResponse;
    }

    public Page<ConteinerResponseDTO> buscarTodos(Pageable page) {
        List<Conteiner> conteineresLocalizados = conteinerRepository.findAll(page).getContent();
        List<ConteinerResponseDTO> conteineresResponse = new ArrayList<>();

        for(Conteiner conteiner: conteineresLocalizados){
            conteineresResponse.add(ConteinerMapper.criarConteinerMapper().toConteinerResponse(conteiner));
        }

        return new PageImpl<>(conteineresResponse);
    }

    @Override
    public Conteiner buscarConteinerCompletoPeloNumero(String numero) {
        return conteinerRepository.findByNumeroAndStatus(numero,true ).orElseThrow(()-> new BadRequestException("Conteiner não foi localizado"));
    }
}
