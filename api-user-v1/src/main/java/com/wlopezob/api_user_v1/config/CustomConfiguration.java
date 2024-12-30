package com.wlopezob.api_user_v1.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@Component
public class CustomConfiguration {
  @Autowired
  private OpenApiProperties openApiProperties;

  @Bean
  public OpenAPI customOpenApi() {
    Info info = new Info();
    info.setTitle(openApiProperties.getTitle());
    info.setVersion(openApiProperties.getVersion());
    info.setDescription(openApiProperties.getDescription());
    OpenAPI openAPI = new OpenAPI().info(info);
    Supplier<List<Server>> obtenerListadoServidores = () -> Optional
        .ofNullable(openApiProperties.getServers())
        .map(x -> x.stream()
            .map(s -> new Server().url(s)).collect(Collectors.toList()))
        .orElseGet(() -> new ArrayList<Server>());
    openAPI.servers(obtenerListadoServidores.get());
    return openAPI;
  }
}
