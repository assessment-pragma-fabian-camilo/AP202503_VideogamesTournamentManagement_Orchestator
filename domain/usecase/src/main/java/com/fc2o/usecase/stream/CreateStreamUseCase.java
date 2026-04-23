package com.fc2o.usecase.stream;

import com.fc2o.model.stream.Stream;
import com.fc2o.model.stream.StreamPlatform;
import com.fc2o.model.stream.StreamType;
import com.fc2o.model.stream.gateways.StreamRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for creating a stream.
 * Business rules:
 * - Only PROMOTER, MODERATOR or ADMIN can create streams
 * - Only for tournaments in UPCOMING or ONGOING status
 * - URL must be valid
 */
public class CreateStreamUseCase {
    private final StreamRepository streamRepository;
    private final TournamentRepository tournamentRepository;

    public CreateStreamUseCase(
            StreamRepository streamRepository,
            TournamentRepository tournamentRepository) {
        this.streamRepository = streamRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public Mono<Stream> execute(String tournamentId, StreamPlatform platform, String url, StreamType type) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }
        if (platform == null) {
            return Mono.error(new IllegalArgumentException("Platform is required"));
        }
        if (url == null || url.isBlank()) {
            return Mono.error(new IllegalArgumentException("URL is required"));
        }

        return tournamentRepository.findById(tournamentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found")))
                .flatMap(tournament -> {
                    if (!tournament.isUpcoming() && !tournament.isOngoing()) {
                        return Mono.error(new IllegalArgumentException(
                                "Can only add streams to UPCOMING or ONGOING tournaments"));
                    }

                    Stream newStream = Stream.builder()
                            .tournamentId(tournamentId)
                            .platform(platform)
                            .url(url)
                            .type(type)
                            .build();

                    return streamRepository.save(newStream);
                });
    }
}

