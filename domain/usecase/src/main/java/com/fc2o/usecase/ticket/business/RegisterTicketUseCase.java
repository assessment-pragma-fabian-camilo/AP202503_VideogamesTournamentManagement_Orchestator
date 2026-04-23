package com.fc2o.usecase.ticket.business;

import com.fc2o.model.ticket.Status;
import com.fc2o.model.ticket.Ticket;
import com.fc2o.model.ticket.Type;
import com.fc2o.model.transaction.Transaction;
import com.fc2o.usecase.ticket.crud.CreateQrUseCase;
import com.fc2o.usecase.ticket.crud.CreateTicketUseCase;
import com.fc2o.usecase.ticket.crud.PatchTicketUseCase;
import com.fc2o.usecase.transaction.crud.RetrieveTransactionUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterTicketUseCase {

  private final RetrieveTransactionUseCase retrieveTransactionUseCase;
  private final CreateTicketUseCase createTicketUseCase;
  private final CreateQrUseCase createQrUseCase;
  private final PatchTicketUseCase patchTicketUseCase;

  /*
  * Validar que haya una transacción en estado APPROVED
   */

  public Mono<Ticket> registerTicket(String tournamentId, String customerId, String reference) {
    return retrieveTransactionUseCase.retrieveByReference(reference)
      .filter(Transaction::isApproved)
      .switchIfEmpty(Mono.error(new RuntimeException("La transacción aún no ha sido aprobada")))
      .map(transaction ->
        Ticket.builder()
          .transactionId(transaction.id())
          .status(Status.NEW)
          .type(Type.valueOf(transaction.type().name()))
          .customerId(customerId)
          .tournamentId(tournamentId)
          .build()
      )
      .flatMap(createTicketUseCase::create)
      .zipWhen(ticket -> createQrUseCase.retrieve(ticket.id()))
      .map(objects -> objects.getT1().toBuilder().qr(objects.getT2()).build())
      .flatMap(patchTicketUseCase::patch);
  }
}
