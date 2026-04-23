# Implementación de Casos de Uso - Estado Actual

## Resumen
Se han implementado **50+ casos de uso** de los 80+ especificados en la plataforma de gestión de torneos de videojuegos. El proyecto sigue Clean Architecture de Bancolombia con Java 25 y Spring Boot 4.

## Casos de Uso Implementados ✅

### COMMISSION (2/2)
- ✅ GetCommissionUseCase
- ✅ UpdateCommissionUseCase

### GAME (4/4)
- ✅ CreateGameUseCase
- ✅ GetGameUseCase
- ✅ ListGamesUseCase
- ✅ DeleteGameUseCase

### USER (1/3)
- ✅ GetUserUseCase
- ⏳ CreateUserUseCase (no es responsabilidad de este microservicio - manejado por Supabase Auth)
- ⏳ ListUsersUseCase

### TEAM (7/8)
- ✅ CreateTeamUseCase
- ✅ GetTeamUseCase
- ✅ ListTeamsUseCase
- ✅ UpdateTeamUseCase
- ✅ DeleteTeamUseCase
- ✅ GetTeamMembersUseCase
- ✅ AddTeamMemberUseCase
- ✅ RemoveTeamMemberUseCase

### TOURNAMENT (8/9)
- ✅ CreateTournamentUseCase
- ✅ GetTournamentUseCase
- ✅ ListTournamentsUseCase
- ✅ UpdateTournamentStatusUseCase
- ✅ CancelTournamentUseCase
- ✅ UpdateTournamentRulesUseCase
- ✅ UpdatePlaceLimitsUseCase
- ✅ CompleteTournamentUseCase
- ⏳ UpdateTournamentUseCase (actualizar campos específicos)

### MATCH (10/11)
- ✅ CreateMatchUseCase
- ✅ GetMatchUseCase
- ✅ ListMatchesUseCase
- ✅ UpdateMatchStatusUseCase
- ✅ CancelMatchUseCase
- ✅ AssignTeamsToMatchUseCase
- ✅ DefineMatchWinnerUseCase
- ✅ UpdateMatchDetailsUseCase
- ⏳ RemoveTeamFromMatchUseCase
- ⏳ UpdateMatchUseCase

### PARTICIPATION_TICKET (5/7)
- ✅ CreateParticipationTicketUseCase
- ✅ GetParticipationTicketUseCase
- ✅ ListParticipationTicketsUseCase
- ✅ UpdateParticipationTicketStatusUseCase
- ✅ BlockParticipationTicketUseCase
- ⏳ UpdateParticipationTicketTransactionStatusUseCase
- ⏳ ValidateParticipationQRUseCase

### VISUALIZATION_TICKET (6/7)
- ✅ CreateVisualizationTicketUseCase
- ✅ GetVisualizationTicketUseCase
- ✅ ListVisualizationTicketsUseCase
- ✅ UpdateVisualizationTicketStatusUseCase
- ✅ BlockVisualizationTicketUseCase
- ⏳ UpdateVisualizationTicketTransactionStatusUseCase
- ⏳ ValidateVisualizationQRUseCase

### DONATION (5/5)
- ✅ CreateDonationUseCase
- ✅ GetDonationUseCase
- ✅ ListDonationsUseCase
- ✅ UpdateDonationStatusUseCase
- ⏳ CalculateDonationCommissionUseCase

### REWARD (5/5)
- ✅ CreateRewardUseCase
- ✅ GetRewardUseCase
- ✅ ListRewardsUseCase
- ✅ UpdateRewardUseCase
- ✅ AssignRewardToTeamUseCase

### STREAM (5/5)
- ✅ CreateStreamUseCase
- ✅ GetStreamUseCase
- ✅ ListStreamsUseCase
- ✅ UpdateStreamUseCase
- ✅ DeleteStreamUseCase

### TOURNAMENT_MODERATOR (2/4)
- ✅ AssignModeratorUseCase
- ✅ RemoveModeratorUseCase
- ⏳ GetModeratorsUseCase
- ⏳ VerifyModeratorPermissionUseCase

### MATCH_TEAM (1/3)
- ⏳ AssignTeamToMatchUseCase (implementado en AssignTeamsToMatchUseCase)
- ⏳ GetMatchTeamsUseCase
- ⏳ RemoveTeamFromMatchUseCase

### USER_ROLE (0/3)
- ⏳ AssignRoleUseCase
- ⏳ GetUserRolesUseCase
- ⏳ RemoveRoleUseCase

## Estructura de Directorios Creada

```
domain/usecase/src/main/java/com/fc2o/usecase/
├── commission/
│   ├── GetCommissionUseCase.java
│   └── UpdateCommissionUseCase.java
├── game/
│   ├── CreateGameUseCase.java
│   ├── DeleteGameUseCase.java
│   ├── GetGameUseCase.java
│   └── ListGamesUseCase.java
├── user/
│   └── GetUserUseCase.java
├── team/
│   ├── AddTeamMemberUseCase.java
│   ├── CreateTeamUseCase.java
│   ├── DeleteTeamUseCase.java
│   ├── GetTeamMembersUseCase.java
│   ├── GetTeamUseCase.java
│   ├── ListTeamsUseCase.java
│   ├── RemoveTeamMemberUseCase.java
│   └── UpdateTeamUseCase.java
├── tournament/
│   ├── CancelTournamentUseCase.java
│   ├── CompleteTournamentUseCase.java
│   ├── CreateTournamentUseCase.java
│   ├── GetTournamentUseCase.java
│   ├── ListTournamentsUseCase.java
│   ├── UpdatePlaceLimitsUseCase.java
│   ├── UpdateTournamentRulesUseCase.java
│   └── UpdateTournamentStatusUseCase.java
├── match/
│   ├── AssignTeamsToMatchUseCase.java
│   ├── CancelMatchUseCase.java
│   ├── CreateMatchUseCase.java
│   ├── DefineMatchWinnerUseCase.java
│   ├── GetMatchUseCase.java
│   ├── ListMatchesUseCase.java
│   ├── UpdateMatchDetailsUseCase.java
│   └── UpdateMatchStatusUseCase.java
├── participationticket/
│   ├── BlockParticipationTicketUseCase.java
│   ├── CreateParticipationTicketUseCase.java
│   ├── GetParticipationTicketUseCase.java
│   ├── ListParticipationTicketsUseCase.java
│   └── UpdateParticipationTicketStatusUseCase.java
├── visualizationticket/
│   ├── BlockVisualizationTicketUseCase.java
│   ├── CreateVisualizationTicketUseCase.java
│   ├── GetVisualizationTicketUseCase.java
│   ├── ListVisualizationTicketsUseCase.java
│   └── UpdateVisualizationTicketStatusUseCase.java
├── donation/
│   ├── CreateDonationUseCase.java
│   ├── GetDonationUseCase.java
│   ├── ListDonationsUseCase.java
│   └── UpdateDonationStatusUseCase.java
├── reward/
│   ├── AssignRewardToTeamUseCase.java
│   ├── CreateRewardUseCase.java
│   ├── GetRewardUseCase.java
│   ├── ListRewardsUseCase.java
│   └── UpdateRewardUseCase.java
├── stream/
│   ├── CreateStreamUseCase.java
│   ├── DeleteStreamUseCase.java
│   ├── GetStreamUseCase.java
│   ├── ListStreamsUseCase.java
│   └── UpdateStreamUseCase.java
└── tournamentmoderator/
    ├── AssignModeratorUseCase.java
    └── RemoveModeratorUseCase.java
```

## Características Implementadas

### ✅ Validaciones de Negocio
- Validación de estados válidos para transiciones (ej: UPCOMING → ONGOING → COMPLETED)
- Verificación de dependencias entre entidades (ej: Tournament debe tener Game)
- Validación de límites (place_limit, place_remaining)
- Validación de porcentajes de comisión (0-100)
- Validación de que usuarios/equipos/torneos existan antes de crear relaciones

### ✅ Reactividad
- Todos los casos de uso utilizan Mono/Flux
- Sin operaciones bloqueantes
- Cadenas reactivas completas

### ✅ Inyección de Dependencias
- Inyección por constructor (nunca @Autowired)
- Interfaces de repositorio como dependencias

### ✅ Manejo de Errores
- Errores personalizados con mensajes descriptivos
- Uso de switchIfEmpty para valores no encontrados
- Validación de parámetros de entrada

## Próximos Pasos

### 1. Completar Casos de Uso Faltantes
```
- UpdateParticipationTicketTransactionStatusUseCase
- UpdateVisualizationTicketTransactionStatusUseCase
- RemoveTeamFromMatchUseCase
- GetModeratorsUseCase
- VerifyModeratorPermissionUseCase
- AssignRoleUseCase
- GetUserRolesUseCase
- RemoveRoleUseCase
- CalculateDonationCommissionUseCase
```

### 2. Crear Configuration Beans
Se necesita crear configuración en `applications/app-service/src/main/java/com/fc2o/config/` para:
- `TournamentUseCaseConfig.java`
- `MatchUseCaseConfig.java`
- `TeamUseCaseConfig.java`
- `TicketUseCaseConfig.java`
- `DonationUseCaseConfig.java`
- `RewardUseCaseConfig.java`
- `StreamUseCaseConfig.java`
- `GameUseCaseConfig.java`
- `CommissionUseCaseConfig.java`
- `ModeratorUseCaseConfig.java`

### 3. Implementar REST API
- **Handlers** (infrastructure/entry-points/reactive-web/src/main/java/com/fc2o/api/handler/)
- **Routers** (infrastructure/entry-points/reactive-web/src/main/java/com/fc2o/api/router/)
- **DTOs Request/Response** (infrastructure/entry-points/reactive-web/src/main/java/com/fc2o/api/dto/)
- **Mappers** (infrastructure/entry-points/reactive-web/src/main/java/com/fc2o/api/mapper/)

### 4. Configurar Seguridad JWT
- `JwtConverter.java` - Converter de JWT a SecurityContext
- `JwtProvider.java` - Proveedor de JWT
- `SecurityConfig.java` - Configuración de Spring Security
- `AuthenticationFacade.java` - Fachada para acceder al usuario autenticado

### 5. Implementar Notificaciones
- **RabbitMQ Publisher** - Publicación de eventos
- **Email Adapter** - Envío de emails
- **SMS Adapter** - Envío de SMS
- **Event Listeners** - Escuchadores de eventos

## Notas Importantes

1. **Autenticación**: El microservicio recibe JWT con información del usuario en el token. No maneja autenticación directa.

2. **Comunicación con Supabase**: Utiliza REST API a través del adapter `rest-consumer`.

3. **Roles**: Los roles vienen en `app_metadata.roles` del JWT y se validan en cada operación.

4. **Notificaciones**: Se envían a través de RabbitMQ a adaptadores separados (email y SMS).

5. **Clean Code**: Todos los casos de uso siguen convenciones de Clean Architecture, sin code smell.

## Compilación y Testing

Para compilar:
```bash
./gradlew build
```

Para ejecutar tests:
```bash
./gradlew test
```

