package com.fc2o.config.usecaseconfig;

import com.fc2o.model.stream.gateways.StreamRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.usecase.stream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Stream use cases.
 */
@Configuration
public class StreamUseCaseConfig {

    @Bean
    public CreateStreamUseCase createStreamUseCase(
            StreamRepository streamRepository,
            TournamentRepository tournamentRepository) {
        return new CreateStreamUseCase(streamRepository, tournamentRepository);
    }

    @Bean
    public GetStreamUseCase getStreamUseCase(
            StreamRepository streamRepository) {
        return new GetStreamUseCase(streamRepository);
    }

    @Bean
    public ListStreamsUseCase listStreamsUseCase(
            StreamRepository streamRepository) {
        return new ListStreamsUseCase(streamRepository);
    }

    @Bean
    public UpdateStreamUseCase updateStreamUseCase(
            StreamRepository streamRepository) {
        return new UpdateStreamUseCase(streamRepository);
    }

    @Bean
    public DeleteStreamUseCase deleteStreamUseCase(
            StreamRepository streamRepository) {
        return new DeleteStreamUseCase(streamRepository);
    }
}

