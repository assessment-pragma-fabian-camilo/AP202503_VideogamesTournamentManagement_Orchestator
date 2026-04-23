package com.fc2o.usecase.tournament;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for updating tournament basic information.
 */
public class UpdateTournamentUseCase {
    private final TournamentRepository tournamentRepository;

    public UpdateTournamentUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Mono<Tournament> execute(
            String tournamentId,
            String name,
            String dateStart,
            String dateEnd) {

        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }

        return tournamentRepository.findById(tournamentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found")))
                .flatMap(tournament -> {
                    Tournament updatedTournament = tournament.toBuilder()
                            .name(name != null ? name : tournament.name())
                            .dateStart(dateStart != null ? dateStart : tournament.dateStart())
                            .dateEnd(dateEnd != null ? dateEnd : tournament.dateEnd())
                            .build();
                    return tournamentRepository.update(updatedTournament);
                });
    }
}

