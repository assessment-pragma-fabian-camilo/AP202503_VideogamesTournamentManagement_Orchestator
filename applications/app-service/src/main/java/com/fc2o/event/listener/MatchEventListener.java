package com.fc2o.event.listener;

import com.fc2o.event.match.MatchResultedEvent;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener for match events.
 * Sends notifications when match results are defined.
 */
@Component
public class MatchEventListener {
//    private static final Logger logger = LoggerFactory.getLogger(MatchEventListener.class);
//
//    @RabbitListener(queues = "match.resulted")
//    public void onMatchResulted(MatchResultedEvent event) {
//        logger.info("Match result defined: {} -> Winner: {}", event.matchId(), event.winnerTeamId());
//
//        // TODO: Send notifications to participants
//        // - Email to winner team
//        // - Email to loser teams
//        // - Update match status for spectators
//    }
}

