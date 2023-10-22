package br.com.docker.t2s.controller;

import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPutRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerResponseDTO;
import br.com.docker.t2s.service.interfaces.ConteinerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("conteiners")
@RequiredArgsConstructor
public class ConteinerController {

    private final ConteinerService conteinerService;

    @Transactional(rollbackOn = Exception.class)
    @PostMapping
    public ResponseEntity<ConteinerResponseDTO> criarConteiner(@RequestBody @Valid ConteinerPostRequestDTO conteiner){
        return new ResponseEntity<>(conteinerService.criar(conteiner), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ConteinerResponseDTO>> buscarConteineres(){
        return ResponseEntity.ok(conteinerService.buscarTodos());

    }

    @GetMapping("/all")
    public ResponseEntity<Page<ConteinerResponseDTO>> buscarConteineres(Pageable pageable){
        return ResponseEntity.ok(conteinerService.buscarTodos(pageable));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ConteinerResponseDTO> buscarConteinerPeloId(@PathVariable Long id){
        return new ResponseEntity<>(conteinerService.buscarPeloId(id), HttpStatus.OK);

    }

    @GetMapping("/search")
    public ResponseEntity<ConteinerResponseDTO> buscarConteinerPeloNome(@RequestParam String nome){
        return ResponseEntity.ok(conteinerService.buscarPeloNumero(nome));

    }

    @Transactional(rollbackOn = Exception.class)
    @PutMapping()
    public ResponseEntity<ConteinerResponseDTO> editarConteiner(@RequestBody @Valid ConteinerPutRequestDTO conteiner){
        return new ResponseEntity<>(conteinerService.editar(conteiner), HttpStatus.NO_CONTENT);

    }

    @Transactional(rollbackOn = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<ConteinerResponseDTO> excluirConteiner(@PathVariable Long id){
        return new ResponseEntity<>(conteinerService.deletar(id), HttpStatus.NO_CONTENT);
    }


}
