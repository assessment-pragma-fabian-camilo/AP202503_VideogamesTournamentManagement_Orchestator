package com.fc2o.usecase.stream;

import com.fc2o.model.stream.Stream;
import com.fc2o.model.stream.StreamType;
import com.fc2o.model.stream.gateways.StreamRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for updating a stream.
 */
public class UpdateStreamUseCase {
    private final StreamRepository streamRepository;

    public UpdateStreamUseCase(StreamRepository streamRepository) {
        this.streamRepository = streamRepository;
    }

    public Mono<Stream> execute(String streamId, String url, StreamType type) {
        if (streamId == null || streamId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Stream ID is required"));
        }

        return streamRepository.findById(streamId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Stream not found")))
                .flatMap(stream -> {
                    Stream updatedStream = stream.toBuilder()
                            .url(url != null ? url : stream.url())
                            .type(type != null ? type : stream.type())
                            .build();
                    return streamRepository.update(updatedStream);
                });
    }
}

