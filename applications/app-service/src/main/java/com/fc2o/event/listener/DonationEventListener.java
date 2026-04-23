package com.fc2o.event.listener;

import com.fc2o.event.donation.DonationStatusChangedEvent;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener for donation events.
 * Sends notifications when donation status changes.
 */
@Component
public class DonationEventListener {
//    private static final Logger logger = LoggerFactory.getLogger(DonationEventListener.class);
//
//    @RabbitListener(queues = "donation.status.changed")
//    public void onDonationStatusChanged(DonationStatusChangedEvent event) {
//        logger.info("Donation status changed: {} -> {}", event.donationId(), event.status());
//
//        // TODO: Send email to donor
//        // emailSender.send(new EmailMessage(
//        //     event.userId(),
//        //     "Donation Status Changed",
//        //     "Your donation of " + event.amount() + " status changed to: " + event.status()
//        // ));
//    }
}

