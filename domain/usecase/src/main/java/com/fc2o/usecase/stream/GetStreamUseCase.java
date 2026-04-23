package com.fc2o.usecase.stream;

import com.fc2o.model.stream.Stream;
import com.fc2o.model.stream.gateways.StreamRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for retrieving a stream by ID.
 */
public class GetStreamUseCase {
    private final StreamRepository streamRepository;

    public GetStreamUseCase(StreamRepository streamRepository) {
        this.streamRepository = streamRepository;
    }

    public Mono<Stream> execute(String streamId) {
        if (streamId == null || streamId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Stream ID is required"));
        }
        return streamRepository.findById(streamId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Stream not found with ID: " + streamId)));
    }
}

