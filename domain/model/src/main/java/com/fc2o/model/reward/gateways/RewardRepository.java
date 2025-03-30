package com.fc2o.model.reward.gateways;

import com.fc2o.model.reward.Reward;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



public interface RewardRepository {
  Mono<Reward> findById(String id);
  Flux<Reward> findAll();
  Mono<Reward> save(Reward reward);
  Mono<Reward> update(Reward reward);
  Mono<Reward> patch(Reward reward);
  Mono<Reward> deleteById(String id);
}
