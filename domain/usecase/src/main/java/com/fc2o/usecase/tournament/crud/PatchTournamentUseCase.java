package com.fc2o.usecase.tournament.crud;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;



@RequiredArgsConstructor
public class PatchTournamentUseCase {

  private final TournamentRepository tournamentRepository;

  public Mono<Tournament> patch(Tournament tournament) {
    return tournamentRepository.patch(tournament);
  }

  //Este method debe eliminar el participantId de pre-gegister y agregarlo a participantIds
  public Mono<Tournament> patchRegisterParticipant(String tournamentId, String participantId) {
    return tournamentRepository.patchRegisterParticipant(tournamentId, participantId);
  }

  //Este method debe agregar el participantId a pre-register y reducir en 1 el remaining
  public Mono<Tournament> patchPreRegisterParticipant(String tournamentId, String participantId) {
    return tournamentRepository.patchPreRegisterParticipant(tournamentId, participantId);
  }

  //Este method debe eliminar el participantId de pre-gegister y de participants y agregarlo en disqualifieds
  public Mono<Tournament> patchDisqualify(String tournamentId, String participantId) {
    return tournamentRepository.patchDisqualify(tournamentId, participantId);
  }
}
