package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    private static final DateTimeFormatter PT_BR_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static LocalDateTime toLocalDateTime(String dataHora) {
        return LocalDateTime.parse(dataHora, PT_BR_FORMATTER);
    }

    public static String dataHoraAtual() {
        return LocalDateTime.now().format(PT_BR_FORMATTER);
    }


    public static void main(String[] args) {
        System.out.println(toLocalDateTime("2023-07-23 23:09:36"));
    }
}
