package com.fc2o.airtable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Getter
public class BaseWebClient {

  private final WebClient webClient;

  public BaseWebClient(@Value("${driven-adapter.airtable.base-url}") String baseUrl) {
    // Configura el ObjectMapper para ignorar valores null
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    // Configura los codecs de Jackson en el WebClient
    ExchangeStrategies strategies = ExchangeStrategies.builder()
      .codecs(clientCodecConfigurer -> clientCodecConfigurer
        .defaultCodecs()
        .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper)))
      .build();

    this.webClient = WebClient.builder().baseUrl(baseUrl).exchangeStrategies(strategies).build();
  }
}
