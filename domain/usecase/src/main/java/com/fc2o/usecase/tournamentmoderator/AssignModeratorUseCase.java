package com.fc2o.usecase.tournamentmoderator;

import com.fc2o.model.tournamentmoderator.TournamentModerator;
import com.fc2o.model.tournamentmoderator.gateways.TournamentModeratorRepository;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

/**
 * Use case for assigning a moderator to a tournament.
 * Business rule: Only PROMOTER or ADMIN can assign moderators.
 */
public class AssignModeratorUseCase {
    private final TournamentModeratorRepository tournamentModeratorRepository;
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;

    public AssignModeratorUseCase(
            TournamentModeratorRepository tournamentModeratorRepository,
            TournamentRepository tournamentRepository,
            UserRepository userRepository) {
        this.tournamentModeratorRepository = tournamentModeratorRepository;
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
    }

    public Mono<TournamentModerator> execute(String tournamentId, String userId) {
        if (tournamentId == null || tournamentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Tournament ID is required"));
        }
        if (userId == null || userId.isBlank()) {
            return Mono.error(new IllegalArgumentException("User ID is required"));
        }

        return userRepository.isActiveUser(userId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found")))
                .flatMap(user -> tournamentRepository.findById(tournamentId)
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Tournament not found")))
                        .flatMap(tournament -> {
                            TournamentModerator newModerator = TournamentModerator.builder()
                                    .tournamentId(tournamentId)
                                    .userId(userId)
                                    .build();

                            return tournamentModeratorRepository.save(newModerator);
                        }));
    }
}

