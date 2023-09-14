package br.com.docker.t2s.service;

import br.com.docker.t2s.model.Categoria;
import br.com.docker.t2s.model.enums.TipoCategoria;
import br.com.docker.t2s.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public void cadastrarCategorias() {
        log.info("Iniciando a verificação para cadastro das categorias dos conteineres");

        List<Categoria> categorias = gerarCategorias();

        if(categoriaRepository.count() == 0){
            log.info("Iniciando cadastro no banco...");
            categoriaRepository.saveAll(categorias);
        } else {
            log.info("Foi verificado que a tabela já está populada");
        }
    }

    private List<Categoria> gerarCategorias() {
        List<Categoria> categorias = new ArrayList<>();

        categorias.add(Categoria.builder().nome(TipoCategoria.IMPORTACAO).build());
        categorias.add(Categoria.builder().nome(TipoCategoria.EXPORTACAO).build());

        return categorias;
    }
}
