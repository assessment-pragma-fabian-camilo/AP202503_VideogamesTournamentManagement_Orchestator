package com.fc2o.usecase.tournament.crud;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class PatchTournamentUseCase {

  private final TournamentRepository tournamentRepository;

  public Mono<Tournament> patch(Tournament tournament) {
    return tournamentRepository.patch(tournament);
  }

  //Este method debe eliminar el participantId de pre-gegister y agregarlo a participantIds
  public Mono<Tournament> patchRegisterParticipant(UUID participantId) {
    return tournamentRepository.patchRegisterParticipant(participantId);
  }

  //Este method debe eliminar el participantId de pre-gegister y agregarlo a participantIds
  public Mono<Tournament> patchPreRegisterParticipant(UUID participantId) {
    return tournamentRepository.patchPreRegisterParticipant(participantId);
  }

  //Este method debe eliminar el participantId de pre-gegister y de participants y agregarlo en disqualifieds
  public Mono<Tournament> patchDisqualify(UUID participantId) {
    return tournamentRepository.patchDisqualify(participantId);
  }
}
