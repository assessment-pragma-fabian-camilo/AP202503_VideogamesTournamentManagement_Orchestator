package com.fc2o.config.usecaseconfig;

import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.game.gateways.GameRepository;
import com.fc2o.model.tournamentteam.gateways.TournamentTeamRepository;
import com.fc2o.model.user.gateways.UserRepository;
import com.fc2o.usecase.tournament.*;
import com.fc2o.usecase.tournamentteam.GetTournamentTeamUseCase;
import com.fc2o.usecase.tournamentteam.ListTournamentTeamsUseCase;
import com.fc2o.usecase.tournamentteam.RemoveTournamentTeamUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Tournament use cases.
 */
@Configuration
public class TournamentUseCaseConfig {

    @Bean
    public CreateTournamentUseCase createTournamentUseCase(
            TournamentRepository tournamentRepository,
            GameRepository gameRepository,
            UserRepository userRepository) {
        return new CreateTournamentUseCase(tournamentRepository, gameRepository, userRepository);
    }

    @Bean
    public GetTournamentUseCase getTournamentUseCase(
            TournamentRepository tournamentRepository) {
        return new GetTournamentUseCase(tournamentRepository);
    }

    @Bean
    public ListTournamentsUseCase listTournamentsUseCase(
            TournamentRepository tournamentRepository) {
        return new ListTournamentsUseCase(tournamentRepository);
    }

    @Bean
    public UpdateTournamentUseCase updateTournamentUseCase(
            TournamentRepository tournamentRepository) {
        return new UpdateTournamentUseCase(tournamentRepository);
    }

    @Bean
    public UpdateTournamentStatusUseCase updateTournamentStatusUseCase(
            TournamentRepository tournamentRepository) {
        return new UpdateTournamentStatusUseCase(tournamentRepository);
    }

    @Bean
    public CancelTournamentUseCase cancelTournamentUseCase(
            TournamentRepository tournamentRepository) {
        return new CancelTournamentUseCase(tournamentRepository);
    }

    @Bean
    public UpdateTournamentRulesUseCase updateTournamentRulesUseCase(
            TournamentRepository tournamentRepository) {
        return new UpdateTournamentRulesUseCase(tournamentRepository);
    }

    @Bean
    public UpdatePlaceLimitsUseCase updatePlaceLimitsUseCase(
            TournamentRepository tournamentRepository) {
        return new UpdatePlaceLimitsUseCase(tournamentRepository);
    }

    @Bean
    public CompleteTournamentUseCase completeTournamentUseCase(
            TournamentRepository tournamentRepository) {
        return new CompleteTournamentUseCase(tournamentRepository);
    }

    @Bean
    public ListTournamentTeamsUseCase listTournamentTeamsUseCase(
            TournamentTeamRepository tournamentTeamRepository) {
        return new ListTournamentTeamsUseCase(tournamentTeamRepository);
    }

    @Bean
    public GetTournamentTeamUseCase getTournamentTeamUseCase(TournamentTeamRepository tournamentTeamRepository) {
        return new GetTournamentTeamUseCase(tournamentTeamRepository);
    }

    @Bean
    public RemoveTournamentTeamUseCase removeTournamentTeamUseCase(TournamentTeamRepository tournamentTeamRepository) {
        return  new RemoveTournamentTeamUseCase(tournamentTeamRepository);
    }
}

