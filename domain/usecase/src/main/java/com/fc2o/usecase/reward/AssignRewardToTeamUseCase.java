package com.fc2o.usecase.reward;

import com.fc2o.model.reward.Reward;
import com.fc2o.model.reward.gateways.RewardRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for assigning a reward to a team (when tournament is completed).
 */
public class AssignRewardToTeamUseCase {
    private final RewardRepository rewardRepository;

    public AssignRewardToTeamUseCase(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    public Mono<Reward> execute(String rewardId, String teamId) {
        if (rewardId == null || rewardId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Reward ID is required"));
        }
        if (teamId == null || teamId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Team ID is required"));
        }

        return rewardRepository.findById(rewardId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Reward not found")))
                .flatMap(reward -> {
                    Reward updatedReward = reward.toBuilder()
                            .teamId(teamId)
                            .build();
                    return rewardRepository.update(updatedReward);
                });
    }
}

