package br.com.docker.t2s.integration;


import org.springframework.stereotype.Component;

@Component("token")
public class ConfigToken {
    public static String token;
    public String getValue() {
        return token;
    }
}
