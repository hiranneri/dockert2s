package br.com.docker.t2s.controller;

import br.com.docker.t2s.controller.dtos.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.dtos.cliente.ClientePutRequestDTO;
import br.com.docker.t2s.controller.dtos.cliente.ClienteResponseDTO;
import br.com.docker.t2s.service.interfaces.ClienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes")
@SecurityRequirement(name = "Bearer Authentication")
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
    @PreAuthorize("hasRole('ADMIN')")
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
        return ResponseEntity.ok(clienteService.editar(cliente));
    }

    @Transactional(rollbackOn = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id){
        clienteService.desativar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
