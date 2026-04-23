package com.fc2o.usecase.ticket.business;

import com.fc2o.model.ticket.Status;
import com.fc2o.model.ticket.Ticket;
import com.fc2o.model.ticket.Type;
import com.fc2o.model.transaction.Transaction;
import com.fc2o.usecase.ticket.crud.CreateTicketUseCase;
import com.fc2o.usecase.transaction.crud.RetrieveTransactionUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterTicketUseCase {

  private final RetrieveTransactionUseCase retrieveTransactionUseCase;
  private final CreateTicketUseCase createTicketUseCase;

  /*
  * Validar que haya una transacción en estado APPROVED
   */

  public Mono<Ticket> registerParticipationTicket(String tournamentId, String customerId) {
    return retrieveTransactionUseCase.retrieveByTournamentIdAndCustomerId(tournamentId, customerId)
      .filter(Transaction::isApproved)
      .switchIfEmpty(Mono.error(new RuntimeException("La transacción aún no ha sido aprobada")))
      .map(transaction ->
        Ticket.builder()
          .transactionId(transaction.id())
          .status(Status.NEW)
          .type(Type.PARTICIPATION)
          .customerId(customerId)
          .tournamentId(tournamentId)
          .build()
      )
      .flatMap(createTicketUseCase::create);
  }
}
