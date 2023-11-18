package br.com.docker.t2s.controller;

import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPutRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerResponseDTO;
import br.com.docker.t2s.service.interfaces.ConteinerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Conteineres")
@SecurityRequirement(name = "Bearer Authentication")
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
    @PutMapping("/{id}")
    public ResponseEntity<ConteinerResponseDTO> editarConteiner(@PathVariable Long id,
                                                                @RequestBody @Valid ConteinerPutRequestDTO conteiner){
        conteiner.setId(id);
        return ResponseEntity.ok(conteinerService.editar(conteiner));
    }

    @Transactional(rollbackOn = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirConteiner(@PathVariable Long id){
        conteinerService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
