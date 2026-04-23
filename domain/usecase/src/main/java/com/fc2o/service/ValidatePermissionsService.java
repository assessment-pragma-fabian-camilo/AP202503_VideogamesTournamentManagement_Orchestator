package com.fc2o.service;

import com.fc2o.model.user.Role;
import com.fc2o.model.user.User;
import com.fc2o.usecase.UseCase;
import com.fc2o.usecase.user.crud.RetrieveUserUseCase;
import com.fc2o.usecase.user.UserUseCases;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Function;

@Getter
public class ValidatePermissionsService {

  protected final Map<Class<?>, Map<? extends UseCase, List<Role>>> roles;
  protected final RetrieveUserUseCase retrieveUserUseCase;
  protected final Map<Role, Map<Class<?>, Function<Object, Boolean>>> validatorRoles = HashMap.newHashMap(Role.values().length);

  public ValidatePermissionsService(
    Map<Class<?>, Map<? extends UseCase, List<Role>>> roles, RetrieveUserUseCase retrieveUserUseCase
  ) {
    this.roles = roles;
    this.retrieveUserUseCase = retrieveUserUseCase;
    Arrays.stream(Role.values())
      .forEach(role -> validatorRoles.put(role, Map.of(User.class, user -> ((User) user).roles().contains(role))));
  }

  public final void validate(String userId, List<Role> roles) {
    Mono<User> userMono = retrieveUserUseCase.retrieveById(userId);
    Flux.fromIterable(roles)
      .flatMap(role -> userMono.map(validatorRoles.get(role).get(User.class)))
      .filter(hasRole -> hasRole)
      .singleOrEmpty()
      .switchIfEmpty(Mono.error(new RuntimeException("No tienes permisos suficientes para realizar esta operación")))
      .then();
  }

  public final void validate(String userId, UserUseCases userUseCases) {
    Mono<User> userMono = retrieveUserUseCase.retrieveById(userId);
    Flux.fromIterable(roles.get(User.class).get(userUseCases))
      .flatMap(role -> userMono.map(validatorRoles.get(role).get(User.class)))
      .filter(hasRole -> hasRole)
      .singleOrEmpty()
      .switchIfEmpty(Mono.error(new RuntimeException("No tienes permisos suficientes para realizar esta operación")))
      .then();
  }
}
