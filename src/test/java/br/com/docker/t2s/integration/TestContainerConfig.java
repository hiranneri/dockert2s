package br.com.docker.t2s.integration;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestContainerConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.4-alpine")
            .withDatabaseName("dockert2s-test")
            .withPassword("secretpg")
            .withReuse(true);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
         postgreSQLContainer.start();
        TestPropertyValues.of(
                "spring.datasource.url=" +  postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.username=" +  postgreSQLContainer.getUsername(),
                "spring.datasource.password=" +  postgreSQLContainer.getPassword(),
                "spring.datasource.driver-class-name" +  postgreSQLContainer.getDriverClassName()
        ).applyTo(applicationContext.getEnvironment());
    }

}
