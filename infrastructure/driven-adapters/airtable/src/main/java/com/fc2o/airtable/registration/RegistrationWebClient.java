package com.fc2o.airtable.registration;

import com.fc2o.airtable.BaseWebClient;
import com.fc2o.airtable.registration.dto.RecordDto;
import com.fc2o.airtable.registration.dto.WrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RegistrationWebClient {

  private final BaseWebClient webClient;
  private final String basePath;
  private final String bearer;

  public RegistrationWebClient(
    BaseWebClient webClient,
    @Value("${driven-adapter.airtable.registration.base-path}") String basePath,
    @Value("${driven-adapter.airtable.registration.bearer}") String bearer
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
      .retrieve()
      .bodyToMono(WrapperDto.class);
  }

  public Mono<WrapperDto> create(WrapperDto dto) {
    return webClient.getWebClient()
      .post()
      .uri(basePath)
      .headers(headers -> headers.setBearerAuth(bearer))
      .contentType(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(WrapperDto.class);
  }
}
