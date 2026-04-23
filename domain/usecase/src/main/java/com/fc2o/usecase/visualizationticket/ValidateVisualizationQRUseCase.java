package com.fc2o.usecase.visualizationticket;

import com.fc2o.model.visualizationticket.VisualizationTicket;
import com.fc2o.model.visualizationticket.gateways.VisualizationTicketRepository;
import com.fc2o.model.shared.TicketStatus;
import reactor.core.publisher.Mono;

/**
 * Use case for validating visualization ticket QR code.
 * When ticket is validated, status changes from ACTIVE to USED.
 */
public class ValidateVisualizationQRUseCase {
    private final VisualizationTicketRepository visualizationTicketRepository;

    public ValidateVisualizationQRUseCase(VisualizationTicketRepository visualizationTicketRepository) {
        this.visualizationTicketRepository = visualizationTicketRepository;
    }

    public Mono<VisualizationTicket> execute(String ticketId, byte[] qrCode) {
        if (ticketId == null || ticketId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Ticket ID is required"));
        }
        if (qrCode == null || qrCode.length == 0) {
            return Mono.error(new IllegalArgumentException("QR code is required"));
        }

        return visualizationTicketRepository.findById(ticketId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Ticket not found")))
                .flatMap(ticket -> {
                    // Validate QR code matches ticket's QR
                    if (!validateQRCode(ticket.qr(), qrCode)) {
                        return Mono.error(new IllegalArgumentException("Invalid QR code"));
                    }

                    // Change status to USED
                    VisualizationTicket validatedTicket = ticket.toBuilder()
                            .ticketStatus(TicketStatus.USED)
                            .build();

                    return visualizationTicketRepository.update(validatedTicket);
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

