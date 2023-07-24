import model.*;
import service.ConteinerService;
import service.MovimentacaoService;
import service.TipoMovimentacaoService;
import service.impl.ConteinerServiceImpl;
import service.impl.MovimentacaoServiceImpl;
import service.impl.TipoMovimentacaoImpl;
import utils.DataUtils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe definida para testes das rotinas com o banco de dados
 */
public class PlaygroundJPA {
/*
    public static void main(String[] args) {
        Cliente compass = new Cliente();
        compass.setNome("ZZA");

        Categoria categoriaImportacao = new Categoria();
        categoriaImportacao.setNome(TipoCategoria.IMPORTACAO);

        Conteiner conteinerAA = new Conteiner();
        conteinerAA.setNumero("TEST0000001");
        conteinerAA.setTipo(TipoConteiner.TIPO_40);
        conteinerAA.setStatus(Status.CHEIO);
        conteinerAA.setCategoria(categoriaImportacao);
        conteinerAA.setCliente(compass);

        // Create
        ConteinerService conteinerService = new ConteinerServiceImpl();
        conteinerService.criar(conteinerAA);

        // Read
        Conteiner conteinerLocalizado = conteinerService.buscar(conteinerAA.getId());
        System.out.println(conteinerLocalizado);

        // Update
//        conteinerLocalizado.setNumero("Excluir");
//        conteinerService.editar(conteinerLocalizado);

        // Delete
        //conteinerService.deletar(conteinerLocalizado);
    }

    public static void main(String[] args) {
        Cliente compass = new Cliente();
        compass.setNome("ZZA");

        Categoria categoriaImportacao = new Categoria();
        categoriaImportacao.setNome(TipoCategoria.IMPORTACAO);

        Conteiner conteinerAA = new Conteiner();
        conteinerAA.setNumero("TEST0000001");
        conteinerAA.setTipo(TipoConteiner.TIPO_40);
        conteinerAA.setStatus(Status.CHEIO);
        conteinerAA.setCategoria(categoriaImportacao);
        conteinerAA.setCliente(compass);

        ConteinerServiceImpl conteinerService = new ConteinerServiceImpl();
        //System.out.println(conteinerService.buscarTodos());
        System.out.println("Container" + conteinerService.buscar(conteinerAA.getNumero()));



    }
    */

/*
    public static void main(String[] args) {
        TipoMovimentacao tipoMovimentacaoEmbarque = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoDescarga = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoGateIn = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoGateOut = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoPosicionamento = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoPilha = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoPesagem = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoScanner = new TipoMovimentacao();

        tipoMovimentacaoEmbarque.setNome(TipoMovimentacaoEnum.EMBARQUE);
        tipoMovimentacaoDescarga.setNome(TipoMovimentacaoEnum.DESCARGA);
        tipoMovimentacaoGateIn.setNome(TipoMovimentacaoEnum.GATE_IN);
        tipoMovimentacaoGateOut.setNome(TipoMovimentacaoEnum.GATE_OUT);
        tipoMovimentacaoPosicionamento.setNome(TipoMovimentacaoEnum.POSICIONAMENTO);
        tipoMovimentacaoPilha.setNome(TipoMovimentacaoEnum.PILHA);
        tipoMovimentacaoPesagem.setNome(TipoMovimentacaoEnum.PESAGEM);
        tipoMovimentacaoScanner.setNome(TipoMovimentacaoEnum.SCANNER);

        List<TipoMovimentacao> tipos = new ArrayList<>();
        tipos.add(tipoMovimentacaoEmbarque);
        tipos.add(tipoMovimentacaoDescarga);
        tipos.add(tipoMovimentacaoGateIn);
        tipos.add(tipoMovimentacaoGateOut);
        tipos.add(tipoMovimentacaoPosicionamento);
        tipos.add(tipoMovimentacaoPilha);
        tipos.add(tipoMovimentacaoPesagem);
        tipos.add(tipoMovimentacaoScanner);

        TipoMovimentacaoService tipoMovimentacaoService = new TipoMovimentacaoImpl();
        tipoMovimentacaoService.criar(tipos);


    }
*/

    public static void main(String[] args) {
/*
        TipoMovimentacao tipoMovimentacaoEmbarque = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoDescarga = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoGateIn = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoGateOut = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoPosicionamento = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoPilha = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoPesagem = new TipoMovimentacao();
        TipoMovimentacao tipoMovimentacaoScanner = new TipoMovimentacao();

        tipoMovimentacaoEmbarque.setNome(TipoMovimentacaoEnum.EMBARQUE);
        tipoMovimentacaoDescarga.setNome(TipoMovimentacaoEnum.DESCARGA);
        tipoMovimentacaoGateIn.setNome(TipoMovimentacaoEnum.GATE_IN);
        tipoMovimentacaoGateOut.setNome(TipoMovimentacaoEnum.GATE_OUT);
        tipoMovimentacaoPosicionamento.setNome(TipoMovimentacaoEnum.POSICIONAMENTO);
        tipoMovimentacaoPilha.setNome(TipoMovimentacaoEnum.PILHA);
        tipoMovimentacaoPesagem.setNome(TipoMovimentacaoEnum.PESAGEM);
        tipoMovimentacaoScanner.setNome(TipoMovimentacaoEnum.SCANNER);

        List<TipoMovimentacao> tipos = new ArrayList<>();
        tipos.add(tipoMovimentacaoEmbarque);
        tipos.add(tipoMovimentacaoDescarga);
        tipos.add(tipoMovimentacaoGateIn);
        tipos.add(tipoMovimentacaoGateOut);
        tipos.add(tipoMovimentacaoPosicionamento);
        tipos.add(tipoMovimentacaoPilha);
        tipos.add(tipoMovimentacaoPesagem);
        tipos.add(tipoMovimentacaoScanner);

        TipoMovimentacaoService tipoMovimentacaoService = new TipoMovimentacaoImpl();
        tipoMovimentacaoService.criar(tipos);
*/
        /*
        Cliente compass = new Cliente();
        compass.setNome("ZZB");

        Categoria categoriaImportacao = new Categoria();
        categoriaImportacao.setNome(TipoCategoria.EXPORTACAO);

        Conteiner conteinerBB = new Conteiner();
        conteinerBB.setNumero("TEST0000002");
        conteinerBB.setTipo(TipoConteiner.TIPO_40);
        conteinerBB.setStatus(Status.VAZIO);
        conteinerBB.setCategoria(categoriaImportacao);
        conteinerBB.setCliente(compass);

        // Create
        ConteinerService conteinerService = new ConteinerServiceImpl();
        conteinerService.criar(conteinerBB);

        Conteiner containerLocalizado = conteinerService.buscar(2L);

        TipoMovimentacaoService tipoMovimentacao = new TipoMovimentacaoImpl();
        TipoMovimentacao movimentacaoDescarga = tipoMovimentacao.buscar(TipoMovimentacaoEnum.GATE_OUT);

        MovimentacaoService movimentacaoService = new MovimentacaoServiceImpl();
        Movimentacao novaM = new Movimentacao();
        novaM.setConteiner(containerLocalizado);
        novaM.setTipoMovimentacao(movimentacaoDescarga);

        movimentacaoService.criar(novaM);




        MovimentacaoService movimentacaoService = new MovimentacaoServiceImpl();
        Movimentacao movimentacaoLocalizada = movimentacaoService.buscarPorDataCriacao("23/07/2023 23:51");
        movimentacaoLocalizada.setDatahorafim(DataUtils.dataHoraAtual());
        movimentacaoService.finalizarMovimentacao(movimentacaoLocalizada);*/

    }

}
