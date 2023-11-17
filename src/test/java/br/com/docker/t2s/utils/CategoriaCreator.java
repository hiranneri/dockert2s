package br.com.docker.t2s.utils;

import br.com.docker.t2s.model.enums.TipoCategoria;

public class CategoriaCreator {

    public static TipoCategoria createCategoriaAtiva() {
        return TipoCategoria.EXPORTACAO;
    }
}
