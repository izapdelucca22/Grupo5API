package org.serratec.backend.redesocial.swagger;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {
	
	@Value("${dominio.openapi.dev-url}")
	private String devUrl;
	
	@Value("${dominio.openapi.dev-url}")
	private String prodUrl;
	
	@Bean
	OpenAPI myOpenApi() {
		Server devServer = new Server();
		devServer.setUrl(devUrl);
		devServer.setDescription("URL do servidor de desenvolvimento");
		Server prodServer = new Server();
		prodServer.setUrl(prodUrl);
		prodServer.setDescription("URL do servidor de produção");
		
		Contact contact = new Contact();
		contact.setEmail("rede@redesocial.com.br");
		contact.setName("Rede Social");
		contact.setUrl("https//www.redesocial.com.br");
		
		License apacheLicense = new License().name("Apache License")
				.url("https://www.apache.org/license/LICENSE=2.0");
		
		Info info = new Info().title("Rede Social").version("1.0").contact(contact)
				.description("Rede Social Swagger").termsOfService("https//www.redesocial.com.br/termos")
				.license(apacheLicense);
		
		return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
		
	}

	
}
