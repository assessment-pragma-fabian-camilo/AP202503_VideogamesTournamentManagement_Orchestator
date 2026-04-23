package com.fc2o.usecase.tournament;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import reactor.core.publisher.Flux;

/**
 * Use case for listing all tournaments or filtered by promoter.
 */
public class ListTournamentsUseCase {
    private final TournamentRepository tournamentRepository;

    public ListTournamentsUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    /**
     * List all tournaments.
     */
    public Flux<Tournament> executeAll() {
        return tournamentRepository.findAll();
    }

    /**
     * List tournaments promoted by a specific user.
     */
    public Flux<Tournament> executeByPromoter(String promoterId) {
        if (promoterId == null || promoterId.isBlank()) {
            return Flux.error(new IllegalArgumentException("Promoter ID is required"));
        }
        return tournamentRepository.findAllByPromoterId(promoterId);
    }
}

