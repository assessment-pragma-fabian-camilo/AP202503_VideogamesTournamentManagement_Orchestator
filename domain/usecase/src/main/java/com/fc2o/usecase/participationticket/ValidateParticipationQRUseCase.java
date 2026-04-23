package com.fc2o.usecase.participationticket;

import com.fc2o.model.participationticket.ParticipationTicket;
import com.fc2o.model.participationticket.gateways.ParticipationTicketRepository;
import com.fc2o.model.shared.TicketStatus;
import reactor.core.publisher.Mono;

/**
 * Use case for validating participation ticket QR code.
 * When ticket is validated, status changes from ACTIVE to USED.
 */
public class ValidateParticipationQRUseCase {
    private final ParticipationTicketRepository participationTicketRepository;

    public ValidateParticipationQRUseCase(ParticipationTicketRepository participationTicketRepository) {
        this.participationTicketRepository = participationTicketRepository;
    }

    public Mono<ParticipationTicket> execute(String ticketId, byte[] qrCode) {
        if (ticketId == null || ticketId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Ticket ID is required"));
        }
        if (qrCode == null || qrCode.length == 0) {
            return Mono.error(new IllegalArgumentException("QR code is required"));
        }

        return participationTicketRepository.findById(ticketId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Ticket not found")))
                .flatMap(ticket -> {
                    // Validate QR code matches ticket's QR
                    if (!validateQRCode(ticket.qr(), qrCode)) {
                        return Mono.error(new IllegalArgumentException("Invalid QR code"));
                    }

                    // Change status to USED
                    ParticipationTicket validatedTicket = ticket.toBuilder()
                            .ticketStatus(TicketStatus.USED)
                            .build();

                    return participationTicketRepository.update(validatedTicket);
                });
    }

    private boolean validateQRCode(byte[] originalQR, byte[] providedQR) {
        if (originalQR == null || providedQR == null) {
            return false;
        }
        if (originalQR.length != providedQR.length) {
            return false;
        }
        for (int i = 0; i < originalQR.length; i++) {
            if (originalQR[i] != providedQR[i]) {
                return false;
            }
        }
        return true;
    }
}

