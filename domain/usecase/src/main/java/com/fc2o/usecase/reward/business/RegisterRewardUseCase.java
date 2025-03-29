package com.fc2o.usecase.reward.business;

import com.fc2o.model.reward.Reward;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.usecase.reward.crud.CreateRewardUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RegisterRewardUseCase {
  private final CreateRewardUseCase createRewardUseCase;

  public Mono<Reward> registerReward(Reward reward, Tournament tournament) {
    Map<UUID, BigDecimal> pricePerStanding = tournament
      .prizePerPosition()
      .entrySet()
      .stream()
      .filter(entry -> reward.standings().containsKey(entry.getKey()))
      .collect(
        Collectors.toMap(
          entry -> reward.standings().get(entry.getKey()),
          Map.Entry::getValue
        )
      );
    return Mono.just(reward)
      .map(r -> 
        r.toBuilder()
          .id(UUID.randomUUID())
          .tournamentId(tournament.id())
          .prizePerStanding(pricePerStanding)
          .build()
      )
      .flatMap(createRewardUseCase::create);
  }
}
