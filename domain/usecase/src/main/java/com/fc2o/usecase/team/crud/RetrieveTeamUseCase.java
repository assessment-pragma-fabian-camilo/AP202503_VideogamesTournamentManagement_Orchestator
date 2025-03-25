package com.fc2o.usecase.team.crud;

import com.fc2o.model.team.Team;
import com.fc2o.model.team.gateways.TeamRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class RetrieveTeamUseCase {

  private final TeamRepository teamRepository;

  public RetrieveTeamUseCase(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  public Mono<Team> retrieveTeamById(UUID id) {
    return teamRepository.findById(id);
  }

}
