package br.com.docker.t2s.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    public static final DateTimeFormatter PT_BR_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static LocalDateTime toLocalDateTime(String dataHora) {
        return LocalDateTime.parse(dataHora, PT_BR_FORMATTER);
    }

    public static String dataHoraAtual() {
        return LocalDateTime.now().format(PT_BR_FORMATTER);
    }

    public static String convertLocalDateTimeToString(LocalDateTime dataHora){
        if(dataHora == null){
            return "";
        }
        return dataHora.format(PT_BR_FORMATTER);
    }
}
