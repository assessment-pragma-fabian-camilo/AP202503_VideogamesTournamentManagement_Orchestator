package com.fc2o.model.matchteam.gateways;

import com.fc2o.model.matchteam.MatchTeam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MatchTeamRepository {
    Flux<MatchTeam> findAllByMatchId(String matchId);

    Mono<Boolean> existsByMatchIdAndTeamId(String matchId, String teamId);

    Mono<MatchTeam> save(MatchTeam matchTeam);

    Mono<Void> deleteById(String matchTeamId);
}

