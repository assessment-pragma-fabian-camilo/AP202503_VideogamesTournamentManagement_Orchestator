package com.fc2o.api.impl;

import com.fc2o.model.reward.gateways.RewardRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class Reward implements RewardRepository {
  @Override
  public Mono<com.fc2o.model.reward.Reward> findById(UUID id) {
    return null;
  }

  @Override
  public Flux<com.fc2o.model.reward.Reward> findAll() {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.reward.Reward> save(com.fc2o.model.reward.Reward reward) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.reward.Reward> update(com.fc2o.model.reward.Reward reward) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.reward.Reward> patch(com.fc2o.model.reward.Reward reward) {
    return null;
  }

  @Override
  public Mono<com.fc2o.model.reward.Reward> deleteById(UUID id) {
    return null;
  }
}
