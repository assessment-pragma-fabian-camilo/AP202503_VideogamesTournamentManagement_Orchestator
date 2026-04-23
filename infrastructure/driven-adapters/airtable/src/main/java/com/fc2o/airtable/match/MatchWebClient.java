package com.fc2o.airtable.match;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc2o.airtable.BaseWebClient;
import com.fc2o.airtable.match.dto.RecordDto;
import com.fc2o.airtable.match.dto.WrapperDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class MatchWebClient {

  private final BaseWebClient webClient;
  private final String basePath;
  private final String bearer;

  public MatchWebClient(
    BaseWebClient webClient,
    @Value("${driven-adapter.airtable.match.base-path}") String basePath,
    @Value("${driven-adapter.airtable.match.bearer}") String bearer
  ) {
    this.webClient = webClient;
    this.basePath = basePath;
    this.bearer = bearer;
  }

  public Mono<RecordDto> retrieveById(String id) {
    return webClient.getWebClient()
      .get()
      .uri(basePath + "/" + id)
      .headers(headers -> headers.setBearerAuth(bearer))
      .retrieve()
      .bodyToMono(RecordDto.class);
  }

  public Mono<WrapperDto> retrieveAll() {
    return webClient.getWebClient()
      .get()
      .uri(basePath)
      .headers(headers -> headers.setBearerAuth(bearer))
      .retrieve()
      .bodyToMono(WrapperDto.class);
  }

  public Mono<WrapperDto> patch(WrapperDto dto) {
    return webClient.getWebClient()
      .patch()
      .uri(basePath)
      .headers(headers -> headers.setBearerAuth(bearer))
      .contentType(MediaType.APPLICATION_JSON)
      .body(Mono.just(dto), WrapperDto.class)
      .retrieve()
      .bodyToMono(WrapperDto.class);
  }

  public Mono<WrapperDto> create(WrapperDto dto) {
    ObjectMapper objectMapper = new ObjectMapper();
    AtomicReference<String> json = new AtomicReference<>();
    try {
      json.set(objectMapper.writeValueAsString(dto));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return webClient.getWebClient()
      .post()
      .uri(basePath)
      .headers(headers -> headers.setBearerAuth(bearer))
      .contentType(MediaType.APPLICATION_JSON)
      .body(Mono.just(dto).doOnNext(w -> System.out.println(json.get())), WrapperDto.class)
      .retrieve()
      .bodyToMono(WrapperDto.class);
  }
}
