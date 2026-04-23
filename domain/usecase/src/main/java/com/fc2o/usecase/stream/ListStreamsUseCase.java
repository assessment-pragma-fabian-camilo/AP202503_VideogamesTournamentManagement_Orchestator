package com.fc2o.usecase.stream;

import com.fc2o.model.stream.Stream;
import com.fc2o.model.stream.gateways.StreamRepository;
import reactor.core.publisher.Flux;

/**
 * Use case for listing streams by tournament.
 */
public class ListStreamsUseCase {
    private final StreamRepository streamRepository;

    public ListStreamsUseCase(StreamRepository streamRepository) {
        this.streamRepository = streamRepository;
    }

    /**
     * List all streams for a specific tournament.
     */
    public Flux<Stream> executeByTournament(String tournamentId) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Flux.error(new IllegalArgumentException("Tournament ID is required"));
        }
        return streamRepository.findAllByTournamentId(tournamentId);
    }
}

