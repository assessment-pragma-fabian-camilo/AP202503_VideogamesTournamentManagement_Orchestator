package com.fc2o.config.usecaseconfig;

import com.fc2o.model.reward.gateways.RewardRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.usecase.reward.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Reward use cases.
 */
@Configuration
public class RewardUseCaseConfig {

    @Bean
    public CreateRewardUseCase createRewardUseCase(
            RewardRepository rewardRepository,
            TournamentRepository tournamentRepository) {
        return new CreateRewardUseCase(rewardRepository, tournamentRepository);
    }

    @Bean
    public GetRewardUseCase getRewardUseCase(
            RewardRepository rewardRepository) {
        return new GetRewardUseCase(rewardRepository);
    }

    @Bean
    public ListRewardsUseCase listRewardsUseCase(
            RewardRepository rewardRepository) {
        return new ListRewardsUseCase(rewardRepository);
    }

    @Bean
    public UpdateRewardUseCase updateRewardUseCase(
            RewardRepository rewardRepository) {
        return new UpdateRewardUseCase(rewardRepository);
    }

    @Bean
    public AssignRewardToTeamUseCase assignRewardToTeamUseCase(
            RewardRepository rewardRepository) {
        return new AssignRewardToTeamUseCase(rewardRepository);
    }
}

