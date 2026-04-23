package com.fc2o.config.usecaseconfig;

import com.fc2o.model.match.gateways.MatchRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.usecase.match.*;
import com.fc2o.usecase.tournamentteam.CheckTeamInTournamentUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Match use cases.
 */
@Configuration
public class MatchUseCaseConfig {

    @Bean
    public CreateMatchUseCase createMatchUseCase(
            MatchRepository matchRepository,
            TournamentRepository tournamentRepository) {
        return new CreateMatchUseCase(matchRepository, tournamentRepository);
    }

    @Bean
    public GetMatchUseCase getMatchUseCase(
            MatchRepository matchRepository) {
        return new GetMatchUseCase(matchRepository);
    }

    @Bean
    public ListMatchesUseCase listMatchesUseCase(
            MatchRepository matchRepository) {
        return new ListMatchesUseCase(matchRepository);
    }

    @Bean
    public UpdateMatchUseCase updateMatchUseCase(
            MatchRepository matchRepository) {
        return new UpdateMatchUseCase(matchRepository);
    }

    @Bean
    public UpdateMatchStatusUseCase updateMatchStatusUseCase(
            MatchRepository matchRepository) {
        return new UpdateMatchStatusUseCase(matchRepository);
    }

    @Bean
    public CancelMatchUseCase cancelMatchUseCase(
            MatchRepository matchRepository) {
        return new CancelMatchUseCase(matchRepository);
    }

    @Bean
    public AssignTeamsToMatchUseCase assignTeamsToMatchUseCase(
            MatchRepository matchRepository,
            CheckTeamInTournamentUseCase checkTeamInTournamentUseCase) {
        return new AssignTeamsToMatchUseCase(matchRepository, checkTeamInTournamentUseCase);
    }

    @Bean
    public RemoveTeamFromMatchUseCase removeTeamFromMatchUseCase(
            MatchRepository matchRepository) {
        return new RemoveTeamFromMatchUseCase(matchRepository);
    }

    @Bean
    public DefineMatchWinnerUseCase defineMatchWinnerUseCase(
            MatchRepository matchRepository,
            CheckTeamInTournamentUseCase checkTeamInTournamentUseCase) {
        return new DefineMatchWinnerUseCase(matchRepository, checkTeamInTournamentUseCase);
    }

    @Bean
    public UpdateMatchDetailsUseCase updateMatchDetailsUseCase(
            MatchRepository matchRepository) {
        return new UpdateMatchDetailsUseCase(matchRepository);
    }
}

