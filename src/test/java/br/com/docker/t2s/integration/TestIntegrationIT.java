package br.com.docker.t2s.integration;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class TestIntegrationIT {

    @Autowired
    private ConfigToken configToken;

    public List<String> obterHeaderComToken() {
        List<String> headerComToken = new ArrayList<>();
        headerComToken.add("authorization");
        headerComToken.add("Bearer " + configToken.getValue());
        return headerComToken;
    }

}
