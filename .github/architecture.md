# Arquitectura del Proyecto: Scaffold Bancolombia

## Patrón de Diseño

Utilizamos **Clean Architecture** con la implementacón que tiene Bancolombia:

1. **Domain**: Modelos de negocio y reglas lógicas (sin dependencias externas). Dentro de domain se encuentran los
   UseCases y los Models.
   - **UseCases**: Representan las acciones y reglas de negocio.
   - **Models**: Representan las entidades del negocio
2. **Applications**: Toda la capa externa de la aplicación. application.yaml y construcción de beans de Spring para los
   UseCases
3. **Infrastructure**: Implementaciones de persistencia y adaptadores externos. En esta capa se
   encuentran los EntryPoints y los DrivenAdapters, que son las implementaciones concretas para interactuar con el mundo.
   - **EntryPoints**: Actuan como puertos de entrada hacia la aplicación.
   - **DrivenAdapters**: Actuan como puertos de salida desde la aplicación hacia diferentes fuentes externas.

## Flujo de Datos

- Los controladores solo reciben y devuelven **DTOs**.
- La comunicación entre capa domain e infrastructure se hace mediante **interfaces**.