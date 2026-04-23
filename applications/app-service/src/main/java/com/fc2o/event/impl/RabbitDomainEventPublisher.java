package com.fc2o.event.impl;

import com.fc2o.event.DomainEventPublisher;
import com.fc2o.event.tournament.TournamentStatusChangedEvent;
import com.fc2o.event.match.MatchResultedEvent;
import com.fc2o.event.ticket.TicketStatusChangedEvent;
import com.fc2o.event.donation.DonationStatusChangedEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * RabbitMQ implementation of DomainEventPublisher.
 */
@Component
public class RabbitDomainEventPublisher /*implements DomainEventPublisher*/ {

//    private final RabbitTemplate rabbitTemplate;
//
//    public RabbitDomainEventPublisher(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    @Override
//    public Mono<Void> publishTournamentStatusChanged(TournamentStatusChangedEvent event) {
//        return Mono.fromRunnable(() -> {
//            rabbitTemplate.convertAndSend(
//                "tournament.events",
//                "tournament.status.changed",
//                event
//            );
//        }).subscribeOn(Schedulers.boundedElastic()).then();
//    }
//
//    @Override
//    public Mono<Void> publishMatchResulted(MatchResultedEvent event) {
//        return Mono.fromRunnable(() -> {
//            rabbitTemplate.convertAndSend(
//                "match.events",
//                "match.result.defined",
//                event
//            );
//        }).subscribeOn(Schedulers.boundedElastic()).then();
//    }
//
//    @Override
//    public Mono<Void> publishTicketStatusChanged(TicketStatusChangedEvent event) {
//        return Mono.fromRunnable(() -> {
//            rabbitTemplate.convertAndSend(
//                "ticket.events",
//                "ticket.status.changed",
//                event
//            );
//        }).subscribeOn(Schedulers.boundedElastic()).then();
//    }
//
//    @Override
//    public Mono<Void> publishDonationStatusChanged(DonationStatusChangedEvent event) {
//        return Mono.fromRunnable(() -> {
//            rabbitTemplate.convertAndSend(
//                "donation.events",
//                "donation.status.changed",
//                event
//            );
//        }).subscribeOn(Schedulers.boundedElastic()).then();
//    }
}

