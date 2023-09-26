package br.com.docker.t2s.utils;

import br.com.docker.t2s.model.Categoria;
import br.com.docker.t2s.model.enums.TipoCategoria;

public class CategoriaCreator {

    public static Categoria createCategoriaAtiva() {
        return Categoria.builder()
                .id(1L)
                .nome(TipoCategoria.EXPORTACAO).build();
    }
}
