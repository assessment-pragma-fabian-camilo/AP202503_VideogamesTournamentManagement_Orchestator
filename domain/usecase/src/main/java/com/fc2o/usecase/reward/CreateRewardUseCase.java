package com.fc2o.usecase.reward;

import com.fc2o.model.reward.Reward;
import com.fc2o.model.reward.gateways.RewardRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for creating a reward.
 * Business rules:
 * - Only PROMOTER or ADMIN can create rewards
 * - team_id should be null until tournament is completed
 * - position is the ranking (1st, 2nd, etc.)
 * - prize is the monetary value (can be null for free tournaments)
 */
public class CreateRewardUseCase {
    private final RewardRepository rewardRepository;
    private final TournamentRepository tournamentRepository;

    public CreateRewardUseCase(
            RewardRepository rewardRepository,
            TournamentRepository tournamentRepository) {
        this.rewardRepository = rewardRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public Mono<Reward> execute(String tournamentId, Short position, Double prize) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }
        if (position == null || position <= 0) {
            return Mono.error(new IllegalArgumentException("Position must be greater than 0"));
        }

        return tournamentRepository.findById(tournamentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found")))
                .flatMap(tournament -> {
                    Reward newReward = Reward.builder()
                            .tournamentId(tournamentId)
                            .position(position)
                            .prize(prize)
                            .teamId(null)  // Will be set when tournament completes
                            .build();

                    return rewardRepository.save(newReward);
                });
    }
}

