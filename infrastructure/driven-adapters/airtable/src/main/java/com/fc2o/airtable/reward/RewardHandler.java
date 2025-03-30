package com.fc2o.airtable.reward;

import com.fc2o.airtable.reward.mapper.RewardMapper;
import com.fc2o.model.reward.Reward;
import com.fc2o.model.reward.gateways.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RewardHandler implements RewardRepository {

  private final RewardWebClient webClient;
  private final RewardMapper mapper;

  @Override
  public Mono<Reward> findById(String id) {
    return webClient.retrieveById(id)
      .map(mapper::toReward);
  }

  @Override
  public Flux<Reward> findAll() {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .map(mapper::toReward);
  }

  @Override
  public Mono<Reward> save(Reward reward) {
    return webClient
      .create(mapper.toWrapperDto(reward))
      .map(dto -> mapper.toReward(dto.records().getFirst()));
  }

  @Override
  public Mono<Reward> update(Reward reward) {
    return null;
  }

  @Override
  public Mono<Reward> patch(Reward reward) {
    return webClient
      .patch(mapper.toWrapperDto(reward))
      .map(dto -> mapper.toReward(dto.records().getFirst()));
  }

  @Override
  public Mono<Reward> deleteById(String id) {
    return null;
  }
}
