package com.fc2o.usecase.tournament;

import com.fc2o.model.shared.TournamentStatus;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for updating tournament rules and description.
 * Business rule: Only PROMOTER, MODERATOR or ADMIN can update rules/description
 * and only for tournaments in status UPCOMING or ONGOING.
 */
public class UpdateTournamentRulesUseCase {
    private final TournamentRepository tournamentRepository;

    public UpdateTournamentRulesUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Mono<Tournament> execute(String tournamentId, String rules, String description) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }

        return tournamentRepository.findById(tournamentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found with ID: " + tournamentId)))
                .flatMap(tournament -> {
                    TournamentStatus status = tournament.status();
                    if (status != TournamentStatus.UPCOMING && status != TournamentStatus.ONGOING) {
                        return Mono.error(new IllegalArgumentException(
                                "Cannot update rules for tournament in status: " + status));
                    }

                    Tournament updatedTournament = tournament.toBuilder()
                            .rules(rules)
                            .build();
                    
                    return tournamentRepository.update(updatedTournament);
                });
    }
}

