package br.com.docker.t2s.controller;

import br.com.docker.t2s.controller.http.MovimentacaoRequestDTO;
import br.com.docker.t2s.controller.http.MovimentacaoResponseDTO;
import br.com.docker.t2s.service.abstracao.MovimentacaoService;
import br.com.docker.t2s.utils.DataUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    // Desenvolver método finalizar movimentação com o visando marcar a data e hora fim da movimentação

    private final MovimentacaoService movimentacaoService;

    @Transactional(rollbackOn = Exception.class)
    @PostMapping
    public ResponseEntity<MovimentacaoResponseDTO> criarMovimentacao(@RequestBody MovimentacaoRequestDTO movimentacaoRequestDTO) {
        return new ResponseEntity<>(movimentacaoService.criar(movimentacaoRequestDTO), HttpStatus.CREATED);
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
    @PutMapping("/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> editarMovimentacao(@PathVariable Long id, @RequestBody
                                                                     @Valid MovimentacaoRequestDTO movimentacaoRequestDTO){
        movimentacaoRequestDTO.setId(id);
        return new ResponseEntity<>(movimentacaoService.editarMovimentacao(movimentacaoRequestDTO), HttpStatus.NO_CONTENT);
    }

    @Transactional(rollbackOn = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> excluirMovimentacao(@PathVariable Long id){
        return new ResponseEntity<>(movimentacaoService.deletarMovimentacao(id), HttpStatus.NO_CONTENT);
    }

}
