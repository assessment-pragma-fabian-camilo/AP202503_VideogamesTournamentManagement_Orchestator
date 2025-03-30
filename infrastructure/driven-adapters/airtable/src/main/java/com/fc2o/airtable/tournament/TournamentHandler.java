package com.fc2o.airtable.tournament;

import com.fc2o.airtable.tournament.dto.FieldsDto;
import com.fc2o.airtable.tournament.dto.RecordDto;
import com.fc2o.airtable.tournament.dto.WrapperDto;
import com.fc2o.airtable.tournament.mapper.TournamentMapper;
import com.fc2o.model.tournament.gateways.TournamentRepository;
import com.fc2o.model.tournament.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TournamentHandler implements TournamentRepository {

  private final TournamentWebClient webClient;
  private final TournamentMapper mapper;

  @Override
  public Mono<Tournament> findById(String id) {
    return webClient.retrieveById(id)
      .map(mapper::toTournament);
  }

  @Override
  public Flux<Tournament> findAll() {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .map(mapper::toTournament);
  }

  @Override
  public Mono<Tournament> save(Tournament transaction) {
    return webClient
      .create(mapper.toWrapperDto(transaction))
      .map(dto -> mapper.toTournament(dto.records().getFirst()));
  }

  @Override
  public Mono<Tournament> update(Tournament transaction) {
    return null;
  }

  @Override
  public Mono<Tournament> patch(Tournament transaction) {
    return webClient
      .patch(mapper.toWrapperDto(transaction))
      .map(dto -> mapper.toTournament(dto.records().getFirst()));
  }

  @Override
  public Mono<Tournament> deleteById(String id) {
    return null;
  }

  @Override
  public Flux<Tournament> findAllByPromoterId(String promoterId) {
    return webClient.retrieveAll()
      .flatMapMany(dto -> Flux.fromIterable(dto.records()))
      .filter(record -> record.fields().promoterId().equals(promoterId))
      .map(mapper::toTournament);
  }

  @Override
  public Mono<Tournament> patchRegisterParticipant(String tournamentId, String participantId) {
    return webClient.retrieveById(tournamentId)
      .flatMap(tournament ->
        webClient.patch(
          WrapperDto.builder()
            .records(
              List.of(
                RecordDto.builder()
                  .id(tournamentId)
                  .fields(
                    FieldsDto.builder()
                      .preRegisteredParticipantIds(
                        tournament.fields().preRegisteredParticipantIds().stream()
                          .filter(s -> s.equals(participantId))
                          .collect(Collectors.toSet())
                      )
                      .participantIds(
                        Stream.concat(
                          tournament.fields().participantIds().stream(),
                          Stream.of(participantId)).collect(Collectors.toSet()
                        )
                      )
                      .placeRemaining((short) (tournament.fields().placeRemaining().intValue() - 1))
                      .build()
                  )
                  .build()
              )
            )
            .build()
        )
      )
      .map(wrapper -> mapper.toTournament(wrapper.records().getFirst()));
  }

  @Override
  public Mono<Tournament> patchDisqualify(String tournamentId, String participantId) {
    return webClient.retrieveById(tournamentId)
      .flatMap(tournament ->
        webClient.patch(
          WrapperDto.builder()
            .records(
              List.of(
                RecordDto.builder()
                  .id(tournamentId)
                  .fields(
                    FieldsDto.builder()
                      .preRegisteredParticipantIds(
                        tournament.fields().preRegisteredParticipantIds().stream()
                          .filter(s -> s.equals(participantId))
                          .collect(Collectors.toSet())
                      )
                      .participantIds(
                        tournament.fields().participantIds().stream()
                          .filter(s -> s.equals(participantId))
                          .collect(Collectors.toSet())
                      )
                      .disqualifiedParticipantIds(
                        Stream.concat(
                          tournament.fields().disqualifiedParticipantIds().stream(),
                          Stream.of(participantId)).collect(Collectors.toSet()
                        )
                      )
                      .build()
                  )
                  .build()
              )
            )
            .build()
        )
      )
      .map(wrapper -> mapper.toTournament(wrapper.records().getFirst()));
  }

  @Override
  public Mono<Tournament> patchPreRegisterParticipant(String tournamentId, String participantId) {
    return webClient.retrieveById(tournamentId)
      .flatMap(tournament ->
        webClient.patch(
          WrapperDto.builder()
            .records(
              List.of(
                RecordDto.builder()
                  .id(tournamentId)
                  .fields(
                    FieldsDto.builder()
                      .preRegisteredParticipantIds(
                        Stream.concat(
                          tournament.fields().preRegisteredParticipantIds().stream(),
                          Stream.of(participantId)).collect(Collectors.toSet()
                        )
                      )
                      .placeRemaining((short) (tournament.fields().placeRemaining().intValue() - 1))
                      .build()
                  )
                  .build()
              )
            )
            .build()
        )
      )
      .map(wrapper -> mapper.toTournament(wrapper.records().getFirst()));
  }
}
