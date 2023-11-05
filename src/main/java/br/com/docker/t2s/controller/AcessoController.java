package br.com.docker.t2s.controller;

import br.com.docker.t2s.controller.dtos.login.LoginPostRequest;
import br.com.docker.t2s.controller.dtos.login.LoginPostResponse;
import br.com.docker.t2s.controller.dtos.registerUsuario.RegisterPostRequest;
import br.com.docker.t2s.controller.dtos.registerUsuario.RegisterPostResponse;
import br.com.docker.t2s.service.interfaces.AcessoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AcessoController {

    private final AcessoService acessoService;

    @PostMapping("/login")
    public ResponseEntity<LoginPostResponse> logar(@RequestBody @Valid LoginPostRequest login){
        return ResponseEntity.ok(acessoService.login(login));
    }

    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/register")
    public ResponseEntity<RegisterPostResponse> register(@RequestBody @Valid RegisterPostRequest registro){
        return new ResponseEntity<>(acessoService.cadastrar(registro), HttpStatus.CREATED);
    }
}
