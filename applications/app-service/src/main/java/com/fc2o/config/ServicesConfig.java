package com.fc2o.config;

import com.fc2o.model.tournament.Tournament;
import com.fc2o.model.user.Role;
import com.fc2o.model.user.User;
import com.fc2o.service.ValidatePermissionsService;
import com.fc2o.service.ValidateTournamentPermissionsService;
import com.fc2o.usecase.UseCase;
import com.fc2o.usecase.tournament.TournamentUseCases;
import com.fc2o.usecase.tournament.crud.RetrieveTournamentUseCase;
import com.fc2o.usecase.user.crud.RetrieveUserUseCase;
import com.fc2o.usecase.user.UserUseCases;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "com.fc2o.service",
  includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+Service")},
  useDefaultFilters = false
)
public class ServicesConfig {

  private final Map<Class<?>, Map<? extends UseCase, List<Role>>> roles;

  public ServicesConfig() {

    Map<TournamentUseCases, List<Role>> rolesPerTournamentUseCase = new HashMap<>();
    Map<? extends UseCase, List<Role>> rolesPerUserUseCase = Map.of(
      UserUseCases.VALIDATE_PERMISSIONS, List.of(Role.values()),
      UserUseCases.REGISTER_TOURNAMENT, List.of(Role.ADMINISTRATOR, Role.PROMOTER),
      UserUseCases.BLOCK_TICKET, List.of(Role.ADMINISTRATOR),
      UserUseCases.BAN_USER, List.of(Role.ADMINISTRATOR),
      UserUseCases.UNBAN_USER, List.of(Role.ADMINISTRATOR)
    );
    rolesPerTournamentUseCase.put(TournamentUseCases.BLOCK_TICKET, List.of(Role.ADMINISTRATOR, Role.PROMOTER));
    rolesPerTournamentUseCase.put(TournamentUseCases.DISQUALIFY_PARTICIPANT, List.of(Role.ADMINISTRATOR, Role.PROMOTER, Role.MODERATOR));
    rolesPerTournamentUseCase.put(TournamentUseCases.ADD_MOD, List.of(Role.ADMINISTRATOR, Role.MODERATOR, Role.PROMOTER));
    rolesPerTournamentUseCase.put(TournamentUseCases.START, List.of(Role.ADMINISTRATOR, Role.PROMOTER, Role.MODERATOR));
    rolesPerTournamentUseCase.put(TournamentUseCases.FORCE_START, List.of(Role.ADMINISTRATOR));
    rolesPerTournamentUseCase.put(TournamentUseCases.REGISTER_PARTICIPANT, List.of(Role.PARTICIPANT));
    rolesPerTournamentUseCase.put(TournamentUseCases.FORCE_REGISTER_PARTICIPANT, List.of(Role.ADMINISTRATOR, Role.PROMOTER));
    rolesPerTournamentUseCase.put(TournamentUseCases.PREREGISTER_PARTICIPANT, List.of(Role.PARTICIPANT));
    rolesPerTournamentUseCase.put(TournamentUseCases.FORCE_PREREGISTER_PARTICIPANT, List.of(Role.ADMINISTRATOR, Role.PROMOTER));
    rolesPerTournamentUseCase.put(TournamentUseCases.CANCEL, List.of(Role.ADMINISTRATOR, Role.PROMOTER));
    rolesPerTournamentUseCase.put(TournamentUseCases.FINALIZE, List.of(Role.ADMINISTRATOR, Role.MODERATOR, Role.PROMOTER));
    rolesPerTournamentUseCase.put(TournamentUseCases.FORCE_FINALIZE, List.of(Role.ADMINISTRATOR));
    rolesPerTournamentUseCase.put(TournamentUseCases.RESCHEDULE, List.of(Role.ADMINISTRATOR, Role.MODERATOR, Role.PROMOTER));
    rolesPerTournamentUseCase.put(TournamentUseCases.FORCE_RESCHEDULE, List.of(Role.ADMINISTRATOR));
    rolesPerTournamentUseCase.put(TournamentUseCases.RESCHEDULE_END_DATE, List.of(Role.ADMINISTRATOR, Role.MODERATOR, Role.PROMOTER));
    rolesPerTournamentUseCase.put(TournamentUseCases.REGISTER_MATCH, List.of(Role.ADMINISTRATOR, Role.MODERATOR, Role.PROMOTER));
    rolesPerTournamentUseCase.put(TournamentUseCases.START_MATCH, List.of(Role.ADMINISTRATOR, Role.MODERATOR, Role.PROMOTER));
    rolesPerTournamentUseCase.put(TournamentUseCases.FORCE_START_MATCH, List.of(Role.ADMINISTRATOR, Role.PROMOTER));
    rolesPerTournamentUseCase.put(TournamentUseCases.FINALIZE_MATCH, List.of(Role.ADMINISTRATOR, Role.MODERATOR, Role.PROMOTER));
    rolesPerTournamentUseCase.put(TournamentUseCases.CANCEL_MATCH, List.of(Role.ADMINISTRATOR, Role.MODERATOR, Role.PROMOTER));

    roles = Map.of(
      User.class, rolesPerUserUseCase,
      Tournament.class, rolesPerTournamentUseCase
    );
  }

  @Bean
  public ValidatePermissionsService permissionsService(RetrieveUserUseCase retrieveUserUseCase) {
    return new ValidatePermissionsService(roles, retrieveUserUseCase);
  }

  @Bean
  public ValidateTournamentPermissionsService validateTournamentPermissionsService(
    RetrieveUserUseCase retrieveUserUseCase,
    RetrieveTournamentUseCase retrieveTournamentUseCase
  ) {
    return new ValidateTournamentPermissionsService(roles, retrieveUserUseCase, retrieveTournamentUseCase);
  }
}
