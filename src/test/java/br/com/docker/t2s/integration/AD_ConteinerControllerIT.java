package br.com.docker.t2s.integration;

import br.com.docker.t2s.IntegrationTest;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPutRequestDTO;
import br.com.docker.t2s.model.Cliente;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.utils.ConteinerCreator;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@IntegrationTest
@AutoConfigureMockMvc
@Log4j2
@ContextConfiguration(initializers = TestContainerConfig.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AD_ConteinerControllerIT extends TestIntegrationIT {

    @Autowired
    protected MockMvc mockMvc;
    protected static ObjectMapper objectMapper = new ObjectMapper();
    private final Long ID_Conteiner = 1L;

    ConteinerPostRequestDTO conteinerASerSalvo = ConteinerCreator.createConteinerPostRequestValido();
    Cliente emirates = new Cliente(null, "Emirates", null, true);

    @Test
    @DisplayName("Create a client")
    void AB_CriarClienteDeveRetornarClienteSalvoComStatusAtivo() throws Exception {
        mockMvc.perform(
                post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emirates))
                        .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
        ).andExpect(status().isCreated())
        .andExpect(jsonPath("$.status").value("Ativo"))
        .andExpect(jsonPath("$.nome").value(emirates.getNome()));

    }

    @Test
    @DisplayName("Create a conteiner")
    void AC_CriarConteinerDeveRetornarConteinerSalvoComStatusAtivo() throws Exception {

        mockMvc.perform(
                post("/conteiners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conteinerASerSalvo))
                        .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("Ativo"))
                .andExpect(jsonPath("$.numeroConteiner").value(conteinerASerSalvo.getNumero()));

    }
    @Test
    @DisplayName("Search for conteiner by id")
    void AD_PesquisarUmConteinerDeveRetornarUmConteinerSalvoComStatusAtivo() throws Exception {

        mockMvc.perform(
                        get("/conteiners/" + ID_Conteiner)
                                .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroConteiner").isNotEmpty())
                .andExpect(jsonPath("$.status").value("Ativo"));
    }

    @Test
    @DisplayName("Edit a conteiner")
    void AE_EditarUmConteinerDeveRetornarConteinerEditadoComStatusAtivo() throws Exception {
        ConteinerPutRequestDTO conteinerASerEditado = ConteinerCreator.createConteinerPutRequestValido();
        conteinerASerEditado.setNomeCliente(emirates.getNome());

        mockMvc.perform(
                        put("/conteiners/"+ID_Conteiner)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(conteinerASerEditado))
                                .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroConteiner").value(conteinerASerEditado.getNumero()))
                .andExpect(jsonPath("$.status").value("Ativo"));

    }

    @Test
    @DisplayName("Delete a conteiner")
    void AF_ExcluirConteinerDeveRetornar204() throws Exception {
        Conteiner conteinerASerExcluido = new Conteiner();
        conteinerASerExcluido.setId(ID_Conteiner);

        mockMvc.perform(
                        delete("/conteiners/"+conteinerASerExcluido.getId())
                                .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
                )
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("Try to create a conteiner without number")
    void AG_CriarConteinerSemNumeroDeveRetornar4XX() throws Exception {

        Conteiner conteinerSemNumero = new Conteiner();

        mockMvc.perform(
                        post("/conteiners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(conteinerSemNumero))
                                .header(obterHeaderComToken().get(0),obterHeaderComToken().get(1))
                ).andExpect(status().is4xxClientError());
    }

}