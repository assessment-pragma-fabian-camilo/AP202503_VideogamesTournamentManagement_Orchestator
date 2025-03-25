package com.fc2o.usecase.reward.business;

import com.fc2o.model.reward.Reward;
import com.fc2o.usecase.reward.crud.CreateRewardUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterRewardUseCase {
  private final CreateRewardUseCase createRewardUseCase;

  public Mono<Reward> registerReward(Reward reward) {
    return createRewardUseCase.create(reward);
  }
}
