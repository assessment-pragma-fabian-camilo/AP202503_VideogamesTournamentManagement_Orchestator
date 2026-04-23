package com.fc2o.event.listener;

import com.fc2o.event.tournament.TournamentStatusChangedEvent;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener for tournament events.
 * Sends notifications when tournament status changes.
 */
@Component
public class TournamentEventListener {
//    private static final Logger logger = LoggerFactory.getLogger(TournamentEventListener.class);
//
//    @RabbitListener(queues = "tournament.status.changed")
//    public void onTournamentStatusChanged(TournamentStatusChangedEvent event) {
//        logger.info("Tournament status changed: {} -> {}", event.tournamentId(), event.newStatus());
//
//        // TODO: Send email to promoter
//        // emailSender.send(new EmailMessage(
//        //     event.promoterUserId(),
//        //     "Tournament Status Changed",
//        //     "Your tournament " + event.tournamentId() + " status changed to: " + event.newStatus()
//        // ));
//    }
}

