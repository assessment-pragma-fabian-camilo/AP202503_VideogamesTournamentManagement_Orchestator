package com.fc2o.usecase.stream;

import com.fc2o.model.stream.gateways.StreamRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for deleting a stream.
 */
public class DeleteStreamUseCase {
    private final StreamRepository streamRepository;

    public DeleteStreamUseCase(StreamRepository streamRepository) {
        this.streamRepository = streamRepository;
    }

    public Mono<Void> execute(String streamId) {
        if (streamId == null || streamId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Stream ID is required"));
        }
        return streamRepository.deleteById(streamId);
    }
}

