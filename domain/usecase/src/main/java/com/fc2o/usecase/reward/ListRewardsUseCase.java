package com.fc2o.usecase.reward;

import com.fc2o.model.reward.Reward;
import com.fc2o.model.reward.gateways.RewardRepository;
import reactor.core.publisher.Flux;

/**
 * Use case for listing rewards by tournament.
 */
public class ListRewardsUseCase {
    private final RewardRepository rewardRepository;

    public ListRewardsUseCase(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    /**
     * List all rewards for a specific tournament.
     */
    public Flux<Reward> executeByTournament(String tournamentId) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Flux.error(new IllegalArgumentException("Tournament ID is required"));
        }
        return rewardRepository.findAllByTournamentId(tournamentId);
    }
}

