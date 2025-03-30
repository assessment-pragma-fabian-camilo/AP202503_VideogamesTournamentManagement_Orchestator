package com.fc2o.airtable.match;

import com.fc2o.airtable.match.mapper.MatchMapper;
import com.fc2o.model.match.Match;
import com.fc2o.model.match.gateways.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MatchHandler implements MatchRepository {

  private final MatchWebClient webClient;
  private final MatchMapper mapper;

  @Override
  public Mono<Match> findById(String id) {
    return webClient.retrieveById(id)
      .map(mapper::toMatch);
  }

  @Override
  public Flux<Match> findAll() {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .map(mapper::toMatch);
  }

  @Override
  public Flux<Match> findAllByTournamentId(String tournamentId) {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .filter(dto -> dto.fields().tournamentId().equals(tournamentId))
      .map(mapper::toMatch);
  }

  @Override
  public Flux<Match> findAllByParticipantIdAndTournamentId(String participantId, String tournamentId) {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .filter(dto -> dto.fields().tournamentId().equals(tournamentId))
      .filter(dto -> dto.fields().participantIds().contains(participantId))
      .map(mapper::toMatch);
  }

  @Override
  public Mono<Match> save(Match match) {
    return webClient
      .create(mapper.toWrapperDto(match))
      .map(dto -> mapper.toMatch(dto.records().getFirst()));
  }

  @Override
  public Mono<Match> update(Match match) {
    return null;
  }

  @Override
  public Mono<Match> patch(Match match) {
    return webClient
      .patch(mapper.toWrapperDto(match))
      .map(dto -> mapper.toMatch(dto.records().getFirst()));
  }

  @Override
  public Mono<Match> deleteById(String id) {
    return null;
  }
}
