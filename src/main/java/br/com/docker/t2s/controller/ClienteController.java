package br.com.docker.t2s.controller;

import br.com.docker.t2s.controller.http.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.http.cliente.ClientePutRequestDTO;
import br.com.docker.t2s.controller.http.cliente.ClienteResponseDTO;
import br.com.docker.t2s.service.abstracao.ClienteService;
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
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @Transactional(rollbackOn = Exception.class)
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criarCliente(@RequestBody @Valid ClientePostRequestDTO clientePostRequestDTO){
        return new ResponseEntity<>(clienteService.criar(clientePostRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> buscarClientes(){
        return ResponseEntity.ok(clienteService.buscarTodos());
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ClienteResponseDTO>> buscarClientes(Pageable pageable){
        return ResponseEntity.ok(clienteService.buscarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClientPeloId(@PathVariable Long id){
        return new ResponseEntity<>(clienteService.buscarPeloID(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ClienteResponseDTO> buscarClientePeloNome(@RequestParam String nome){
        return ResponseEntity.ok(clienteService.buscarPeloNome(nome));
    }

    @Transactional(rollbackOn = Exception.class)
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> editarCliente(@PathVariable Long id,
                                                            @RequestBody @Valid ClientePutRequestDTO cliente){
        cliente.setId(id);
        return new ResponseEntity<>(clienteService.editar(cliente), HttpStatus.NO_CONTENT);
    }

    @Transactional(rollbackOn = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> excluirCliente(@PathVariable Long id){
        return new ResponseEntity<>(clienteService.desativar(id), HttpStatus.NO_CONTENT);
    }


}
