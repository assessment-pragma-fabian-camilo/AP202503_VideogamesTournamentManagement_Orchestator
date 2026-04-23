package com.fc2o.event;

import com.fc2o.event.tournament.TournamentStatusChangedEvent;
import com.fc2o.event.match.MatchResultedEvent;
import com.fc2o.event.ticket.TicketStatusChangedEvent;
import com.fc2o.event.donation.DonationStatusChangedEvent;
import reactor.core.publisher.Mono;

/**
 * Interface for publishing domain events.
 */
public interface DomainEventPublisher {
    Mono<Void> publishTournamentStatusChanged(TournamentStatusChangedEvent event);
    Mono<Void> publishMatchResulted(MatchResultedEvent event);
    Mono<Void> publishTicketStatusChanged(TicketStatusChangedEvent event);
    Mono<Void> publishDonationStatusChanged(DonationStatusChangedEvent event);
}

