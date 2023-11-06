package br.com.docker.t2s.integration;

import br.com.docker.t2s.IntegrationTest;
import br.com.docker.t2s.controller.dtos.cliente.ClientePostRequestDTO;
import br.com.docker.t2s.controller.dtos.conteiner.ConteinerPostRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPostRequestDTO;
import br.com.docker.t2s.controller.dtos.movimentacao.MovimentacaoPutRequestDTO;
import br.com.docker.t2s.model.Conteiner;
import br.com.docker.t2s.utils.ClienteCreator;
import br.com.docker.t2s.utils.ConteinerCreator;
import br.com.docker.t2s.utils.MovimentacaoCreator;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@IntegrationTest
@AutoConfigureMockMvc
@Log4j2
@ContextConfiguration(initializers = TestContainerConfig.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AE_MovimentacaoControllerIT extends AB_AbstractTestIntegrationIT {

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
                                .header("authorization", "Bearer "+ tokenAcesso)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(clienteASerSalvo.getNome()))
                .andExpect(jsonPath("$.status").value("ATIVO"));

    }

    @Test
    @DisplayName("Create a conteiner")
    void AC_CriarConteinerDeveRetornarConteinerSalvoComStatusAtivo() throws Exception {

        mockMvc.perform(
                        post("/conteiners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(conteinerASerSalvo))
                                .header("authorization", "Bearer "+ tokenAcesso)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("ATIVO"))
                .andExpect(jsonPath("$.numeroConteiner").value(conteinerASerSalvo.getNumero()));

    }

    @Test
    @DisplayName("Create a movimentacao")
    void AD_CriarMovimentacaoDeveRetornarMovimentacaoComStatusAtivo() throws Exception {

        mockMvc.perform(
                        post("/movimentacoes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movimentacaoASerSalva))
                                .header("authorization", "Bearer "+ tokenAcesso)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("ATIVO"))
                .andExpect(jsonPath("$.tipoMovimentacao").value(movimentacaoASerSalva.getTipoMovimentacao()))
                .andExpect(jsonPath("$.dataHoraInicio").isNotEmpty());

    }

    @Test
    @DisplayName("Edit a movimentacao")
    void AE_EditarUmaMovimentacaoDeveRetornarMovimentacaoditadaComStatusAtivo() throws Exception {
        MovimentacaoPutRequestDTO movimentacaoASerEditada = MovimentacaoCreator.createMovimentacaoPutRequestValido();

        mockMvc.perform(
                        put("/movimentacoes/"+ ID_MOVIMENTACAO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movimentacaoASerEditada))
                                .header("authorization", "Bearer "+ tokenAcesso)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroConteiner").value(movimentacaoASerEditada.getNumeroConteiner()))
                .andExpect(jsonPath("$.tipoMovimentacao").value(movimentacaoASerEditada.getTipoMovimentacao()))
                .andExpect(jsonPath("$.dataHoraInicio").isNotEmpty())
                .andExpect(jsonPath("$.status").value("ATIVO"));

    }

    @Test
    @DisplayName("Finalizar uma movimentação")
    void AF_FinalizarMovimentacaoDeveRetornar200_QuandoSucedido() throws Exception {

        MovimentacaoPutRequestDTO movimentacaoASerFinalizada = MovimentacaoCreator.createMovimentacaoASerFinalizadaValida();

        mockMvc.perform(
                        patch("/movimentacoes/finalizar/"+ID_MOVIMENTACAO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movimentacaoASerFinalizada))
                                .header("authorization", "Bearer "+ tokenAcesso)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Criar uma movimentação com um tipo já usado para o mesmo conteiner")
    void AG_CriarMovimentacaoComTipoJaUsadoNoConteiner_DeveRetornarBadRequest() throws Exception {
        MovimentacaoPutRequestDTO movimentacaoASerEditada = MovimentacaoCreator.createMovimentacaoPutRequestValido();

        mockMvc.perform(
                        post("/movimentacoes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movimentacaoASerEditada))
                                .header("authorization", "Bearer "+ tokenAcesso)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Delete a movimentacao - Sempre ser último")
    void AH_ExcluirConteinerDeveRetornar204() throws Exception {
        Conteiner movimentacaoASerExcluida = new Conteiner();
        movimentacaoASerExcluida.setId(ID_MOVIMENTACAO);

        mockMvc.perform(
                        delete("/movimentacoes/"+movimentacaoASerExcluida.getId())
                                .header("authorization", "Bearer "+ tokenAcesso)
                )
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

}