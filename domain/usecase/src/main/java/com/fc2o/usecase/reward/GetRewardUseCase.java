package com.fc2o.usecase.reward;

import com.fc2o.model.reward.Reward;
import com.fc2o.model.reward.gateways.RewardRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for retrieving a reward by ID.
 */
public class GetRewardUseCase {
    private final RewardRepository rewardRepository;

    public GetRewardUseCase(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    public Mono<Reward> execute(String rewardId) {
        if (rewardId == null || rewardId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Reward ID is required"));
        }
        return rewardRepository.findById(rewardId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Reward not found with ID: " + rewardId)));
    }
}

