package br.com.docker.t2s.utils;

import lombok.extern.log4j.Log4j2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class ExtracaoUtils {

    public static Regex regexToken = new Regex("\"token\":\"(.*?)\"");

    public static String extrairDadoDesejadoResponseBody(String texto, Regex regex){
        if(texto.isBlank()) {
            log.warn("Não foi informado o response body");
            return "";
        }
        // Padrão regex para extrair o token.
        Pattern pattern = Pattern.compile(regex.getRegex());

        // Criar um Matcher para encontrar a correspondência no JSON.
        Matcher matcher = pattern.matcher(texto);
        String token = "";

        if (matcher.find()) {
             token = matcher.group(1); // O grupo de captura 1 contém o token.
        }
        return token;
    }

}
