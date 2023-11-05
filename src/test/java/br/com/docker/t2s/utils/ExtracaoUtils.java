package br.com.docker.t2s.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtracaoUtils {

    public static String regexToken = "\"token\":\"(.*?)\"";

    public static String extrairDadoDesejadoResponseBody(String texto, String regex){
        // Padrão regex para extrair o token.
        Pattern pattern = Pattern.compile(regex);

        // Criar um Matcher para encontrar a correspondência no JSON.
        Matcher matcher = pattern.matcher(texto);
        String token = "";

        // Encontrar a correspondência e obter o token.
        if (matcher.find()) {
             token = matcher.group(1); // O grupo de captura 1 contém o token.
        }
        return token;
    }

}
