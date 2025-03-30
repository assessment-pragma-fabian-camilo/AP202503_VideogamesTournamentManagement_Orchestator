package com.fc2o.usecase.ticket.business;

import com.fc2o.model.ticket.Status;
import com.fc2o.model.ticket.Ticket;
import com.fc2o.service.ValidatePermissionsService;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.ticket.crud.PatchTicketUseCase;
import com.fc2o.usecase.ticket.crud.RetrieveTicketUseCase;
import com.fc2o.usecase.tournament.TournamentUseCases;
import com.fc2o.usecase.user.UserUseCases;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BlockTicketUseCase {

  private final RetrieveTicketUseCase retrieveTicketUseCase;
  private final PatchTicketUseCase patchTicketUseCase;
  private final ValidatePermissionsService permissionsService;
  private final ValidateTournamentPermissionsService tournamentPermissionsService;

  public Mono<Ticket> blockTicket(String ticketId, String userId) {
    return retrieveTicketUseCase.retrieveTicketById(ticketId)
      .doOnNext(t -> permissionsService.validate(userId, UserUseCases.BLOCK_TICKET))
      .filter(ticket -> !ticket.isBlocked())
      .switchIfEmpty(Mono.error(new RuntimeException("El ticket ya fue bloqueado")))
      .map(ticket -> Ticket.builder().status(Status.BLOCKED).build())
      .flatMap(patchTicketUseCase::patch);
  }

  public Flux<Ticket> blockTicketsByTournamentIdAndCustomerId(String tournamentId, String userId, String customerId) {
    return retrieveTicketUseCase.retrieveTicketsByTournamentIdAndCustomerId(tournamentId, customerId)
      .doOnNext(t -> tournamentPermissionsService.validate(tournamentId, userId, TournamentUseCases.BLOCK_TICKET))
      .map(ticket -> Ticket.builder().status(Status.BLOCKED).build())
      .flatMap(patchTicketUseCase::patch);
  }
}
