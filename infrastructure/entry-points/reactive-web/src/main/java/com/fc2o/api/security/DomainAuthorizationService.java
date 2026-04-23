package com.fc2o.api.security;

import com.fc2o.api.config.JwtUserContextExtractor;
import com.fc2o.model.userrole.Role;
import com.fc2o.usecase.match.GetMatchUseCase;
import com.fc2o.usecase.team.GetTeamUseCase;
import com.fc2o.usecase.tournament.GetTournamentUseCase;
import com.fc2o.usecase.tournamentmoderator.VerifyModeratorPermissionUseCase;
import com.fc2o.usecase.visualizationticket.ListVisualizationTicketsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DomainAuthorizationService {

    private final JwtUserContextExtractor jwtUserContextExtractor;
    private final GetTournamentUseCase getTournamentUseCase;
    private final GetMatchUseCase getMatchUseCase;
    private final GetTeamUseCase getTeamUseCase;
    private final VerifyModeratorPermissionUseCase verifyModeratorPermissionUseCase;
    private final ListVisualizationTicketsUseCase listVisualizationTicketsUseCase;

    public Mono<Void> authorizeTournamentManagement(String tournamentId, boolean allowModerator) {
        return Mono.zip(jwtUserContextExtractor.extractUserId(), jwtUserContextExtractor.extractRoles())
                .flatMap(tuple -> authorizeTournamentManagement(tuple.getT1(), tuple.getT2(), tournamentId, allowModerator));
    }

    public Mono<Void> authorizeMatchManagement(String matchId, boolean allowModerator) {
        return getMatchUseCase.execute(matchId)
                .flatMap(match -> authorizeTournamentManagement(match.tournamentId(), allowModerator));
    }

    public Mono<Void> authorizeTeamLeaderOrPrivileged(String teamId) {
        return Mono.zip(jwtUserContextExtractor.extractUserId(), jwtUserContextExtractor.extractRoles())
                .flatMap(tuple -> {
                    String userId = tuple.getT1();
                    Set<String> roles = tuple.getT2();
                    if (isAdminOrPromoter(roles)) {
                        return Mono.empty();
                    }
                    return getTeamUseCase.execute(teamId)
                            .flatMap(team -> team.isLeader(userId)
                                    ? Mono.<Void>empty()
                                    : Mono.error(new SecurityException("Only the team leader, PROMOTER or ADMIN can manage this team")));
                });
    }

    public Mono<Void> authorizeOwnedOrTournamentManagedResource(String ownerUserId, String tournamentId, boolean allowModerator) {
        return Mono.zip(jwtUserContextExtractor.extractUserId(), jwtUserContextExtractor.extractRoles())
                .flatMap(tuple -> {
                    String currentUserId = tuple.getT1();
                    Set<String> roles = tuple.getT2();
                    if (roles.contains(Role.ADMIN.name()) || currentUserId.equals(ownerUserId)) {
                        return Mono.empty();
                    }
                    return authorizeTournamentManagement(currentUserId, roles, tournamentId, allowModerator);
                });
    }

    public Mono<Void> authorizeRoleAssignment(String targetUserId, Role role) {
        return Mono.zip(jwtUserContextExtractor.extractUserId(), jwtUserContextExtractor.extractRoles())
                .flatMap(tuple -> {
                    String currentUserId = tuple.getT1();
                    Set<String> roles = tuple.getT2();
                    if (roles.contains(Role.ADMIN.name())) {
                        return Mono.empty();
                    }
                    boolean isSelfAssignment = currentUserId.equals(targetUserId);
                    boolean allowedSelfRole = role == Role.PLAYER || role == Role.MOD;
                    if (isSelfAssignment && allowedSelfRole) {
                        return Mono.empty();
                    }
                    return Mono.error(new SecurityException("You are not allowed to assign this role"));
                });
    }

    public Mono<Void> authorizeSelfOrAdmin(String targetUserId) {
        return Mono.zip(jwtUserContextExtractor.extractUserId(), jwtUserContextExtractor.extractRoles())
                .flatMap(tuple -> {
                    String currentUserId = tuple.getT1();
                    Set<String> roles = tuple.getT2();
                    if (roles.contains(Role.ADMIN.name()) || currentUserId.equals(targetUserId)) {
                        return Mono.empty();
                    }
                    return Mono.error(new SecurityException("The current user is not allowed to access this resource"));
                });
    }

    public Mono<Void> authorizeStreamAccess(String tournamentId) {
        return Mono.zip(jwtUserContextExtractor.extractUserId(), jwtUserContextExtractor.extractRoles())
                .flatMap(tuple -> {
                    String currentUserId = tuple.getT1();
                    Set<String> roles = tuple.getT2();
                    if (roles.contains(Role.ADMIN.name())) {
                        return Mono.empty();
                    }
                    return getTournamentUseCase.execute(tournamentId)
                            .flatMap(tournament -> {
                                if (currentUserId.equals(tournament.promoterUserId()) || tournament.isFree()) {
                                    return Mono.<Void>empty();
                                }
                                return listVisualizationTicketsUseCase.executeByTournament(tournamentId)
                                        .filter(ticket -> currentUserId.equals(ticket.userId()) && ticket.isUsed())
                                        .hasElements()
                                        .flatMap(hasUsedTicket -> hasUsedTicket
                                                ? Mono.<Void>empty()
                                                : Mono.error(new SecurityException("The current user does not have access to this stream")));
                            });
                });
    }

    public Mono<Void> authorizeModeratorAssignment(String tournamentId) {
        return authorizeTournamentManagement(tournamentId, false);
    }

    public Mono<Void> authorizeModeratorRemoval(String tournamentId, String targetUserId) {
        return Mono.zip(jwtUserContextExtractor.extractUserId(), jwtUserContextExtractor.extractRoles())
                .flatMap(tuple -> {
                    String currentUserId = tuple.getT1();
                    Set<String> roles = tuple.getT2();
                    if (roles.contains(Role.ADMIN.name()) || currentUserId.equals(targetUserId)) {
                        return Mono.empty();
                    }
                    return authorizeTournamentManagement(currentUserId, roles, tournamentId, false);
                });
    }

    private Mono<Void> authorizeTournamentManagement(String currentUserId, Set<String> roles, String tournamentId, boolean allowModerator) {
        if (roles.contains(Role.ADMIN.name())) {
            return Mono.empty();
        }
        return getTournamentUseCase.execute(tournamentId)
                .flatMap(tournament -> {
                    if (currentUserId.equals(tournament.promoterUserId())) {
                        return Mono.<Void>empty();
                    }
                    if (allowModerator && isModeratorRole(roles)) {
                        return verifyModeratorPermissionUseCase.execute(tournamentId, currentUserId)
                                .flatMap(isModerator -> isModerator
                                        ? Mono.<Void>empty()
                                        : Mono.error(new SecurityException("The current user cannot manage this tournament")));
                    }
                    return Mono.error(new SecurityException("The current user cannot manage this tournament"));
                });
    }

    private boolean isAdminOrPromoter(Set<String> roles) {
        return roles.contains(Role.ADMIN.name()) || roles.contains(Role.PROMOTER.name());
    }

    private boolean isModeratorRole(Set<String> roles) {
        return roles.contains(Role.MOD.name()) || roles.contains("MODERATOR");
    }
}


