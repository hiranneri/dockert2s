package br.com.docker.t2s.controller;

import br.com.docker.t2s.controller.dtos.movimentacao.*;
import br.com.docker.t2s.service.interfaces.MovimentacaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("movimentacoes")
@RequiredArgsConstructor
@Tag(name = "Movimentações")
@SecurityRequirement(name = "Bearer Authentication")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @Transactional(rollbackOn = Exception.class)
    @PostMapping
    public ResponseEntity<MovimentacaoResponseDTO> criarMovimentacao(@RequestBody @Valid MovimentacaoPostRequestDTO movimentacaoPostRequestDTO) {
        return new ResponseEntity<>(movimentacaoService.criar(movimentacaoPostRequestDTO), HttpStatus.CREATED);
    }

    @Transactional(rollbackOn = Exception.class)
    @PatchMapping("/finalizar")
    public ResponseEntity<MovimentacaoPatchResponseDTO> finalizarMovimentacao(@RequestBody @Valid MovimentacaoPatchRequestDTO movimentacao) {
        return new ResponseEntity<>(movimentacaoService.finalizar(movimentacao), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoResponseDTO>> buscarMovimentacoes(){
        return ResponseEntity.ok(movimentacaoService.buscarMovimentacoes());
    }

    @Transactional(rollbackOn = Exception.class)
    @PutMapping("/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> editarMovimentacao(@PathVariable Long id,
            @RequestBody @Valid MovimentacaoPutRequestDTO movimentacaoPutRequestDTO){
        movimentacaoPutRequestDTO.setId(id);
        return ResponseEntity.ok(movimentacaoService.editarMovimentacao(movimentacaoPutRequestDTO));
    }

    @Transactional(rollbackOn = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMovimentacao(@PathVariable Long id){
        movimentacaoService.deletarMovimentacao(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/tipos")
    public ResponseEntity<List<TipoMovimentacaoResponseDTO>> criarTiposMovimentacao(@RequestBody @Valid TipoMovimentacaoListWrapper tiposDTO) {
        return new ResponseEntity<>(movimentacaoService.criarTiposMovimentacao(tiposDTO), HttpStatus.CREATED);
    }

}
