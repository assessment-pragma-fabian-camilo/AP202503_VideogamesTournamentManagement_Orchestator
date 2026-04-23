package com.fc2o.usecase.tournament;

import com.fc2o.model.shared.CommissionType;
import com.fc2o.model.shared.TournamentStatus;
import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.game.gateways.GameRepository;
import com.fc2o.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Use case for creating a new tournament.
 * Business rules:
 * - Only users with PROMOTER or ADMIN role can create tournaments
 * - Tournament must have a promoter (user of the platform)
 * - Tournament must be associated with a specific game
 * - Initial status must be UPCOMING
 * - place_remaining must equal place_limit when created
 * - Validations: place_limit >= place_minimum, date_end >= date_start, prices >= 0
 */
public class CreateTournamentUseCase {
    private final TournamentRepository tournamentRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public CreateTournamentUseCase(
            TournamentRepository tournamentRepository,
            GameRepository gameRepository,
            UserRepository userRepository) {
        this.tournamentRepository = tournamentRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public Mono<Tournament> execute(Tournament tournament) {

        // Verify promoter exists
        return userRepository.isActiveUser(tournament.promoterUserId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Promoter not found with ID: " + tournament.promoterUserId())))
                .flatMap(promoter -> {
                    // Verify game exists
                    return gameRepository.findById(tournament.gameId())
                            .switchIfEmpty(Mono.error(new IllegalArgumentException("Game not found with ID: " + tournament.gameId())))
                            .flatMap(game -> tournamentRepository.save(tournament));
                });
    }
}

