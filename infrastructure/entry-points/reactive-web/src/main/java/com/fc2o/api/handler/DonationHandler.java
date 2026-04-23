package com.fc2o.api.handler;

import com.fc2o.api.config.JwtUserContextExtractor;
import com.fc2o.api.dto.request.donation.CreateDonationRequest;
import com.fc2o.api.dto.request.donation.UpdateDonationStatusRequest;
import com.fc2o.api.dto.response.generic.ApiResponse;
import com.fc2o.api.mapper.donation.DonationMapper;
import com.fc2o.api.security.DomainAuthorizationService;
import com.fc2o.api.validator.ValidatorHandler;
import com.fc2o.usecase.donation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class DonationHandler {
    private final CreateDonationUseCase createDonationUseCase;
    private final GetDonationUseCase getDonationUseCase;
    private final ListDonationsUseCase listDonationsUseCase;
    private final UpdateDonationStatusUseCase updateDonationStatusUseCase;
    private final CalculateDonationCommissionUseCase calculateDonationCommissionUseCase;
    private final DonationMapper donationMapper;
    private final ValidatorHandler validatorHandler;
    private final JwtUserContextExtractor jwtUserContextExtractor;
    private final DomainAuthorizationService authorizationService;

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> createDonation(ServerRequest request) {
        return jwtUserContextExtractor.extractUserId()
                .zipWith(request.bodyToMono(CreateDonationRequest.class)
                        .doOnNext(validatorHandler::validate))
                .map(t -> donationMapper.toDomain(t.getT1(), t.getT2()))
                .flatMap(createDonationUseCase::execute)
                .flatMap(donation -> ServerResponse
                    .created(URI.create("/api/v1/donations/" + donation.id()))
                    .bodyValue(ApiResponse.created(donationMapper.toResponse(donation))));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> getDonation(ServerRequest request) {
        String donationId = request.pathVariable("id");
        return getDonationUseCase.execute(donationId)
                .flatMap(donation -> authorizationService.authorizeOwnedOrTournamentManagedResource(
                                donation.userId(),
                                donation.tournamentId(),
                                true)
                        .thenReturn(donation))
                .flatMap(donation -> ServerResponse
                    .ok()
                    .bodyValue(ApiResponse.success(donationMapper.toResponse(donation))));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> listDonations(ServerRequest request) {
        String tournamentId = request.pathVariable("tournamentId");
        return authorizationService.authorizeTournamentManagement(tournamentId, true)
                .thenMany(listDonationsUseCase.executeByTournament(tournamentId))
                .map(donationMapper::toResponse)
                .collectList()
                .flatMap(donations -> ServerResponse
                    .ok()
                    .bodyValue(ApiResponse.success(donations)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> updateDonationStatus(ServerRequest request) {
        String donationId = request.pathVariable("id");
        return getDonationUseCase.execute(donationId)
                .flatMap(donation -> authorizationService.authorizeTournamentManagement(donation.tournamentId(), true)
                        .thenReturn(donation))
                .then(request.bodyToMono(UpdateDonationStatusRequest.class).doOnNext(validatorHandler::validate))
                .flatMap(body -> updateDonationStatusUseCase.execute(donationId, body.status()))
                .map(donationMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> calculateDonationCommission(ServerRequest request) {
        String donationId = request.pathVariable("id");
        return getDonationUseCase.execute(donationId)
                .flatMap(donation -> authorizationService.authorizeOwnedOrTournamentManagedResource(
                                donation.userId(),
                                donation.tournamentId(),
                                true)
                        .thenReturn(donation))
                .then(calculateDonationCommissionUseCase.execute(donationId))
                .flatMap(commission -> ServerResponse.ok().bodyValue(ApiResponse.success(commission)));
    }
}

