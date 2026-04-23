package com.fc2o.usecase.reward.crud;

import com.fc2o.model.reward.Reward;
import com.fc2o.model.reward.gateways.RewardRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateRewardUseCase {
  private final RewardRepository rewardRepository;

  public Mono<Reward> create(Reward reward) {
    return rewardRepository.save(reward);
  }
}
