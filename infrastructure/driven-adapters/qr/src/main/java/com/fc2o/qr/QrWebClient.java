package com.fc2o.qr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Component
public class QrWebClient {

  private final WebClient webClient;

  public QrWebClient(@Value("${driven-adapter.qr.base-url}") String baseUrl) {
    this.webClient = WebClient.builder().baseUrl(baseUrl).build();
  }

  public Mono<String> retrieve(String ticketId) {
    return webClient.get()
      .uri(uriBuilder -> uriBuilder
        .queryParam("size", "200x200")
        .queryParam("data", ticketId)
        .build()
      )
      .retrieve()
      .bodyToMono(byte[].class)
      .map(this::encodeToBase64);
  }
  private String encodeToBase64(byte[] imageBytes) {
    return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
  }
}
