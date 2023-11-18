package br.com.docker.t2s.integration;

import br.com.docker.t2s.IntegrationTest;
import br.com.docker.t2s.controller.dtos.cliente.ClientePutRequestDTO;
import br.com.docker.t2s.model.Cliente;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
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
public class AC_ClienteControllerIT extends TestIntegrationIT {

    @Autowired
    protected MockMvc mockMvc;
    protected static ObjectMapper objectMapper = new ObjectMapper();
    private final Long ID_CLIENTE = 1L;
    static Cliente emirates;

    @BeforeAll
    static void setUp(){
        emirates = Cliente.builder().nome("MSC").build();
    }

    @Test
    @DisplayName("Create a client")
    void AB_CriarClienteDeveRetornarClienteSalvoComStatusAtivo() throws Exception {

        Cliente emirates = new Cliente();
        emirates.setNome("MSC");

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
    @DisplayName("Search for client by id")
    void AC_PesquisarUmClienteDeveRetornarUmClienteSalvoComStatusAtivo() throws Exception {

        mockMvc.perform(
                        get("/clientes/" + ID_CLIENTE)
                                .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").isNotEmpty())
                .andExpect(jsonPath("$.status").value("Ativo"));
    }

    @Test
    @DisplayName("Edit a client")
    void AD_EditarUmClienteDeveRetornarClienteEditadoComStatusAtivo() throws Exception {
        ClientePutRequestDTO stc = ClientePutRequestDTO.builder()
                .id(ID_CLIENTE)
                .nome("STC").build();

        mockMvc.perform(
                        put("/clientes/"+stc.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(stc))
                                .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(stc.getNome()))
                .andExpect(jsonPath("$.status").value("Ativo"));

    }

    @Test
    @DisplayName("Delete a client")
    void AE_ExcluirClienteDeveRetornar204() throws Exception {
        Cliente clienteASerExcluido = new Cliente();
        clienteASerExcluido.setId(ID_CLIENTE);

        mockMvc.perform(
                        delete("/clientes/"+clienteASerExcluido.getId())
                                .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
                )
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("Try to create a client without name")
    void AF_CriarClienteSemNomeDeveRetornar403() throws Exception {
        Cliente clienteSemNome = Cliente.builder().build();

        mockMvc.perform(
                        post("/clientes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(clienteSemNome))
                                .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
                ).andExpect(status().is4xxClientError());
    }

}