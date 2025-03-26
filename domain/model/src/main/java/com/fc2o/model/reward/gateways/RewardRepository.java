package com.fc2o.model.reward.gateways;

import com.fc2o.model.reward.Reward;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RewardRepository {
  Mono<Reward> findById(UUID id);
  Flux<Reward> findAll();
  Mono<Reward> save(Reward reward);
  Mono<Reward> update(Reward reward);
  Mono<Reward> patch(Reward reward);
  Mono<Reward> deleteById(UUID id);
}
