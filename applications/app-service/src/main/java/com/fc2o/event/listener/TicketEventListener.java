package com.fc2o.event.listener;

import com.fc2o.event.ticket.TicketStatusChangedEvent;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener for ticket events.
 * Sends notifications when ticket status changes.
 */
@Component
public class TicketEventListener {
//    private static final Logger logger = LoggerFactory.getLogger(TicketEventListener.class);
//
//    @RabbitListener(queues = "ticket.status.changed")
//    public void onTicketStatusChanged(TicketStatusChangedEvent event) {
//        logger.info("Ticket status changed: {} -> {}", event.ticketId(), event.ticketStatus());
//
//        // TODO: Send email to user
//        // emailSender.send(new EmailMessage(
//        //     event.userId(),
//        //     "Ticket Status Changed",
//        //     "Your ticket " + event.ticketId() + " status is now: " + event.ticketStatus()
//        // ));
//    }
}

