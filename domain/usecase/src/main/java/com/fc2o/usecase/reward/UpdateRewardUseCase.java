package com.fc2o.usecase.reward;

import com.fc2o.model.reward.Reward;
import com.fc2o.model.reward.gateways.RewardRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for updating a reward.
 */
public class UpdateRewardUseCase {
    private final RewardRepository rewardRepository;

    public UpdateRewardUseCase(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    public Mono<Reward> execute(String rewardId, Double prize) {
        if (rewardId == null || rewardId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Reward ID is required"));
        }

        return rewardRepository.findById(rewardId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Reward not found")))
                .flatMap(reward -> {
                    Reward updatedReward = reward.toBuilder()
                            .prize(prize)
                            .build();
                    return rewardRepository.update(updatedReward);
                });
    }
}

