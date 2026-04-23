package com.fc2o.config.usecaseconfig;

import com.fc2o.model.visualizationticket.gateways.VisualizationTicketRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.user.gateways.UserRepository;
import com.fc2o.usecase.visualizationticket.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Visualization Ticket use cases.
 */
@Configuration
public class VisualizationTicketUseCaseConfig {

    @Bean
    public CreateVisualizationTicketUseCase createVisualizationTicketUseCase(
            VisualizationTicketRepository visualizationTicketRepository,
            TournamentRepository tournamentRepository,
            UserRepository userRepository) {
        return new CreateVisualizationTicketUseCase(
            visualizationTicketRepository,
            tournamentRepository,
            userRepository
        );
    }

    @Bean
    public GetVisualizationTicketUseCase getVisualizationTicketUseCase(
            VisualizationTicketRepository visualizationTicketRepository) {
        return new GetVisualizationTicketUseCase(visualizationTicketRepository);
    }

    @Bean
    public ListVisualizationTicketsUseCase listVisualizationTicketsUseCase(
            VisualizationTicketRepository visualizationTicketRepository) {
        return new ListVisualizationTicketsUseCase(visualizationTicketRepository);
    }

    @Bean
    public UpdateVisualizationTicketStatusUseCase updateVisualizationTicketStatusUseCase(
            VisualizationTicketRepository visualizationTicketRepository) {
        return new UpdateVisualizationTicketStatusUseCase(visualizationTicketRepository);
    }

    @Bean
    public UpdateVisualizationTicketTransactionStatusUseCase updateVisualizationTicketTransactionStatusUseCase(
            VisualizationTicketRepository visualizationTicketRepository) {
        return new UpdateVisualizationTicketTransactionStatusUseCase(visualizationTicketRepository);
    }

    @Bean
    public BlockVisualizationTicketUseCase blockVisualizationTicketUseCase(
            VisualizationTicketRepository visualizationTicketRepository) {
        return new BlockVisualizationTicketUseCase(visualizationTicketRepository);
    }

    @Bean
    public ValidateVisualizationQRUseCase validateVisualizationQRUseCase(
            VisualizationTicketRepository visualizationTicketRepository) {
        return new ValidateVisualizationQRUseCase(visualizationTicketRepository);
    }
}

