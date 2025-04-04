package com.fc2o.usecase.user.business;

import com.fc2o.model.user.Status;
import com.fc2o.model.user.User;
import com.fc2o.service.ValidatePermissionsService;
import com.fc2o.usecase.user.UserUseCases;
import com.fc2o.usecase.user.crud.PatchUserUseCase;
import com.fc2o.usecase.user.crud.RetrieveUserUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;



@RequiredArgsConstructor
public class UnbanUserUseCase {

  private final RetrieveUserUseCase retrieveUserUseCase;
  private final PatchUserUseCase patchUserUseCase;
  private final ValidatePermissionsService permissionsService;

  public Mono<User> unbanUser(String adminId, String userId) {
    return retrieveUserUseCase.retrieveById(userId)
      .doOnNext(user -> permissionsService.validate(adminId, UserUseCases.UNBAN_USER))
      .map(user -> User.builder().status(Status.ACTIVE).build())
      .flatMap(patchUserUseCase::patch);
  }
}
