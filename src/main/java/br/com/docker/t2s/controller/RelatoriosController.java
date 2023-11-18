package br.com.docker.t2s.controller;

import br.com.docker.t2s.repository.dto.RelatorioAgrupadoComSumarioDTO;
import br.com.docker.t2s.service.interfaces.MovimentacaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorios")
@Tag(name = "Relatórios")
@SecurityRequirement(name = "Bearer Authentication")
public class RelatoriosController {

    @Autowired
    MovimentacaoService movimentacaoService;

    @GetMapping("/movimentacoes")
    public ResponseEntity<RelatorioAgrupadoComSumarioDTO> gerarRelatorioMovimentacoesAgrupadas(@RequestParam(required = false) String campo,
                                                                                               @RequestParam(required = false) String ordenacao){
        return ResponseEntity.ok(movimentacaoService.gerarRelatoriosMovimentacoesAgrupadas(campo,ordenacao));
    }

}
