package br.com.docker.t2s.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * Classe responsável para realizar a configuração do Swagger
 */
@Configuration
public class SpringFoxConfig {

    // Docket: Objeto que realiza as configurações no Swagger
    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(List.of(apiKeyBearerToken()))
                .securityContexts(List.of(securityContext()));
    }

    /**
     * Método auxiliar que cria um esquema de segurança chamado "Bearer Token" que especifica que a autenticação Bearer
     * será realizada por meio do cabeçalho "Authorization".
     * Esta configuração é usada para indicar como os usuários devem fornecer seus tokens de acesso Bearer ao acessar
     * os endpoints protegidos da API.
     * @return apiKey
     */
    private ApiKey apiKeyBearerToken() {
        return new ApiKey("Bearer Token", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/auth/**")) // Sem token
                .forPaths(PathSelectors.ant("/swagger-ui/**")) // Sem token
                .forPaths(PathSelectors.ant("/swagger-resources/**")) // Sem token
                .forPaths(PathSelectors.ant("/swagger-ui.html")) // Sem token
                .forPaths(PathSelectors.ant("/v2/api-docs")) // Sem token
                .forPaths(PathSelectors.ant("/webjars/**")) // Sem token
                .forPaths(PathSelectors.any()) // Com token
                .build();
    }

    /**
     * Cria uma lista de referência de segurança (Bearer Token) ou mais, e aplica Bearer em todas as rotas especificadas
     * @return [SecurityReference]
     */
    List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];

        // Criação do escopo de autorização
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");

        authorizationScopes[0] = authorizationScope;

        return List.of(
                new SecurityReference("Bearer Token", authorizationScopes));
    }
}