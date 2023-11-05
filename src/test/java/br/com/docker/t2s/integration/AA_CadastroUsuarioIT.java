package br.com.docker.t2s.integration;

import br.com.docker.t2s.IntegrationTest;
import br.com.docker.t2s.controller.dtos.registerUsuario.RegisterPostRequest;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@IntegrationTest
@AutoConfigureMockMvc
@Log4j2
@ContextConfiguration(initializers = TestContainerConfig.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AA_CadastroUsuarioIT {

    @Autowired
    protected MockMvc mockMvc;
    protected static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Create a login to do requests")
    public void AA_Cadastro() throws Exception {
        // teste secret
        RegisterPostRequest usuarioNovo = RegisterPostRequest.builder()
                .name("teste")
                .username("teste")
                .password("secret")
                .roles("ROLE_USER").build();

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(usuarioNovo))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.authorities").isNotEmpty())
                .andExpect(jsonPath("$.dataHoraCriacao").isNotEmpty());

    }
}
