package br.com.docker.t2s.utils;

import br.com.docker.t2s.model.UsuarioDockerT2S;
import br.com.docker.t2s.service.dto.TokenDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenUtils {

    @Value("${api.security.token.secret}")
    private String secretToken;
    public TokenDTO gerarToken(UsuarioDockerT2S usuario) {
        try {
            String token = JWT.create()
                    .withIssuer("DockerT2S")
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(DataUtils.dataHoraAtualMaisMinutos(60))
                    .sign(criarAlgoritmoHMAC256());

            return TokenDTO.builder()
                    .token(token)
                    .tempoExpiracaoEmMinutos(60L).build();

        }catch (JWTCreationException jwtException) {
            throw new RuntimeException("Erro durante a criação do token", jwtException);

        }
    }

    private Algorithm criarAlgoritmoHMAC256() {
        return Algorithm.HMAC256(secretToken);
    }

    /**
     * Realiza a validação do token informado retornando o subject dele
     * @param tokenASerValidado token que será validado
     * @return subject
     */
    public String obterUsernameNoTokenValido(String tokenASerValidado){
        try {
            return JWT.require(criarAlgoritmoHMAC256())
                    .withIssuer("DockerT2S")
                    .build()
                    .verify(tokenASerValidado)
                    .getSubject();

        }catch (JWTVerificationException jwtVerificationException){
            return "";
        }
    }
}
