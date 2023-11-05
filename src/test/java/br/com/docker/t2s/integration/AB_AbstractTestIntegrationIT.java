package br.com.docker.t2s.integration;

import br.com.docker.t2s.IntegrationTest;
import br.com.docker.t2s.controller.dtos.login.LoginPostRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static br.com.docker.t2s.utils.ExtracaoUtils.extrairDadoDesejadoResponseBody;
import static br.com.docker.t2s.utils.ExtracaoUtils.regexToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@IntegrationTest
@AutoConfigureMockMvc
@Log4j2
@ContextConfiguration(initializers = TestContainerConfig.class)
@TestMethodOrder(MethodOrderer.MethodName.class)

/**
 * É executada a partir da AC_*IT.java
 */
public abstract class AB_AbstractTestIntegrationIT {

    @Autowired
    protected MockMvc mockMvc;
    protected static String tokenAcesso = "";
    protected static ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("Log in to generate a token")
    public void AA_Login() throws Exception {
        // teste secret
        LoginPostRequest usuarioNovo = LoginPostRequest.builder()
                .username("teste")
                .senha("secret").build();

        ResultActions result = mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(usuarioNovo))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());

        String responseBody = result.andReturn().getResponse().getContentAsString();
        tokenAcesso = extrairDadoDesejadoResponseBody(responseBody,regexToken);

    }


}
