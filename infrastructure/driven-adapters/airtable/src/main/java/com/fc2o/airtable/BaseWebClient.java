package com.fc2o.airtable;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Getter
public class BaseWebClient {

  private final WebClient webClient;

  public BaseWebClient(@Value("${driven-adapter.airtable.base-url}") String baseUrl) {
    this.webClient = WebClient.builder().baseUrl(baseUrl).build();
  }
}
