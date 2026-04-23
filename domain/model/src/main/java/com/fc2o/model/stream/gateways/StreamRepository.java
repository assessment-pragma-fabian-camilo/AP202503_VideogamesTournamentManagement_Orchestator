package com.fc2o.model.stream.gateways;

import com.fc2o.model.stream.Stream;
import com.fc2o.model.stream.StreamType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for Stream repository operations.
 * Defines all operations needed by stream use cases.
 */
public interface StreamRepository {
    /**
     * Find stream by ID.
     */
    Mono<Stream> findById(String id);

    /**
     * Find all streams for a specific tournament.
     */
    Flux<Stream> findAllByTournamentId(String tournamentId);

    /**
     * Find all streams by type.
     */
    Flux<Stream> findAllByType(StreamType type);

    /**
     * Create a new stream.
     */
    Mono<Stream> save(Stream stream);

    /**
     * Update an entire stream.
     */
    Mono<Stream> update(Stream stream);

    /**
     * Delete stream by ID.
     */
    Mono<Void> deleteById(String streamId);
}

