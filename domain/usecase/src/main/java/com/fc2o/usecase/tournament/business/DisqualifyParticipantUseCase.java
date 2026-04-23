package com.fc2o.usecase.tournament.business;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.match.business.CancelMatchUseCase;
import com.fc2o.usecase.ticket.business.BlockTicketUseCase;
import com.fc2o.usecase.tournament.TournamentUseCases;
import com.fc2o.usecase.tournament.crud.PatchTournamentUseCase;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class DisqualifyParticipantUseCase {
  private final RetrieveTournamentUseCase retrieveTournamentUseCase;
  private final PatchTournamentUseCase patchTournamentUseCase;
  private final ValidateTournamentPermissionsService permissionsService;
  private final CancelMatchUseCase cancelMatchUseCase;
  private final BlockTicketUseCase blockTicketUseCase;

  public Mono<Tournament> disqualifyParticipant(UUID tournamentId, UUID userId, UUID participantId) {
    return retrieveTournamentUseCase.retrieveById(tournamentId)
      .doOnNext(tournament -> permissionsService.validate(tournamentId, userId, TournamentUseCases.DISQUALIFY_PARTICIPANT))
      .filter(tournament -> !tournament.isFinished())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya terminó")))
      .filter(tournament -> !tournament.isCanceled())
      .switchIfEmpty(Mono.error(new RuntimeException("El torneo ya fue cancelado")))
      .filter(tournament -> !tournament.participantIds().contains(userId))
      .switchIfEmpty(Mono.error(new RuntimeException("El participante no está registrado en este torneo")))
      .filter(tournament -> !tournament.preRegisteredParticipantIds().contains(userId))
      .switchIfEmpty(Mono.error(new RuntimeException("El participante no está pre-registrado en este torneo")))
      .flatMap(tournament -> patchTournamentUseCase.patchDisqualify(tournamentId))
      .doOnNext(tournament -> blockTicketUseCase.blockTicketsByTournamentIdAndCustomerId(tournamentId, userId, participantId))
      .doOnNext(tournament -> cancelMatchUseCase.cancelAllMatchesByParticipantIdAndTournamentId(participantId, tournamentId));
  }
}
