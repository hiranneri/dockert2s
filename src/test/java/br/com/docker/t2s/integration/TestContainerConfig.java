package br.com.docker.t2s.integration;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestContainerConfig implements BeforeAllCallback, ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("dockert2s-test")
            .withPassword("secret")
            .withReuse(true);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        mySQLContainer.start();
        TestPropertyValues.of(
                "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                "spring.datasource.username=" + mySQLContainer.getUsername(),
                "spring.datasource.password=" + mySQLContainer.getPassword(),
                "spring.datasource.driver-class-name" + mySQLContainer.getDriverClassName()
        ).applyTo(applicationContext.getEnvironment());
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        mySQLContainer.start();
    }

}
