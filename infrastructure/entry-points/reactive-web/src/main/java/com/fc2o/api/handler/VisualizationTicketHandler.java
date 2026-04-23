package com.fc2o.api.handler;

import com.fc2o.api.config.JwtUserContextExtractor;
import com.fc2o.api.dto.request.ticket.CreateVisualizationTicketRequest;
import com.fc2o.api.dto.request.ticket.UpdateTicketStatusRequest;
import com.fc2o.api.dto.request.ticket.UpdateTicketTransactionStatusRequest;
import com.fc2o.api.dto.request.ticket.ValidateQrRequest;
import com.fc2o.api.dto.response.generic.ApiResponse;
import com.fc2o.api.mapper.ticket.TicketMapper;
import com.fc2o.api.security.DomainAuthorizationService;
import com.fc2o.api.validator.ValidatorHandler;
import com.fc2o.usecase.visualizationticket.BlockVisualizationTicketUseCase;
import com.fc2o.usecase.visualizationticket.CreateVisualizationTicketUseCase;
import com.fc2o.usecase.visualizationticket.GetVisualizationTicketUseCase;
import com.fc2o.usecase.visualizationticket.ListVisualizationTicketsUseCase;
import com.fc2o.usecase.visualizationticket.UpdateVisualizationTicketStatusUseCase;
import com.fc2o.usecase.visualizationticket.UpdateVisualizationTicketTransactionStatusUseCase;
import com.fc2o.usecase.visualizationticket.ValidateVisualizationQRUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class VisualizationTicketHandler {

    private final CreateVisualizationTicketUseCase createVisualizationTicketUseCase;
    private final GetVisualizationTicketUseCase getVisualizationTicketUseCase;
    private final ListVisualizationTicketsUseCase listVisualizationTicketsUseCase;
    private final UpdateVisualizationTicketStatusUseCase updateVisualizationTicketStatusUseCase;
    private final UpdateVisualizationTicketTransactionStatusUseCase updateVisualizationTicketTransactionStatusUseCase;
    private final BlockVisualizationTicketUseCase blockVisualizationTicketUseCase;
    private final ValidateVisualizationQRUseCase validateVisualizationQRUseCase;
    private final TicketMapper ticketMapper;
    private final ValidatorHandler validatorHandler;
    private final JwtUserContextExtractor jwtUserContextExtractor;
    private final DomainAuthorizationService authorizationService;

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> createTicket(ServerRequest request) {
        return jwtUserContextExtractor.extractUserId()
                .zipWith(request.bodyToMono(CreateVisualizationTicketRequest.class).doOnNext(validatorHandler::validate))
                .flatMap(tuple -> createVisualizationTicketUseCase.execute(
                        tuple.getT2().tournamentId(),
                        tuple.getT1(),
                        tuple.getT2().amount()))
                .map(ticketMapper::toResponse)
                .flatMap(response -> ServerResponse.created(URI.create("/api/v1/visualization-tickets/" + response.id()))
                        .bodyValue(ApiResponse.created(response)));
    }

    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> getTicket(ServerRequest request) {
        String ticketId = request.pathVariable("id");
        return getVisualizationTicketUseCase.execute(ticketId)
                .flatMap(ticket -> authorizationService.authorizeOwnedOrTournamentManagedResource(ticket.userId(), ticket.tournamentId(), true)
                        .thenReturn(ticket))
                .map(ticketMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> listTicketsByTournament(ServerRequest request) {
        String tournamentId = request.pathVariable("tournamentId");
        return authorizationService.authorizeTournamentManagement(tournamentId, true)
                .thenMany(listVisualizationTicketsUseCase.executeByTournament(tournamentId))
                .map(ticketMapper::toResponse)
                .collectList()
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> updateTicketStatus(ServerRequest request) {
        String ticketId = request.pathVariable("id");
        return getVisualizationTicketUseCase.execute(ticketId)
                .flatMap(ticket -> authorizationService.authorizeTournamentManagement(ticket.tournamentId(), true)
                        .then(request.bodyToMono(UpdateTicketStatusRequest.class).doOnNext(validatorHandler::validate))
                        .flatMap(body -> updateVisualizationTicketStatusUseCase.execute(ticketId, body.status())))
                .map(ticketMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> updateTicketTransactionStatus(ServerRequest request) {
        String ticketId = request.pathVariable("id");
        return getVisualizationTicketUseCase.execute(ticketId)
                .flatMap(ticket -> authorizationService.authorizeTournamentManagement(ticket.tournamentId(), true)
                        .then(request.bodyToMono(UpdateTicketTransactionStatusRequest.class).doOnNext(validatorHandler::validate))
                        .flatMap(body -> updateVisualizationTicketTransactionStatusUseCase.execute(ticketId, body.status())))
                .map(ticketMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','ADMIN')")
    public Mono<ServerResponse> blockTicket(ServerRequest request) {
        String ticketId = request.pathVariable("id");
        return getVisualizationTicketUseCase.execute(ticketId)
                .flatMap(ticket -> authorizationService.authorizeTournamentManagement(ticket.tournamentId(), false)
                        .then(blockVisualizationTicketUseCase.execute(ticketId)))
                .map(ticketMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }

    @PreAuthorize("hasAnyRole('PROMOTER','MOD','MODERATOR','ADMIN')")
    public Mono<ServerResponse> validateQr(ServerRequest request) {
        String ticketId = request.pathVariable("id");
        return getVisualizationTicketUseCase.execute(ticketId)
                .flatMap(ticket -> authorizationService.authorizeTournamentManagement(ticket.tournamentId(), true)
                        .then(request.bodyToMono(ValidateQrRequest.class).doOnNext(validatorHandler::validate))
                        .map(body -> Base64.getDecoder().decode(body.qr()))
                        .flatMap(qr -> validateVisualizationQRUseCase.execute(ticketId, qr)))
                .map(ticketMapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(ApiResponse.success(response)));
    }
}

