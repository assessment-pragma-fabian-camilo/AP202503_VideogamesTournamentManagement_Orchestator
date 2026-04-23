package com.fc2o.usecase.registration.crud;

import com.fc2o.model.registration.Registration;
import com.fc2o.model.registration.gateways.RegistrationRepository;
import reactor.core.publisher.Mono;

public class PatchRegistrationUseCase {
  private final RegistrationRepository registrationRepository;

  public PatchRegistrationUseCase(RegistrationRepository registrationRepository) {
    this.registrationRepository = registrationRepository;
  }

  public Mono<Registration> patch(Registration registration) {
    return registrationRepository.patch(registration);
  }
  public Mono<Registration> patchByTournamentIdAndParticipantId(String tournamentId, String participantId, Registration registration) {
    return registrationRepository.patchByTournamentIdAndParticipantId(tournamentId, participantId, registration);
  }
}
