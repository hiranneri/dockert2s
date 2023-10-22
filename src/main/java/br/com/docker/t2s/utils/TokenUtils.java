package br.com.docker.t2s.utils;

import br.com.docker.t2s.model.UsuarioDockerT2S;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;

public class TokenUtils {

    @Value("${api.security.token.secret}")
    private String secretToken;
    public String gerarToken(UsuarioDockerT2S usuario) {
        try {
            return JWT.create()
                    .withIssuer("DockerT2S")
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(DataUtils.dataHoraAtualMaisMinutos(15))
                    .sign(criarAlgoritmoHMAC256());

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
