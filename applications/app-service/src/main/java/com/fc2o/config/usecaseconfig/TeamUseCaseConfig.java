package com.fc2o.config.usecaseconfig;

import com.fc2o.model.team.gateways.TeamRepository;
import com.fc2o.model.team.gateways.TeamMemberRepository;
import com.fc2o.model.tournamentteam.gateways.TournamentTeamRepository;
import com.fc2o.model.user.gateways.UserRepository;
import com.fc2o.usecase.team.*;
import com.fc2o.usecase.tournamentteam.AutoRegisterTeamInTournamentUseCase;
import com.fc2o.usecase.tournamentteam.CheckTeamInTournamentUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Team use cases.
 */
@Configuration
public class TeamUseCaseConfig {

    @Bean
    public CreateTeamUseCase createTeamUseCase(
            TeamRepository teamRepository,
            TeamMemberRepository teamMemberRepository,
            UserRepository userRepository) {
        return new CreateTeamUseCase(teamRepository, teamMemberRepository, userRepository);
    }

    @Bean
    public GetTeamUseCase getTeamUseCase(
            TeamRepository teamRepository) {
        return new GetTeamUseCase(teamRepository);
    }

    @Bean
    public ListTeamsUseCase listTeamsUseCase(
            TeamRepository teamRepository) {
        return new ListTeamsUseCase(teamRepository);
    }

    @Bean
    public UpdateTeamUseCase updateTeamUseCase(
            TeamRepository teamRepository) {
        return new UpdateTeamUseCase(teamRepository);
    }

    @Bean
    public DeleteTeamUseCase deleteTeamUseCase(
            TeamRepository teamRepository) {
        return new DeleteTeamUseCase(teamRepository);
    }

    @Bean
    public GetTeamMembersUseCase getTeamMembersUseCase(
            TeamMemberRepository teamMemberRepository) {
        return new GetTeamMembersUseCase(teamMemberRepository);
    }

    @Bean
    public AddTeamMemberUseCase addTeamMemberUseCase(
            TeamMemberRepository teamMemberRepository,
            UserRepository userRepository) {
        return new AddTeamMemberUseCase(teamMemberRepository, userRepository);
    }

    @Bean
    public RemoveTeamMemberUseCase removeTeamMemberUseCase(
            TeamMemberRepository teamMemberRepository) {
        return new RemoveTeamMemberUseCase(teamMemberRepository);
    }

    @Bean
    AutoRegisterTeamInTournamentUseCase autoRegisterTeamInTournamentUseCase(
            TournamentTeamRepository tournamentTeamRepository) {
        return new AutoRegisterTeamInTournamentUseCase(tournamentTeamRepository);
    }

    @Bean
    public CheckTeamInTournamentUseCase checkTeamInTournamentUseCase(
            TournamentTeamRepository tournamentTeamRepository) {
        return new CheckTeamInTournamentUseCase(tournamentTeamRepository);
    }
}

