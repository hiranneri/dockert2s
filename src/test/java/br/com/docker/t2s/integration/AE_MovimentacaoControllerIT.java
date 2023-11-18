package br.com.docker.t2s.integration;

import br.com.docker.t2s.IntegrationTest;
import br.com.docker.t2s.controller.dtos.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPostRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPutRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.TipoMovimentacaoListWrapper;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.utils.ClienteCreator;
import br.com.docker.t2s.utils.ConteinerCreator;
import br.com.docker.t2s.utils.MovimentacaoCreator;
import br.com.docker.t2s.utils.TipoMovimentacaoCreator;
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
public class AE_MovimentacaoControllerIT extends TestIntegrationIT {

    @Autowired
    protected MockMvc mockMvc;
    protected static ObjectMapper objectMapper = new ObjectMapper();
    private final Long ID_MOVIMENTACAO = 1L;
    ConteinerPostRequestDTO conteinerASerSalvo = ConteinerCreator.createConteinerMovimentacaoValido();
    MovimentacaoPostRequestDTO movimentacaoASerSalva = MovimentacaoCreator.createMovimentacaoRequestValido2();
    ClientePostRequestDTO clienteASerSalvo = ClienteCreator.createClienteRequestValido2();

    @Test
    @DisplayName("Create a client")
    void AB_CriarClienteDeveRetornarClienteComStatusAtivo() throws Exception {

        mockMvc.perform(
                post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteASerSalvo))
                        .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
        ).andExpect(status().isCreated())
        .andExpect(jsonPath("$.nome").value(clienteASerSalvo.getNome()))
        .andExpect(jsonPath("$.status").value("Ativo"));

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
    @DisplayName("Criar os tipos de movimentacao")
    public void AD_CadastrarTiposMovimentacao() throws Exception {
        TipoMovimentacaoListWrapper tiposMovimentacao = TipoMovimentacaoCreator.createTiposMovimentacao();

        mockMvc.perform(
                post("/movimentacoes/tipos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tiposMovimentacao))
                        .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Create a movimentacao")
    void AE_CriarMovimentacaoDeveRetornarMovimentacaoComStatusAtivo() throws Exception {

        mockMvc.perform(
                        post("/movimentacoes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movimentacaoASerSalva))
                                .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("Ativo"))
                .andExpect(jsonPath("$.tipoMovimentacao").value(movimentacaoASerSalva.getTipoMovimentacao()))
                .andExpect(jsonPath("$.dataHoraInicio").isNotEmpty());
    }

    @Test
    @DisplayName("Edit a movimentacao")
    void AF_EditarUmaMovimentacaoDeveRetornarMovimentacaoditadaComStatusAtivo() throws Exception {
        MovimentacaoPutRequestDTO movimentacaoASerEditada = MovimentacaoCreator.createMovimentacaoPutRequestValido();
        mockMvc.perform(
                        put("/movimentacoes/"+ ID_MOVIMENTACAO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movimentacaoASerEditada))
                                .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroConteiner").value(movimentacaoASerEditada.getNumeroConteiner()))
                .andExpect(jsonPath("$.tipoMovimentacao").value(movimentacaoASerEditada.getTipoMovimentacao()))
                .andExpect(jsonPath("$.dataHoraInicio").isNotEmpty())
                .andExpect(jsonPath("$.status").value("Ativo"));
    }

    @Test
    @DisplayName("Finalizar uma movimentação")
    void AG_FinalizarMovimentacaoDeveRetornar200_QuandoSucedido() throws Exception {
        MovimentacaoPutRequestDTO movimentacaoASerFinalizada = MovimentacaoCreator.createMovimentacaoASerFinalizadaValida();

        mockMvc.perform(
                        patch("/movimentacoes/finalizar/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movimentacaoASerFinalizada))
                                .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Criar uma movimentação com um tipo já usado para o mesmo conteiner")
    void AH_CriarMovimentacaoComTipoJaUsadoNoConteiner_DeveRetornarBadRequest() throws Exception {
        MovimentacaoPutRequestDTO movimentacaoASerEditada = MovimentacaoCreator.createMovimentacaoPutRequestValido();

        mockMvc.perform(
                        post("/movimentacoes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movimentacaoASerEditada))
                                .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Delete a movimentacao")
    void AZ_ExcluirConteinerDeveRetornar204() throws Exception {
        Conteiner movimentacaoASerExcluida = new Conteiner();
        movimentacaoASerExcluida.setId(ID_MOVIMENTACAO);

        mockMvc.perform(
                        delete("/movimentacoes/"+movimentacaoASerExcluida.getId())
                                .header(obterHeaderComToken().get(0), obterHeaderComToken().get(1))
                )
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

}