package br.com.docker.t2s;

import br.com.docker.t2s.integration.TestContainerConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@ContextConfiguration(initializers = TestContainerConfig.class)
public @interface IntegrationTest {
}
