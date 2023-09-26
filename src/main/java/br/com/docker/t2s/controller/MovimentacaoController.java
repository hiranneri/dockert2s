package br.com.docker.t2s.controller;

import br.com.docker.t2s.controller.http.movimentacao.MovimentacaoPostRequestDTO;
import br.com.docker.t2s.controller.http.movimentacao.MovimentacaoPutRequestDTO;
import br.com.docker.t2s.controller.http.movimentacao.MovimentacaoResponseDTO;
import br.com.docker.t2s.service.abstracao.MovimentacaoService;
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
@RequestMapping("movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @Transactional(rollbackOn = Exception.class)
    @PostMapping
    public ResponseEntity<MovimentacaoResponseDTO> criarMovimentacao(@RequestBody @Valid MovimentacaoPostRequestDTO movimentacaoPostRequestDTO) {
        return new ResponseEntity<>(movimentacaoService.criar(movimentacaoPostRequestDTO), HttpStatus.CREATED);
    }

    @Transactional(rollbackOn = Exception.class)
    @PostMapping
    public ResponseEntity<MovimentacaoResponseDTO> finalizarMovimentacao(@RequestBody @Valid MovimentacaoPostRequestDTO movimentacaoPostRequestDTO) {
        return new ResponseEntity<>(movimentacaoService.finalizar(movimentacaoPostRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoResponseDTO>> buscarMovimentacoes(){
        return ResponseEntity.ok(movimentacaoService.buscarMovimentacoes());

    }

    @GetMapping("/all")
    public ResponseEntity<Page<MovimentacaoResponseDTO>> buscarMovimentacoes(Pageable pageable){
        return ResponseEntity.ok(movimentacaoService.buscarMovimentacoes(pageable));
    }

    @Transactional(rollbackOn = Exception.class)
    @PutMapping()
    public ResponseEntity<MovimentacaoResponseDTO> editarMovimentacao(@RequestBody @Valid MovimentacaoPutRequestDTO movimentacaoPutRequestDTO){
        return new ResponseEntity<>(movimentacaoService.editarMovimentacao(movimentacaoPutRequestDTO), HttpStatus.NO_CONTENT);
    }

    @Transactional(rollbackOn = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> excluirMovimentacao(@PathVariable Long id){
        return new ResponseEntity<>(movimentacaoService.deletarMovimentacao(id), HttpStatus.NO_CONTENT);
    }



}
