package com.fc2o.config.usecaseconfig;

import com.fc2o.model.tournamentmoderator.gateways.TournamentModeratorRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.user.gateways.UserRepository;
import com.fc2o.usecase.tournamentmoderator.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Tournament Moderator use cases.
 */
@Configuration
public class TournamentModeratorUseCaseConfig {

    @Bean
    public AssignModeratorUseCase assignModeratorUseCase(
            TournamentModeratorRepository tournamentModeratorRepository,
            TournamentRepository tournamentRepository,
            UserRepository userRepository) {
        return new AssignModeratorUseCase(
            tournamentModeratorRepository,
            tournamentRepository,
            userRepository
        );
    }

    @Bean
    public RemoveModeratorUseCase removeModeratorUseCase(
            TournamentModeratorRepository tournamentModeratorRepository) {
        return new RemoveModeratorUseCase(tournamentModeratorRepository);
    }

    @Bean
    public GetModeratorsUseCase getModeratorsUseCase(
            TournamentModeratorRepository tournamentModeratorRepository) {
        return new GetModeratorsUseCase(tournamentModeratorRepository);
    }

    @Bean
    public VerifyModeratorPermissionUseCase verifyModeratorPermissionUseCase(
            TournamentModeratorRepository tournamentModeratorRepository) {
        return new VerifyModeratorPermissionUseCase(tournamentModeratorRepository);
    }
}

