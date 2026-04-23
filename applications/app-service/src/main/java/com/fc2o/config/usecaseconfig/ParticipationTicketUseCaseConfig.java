package com.fc2o.config.usecaseconfig;

import com.fc2o.model.participationticket.gateways.ParticipationTicketRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.user.gateways.UserRepository;
import com.fc2o.usecase.participationticket.*;
import com.fc2o.usecase.tournamentteam.AutoRegisterTeamInTournamentUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Participation Ticket use cases.
 */
@Configuration
public class ParticipationTicketUseCaseConfig {

    @Bean
    public CreateParticipationTicketUseCase createParticipationTicketUseCase(
            ParticipationTicketRepository participationTicketRepository,
            TournamentRepository tournamentRepository,
            UserRepository userRepository) {
        return new CreateParticipationTicketUseCase(
            participationTicketRepository,
            tournamentRepository,
            userRepository
        );
    }

    @Bean
    public GetParticipationTicketUseCase getParticipationTicketUseCase(
            ParticipationTicketRepository participationTicketRepository) {
        return new GetParticipationTicketUseCase(participationTicketRepository);
    }

    @Bean
    public ListParticipationTicketsUseCase listParticipationTicketsUseCase(
            ParticipationTicketRepository participationTicketRepository) {
        return new ListParticipationTicketsUseCase(participationTicketRepository);
    }

    @Bean
    public UpdateParticipationTicketStatusUseCase updateParticipationTicketStatusUseCase(
            ParticipationTicketRepository participationTicketRepository,
            AutoRegisterTeamInTournamentUseCase autoRegisterTeamInTournamentUseCase) {
        return new UpdateParticipationTicketStatusUseCase(participationTicketRepository, autoRegisterTeamInTournamentUseCase);
    }

    @Bean
    public UpdateParticipationTicketTransactionStatusUseCase updateParticipationTicketTransactionStatusUseCase(
            ParticipationTicketRepository participationTicketRepository) {
        return new UpdateParticipationTicketTransactionStatusUseCase(participationTicketRepository);
    }

    @Bean
    public BlockParticipationTicketUseCase blockParticipationTicketUseCase(
            ParticipationTicketRepository participationTicketRepository) {
        return new BlockParticipationTicketUseCase(participationTicketRepository);
    }

    @Bean
    public ValidateParticipationQRUseCase validateParticipationQRUseCase(
            ParticipationTicketRepository participationTicketRepository) {
        return new ValidateParticipationQRUseCase(participationTicketRepository);
    }
}

