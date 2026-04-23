# Convenciones de Desarrollo

## Naming

- Interfaces: No usar prefijo "I" (ej. usar `PaymentService`, no `IPaymentService`).
- Variables Booleanas: Deben empezar con un verbo (ej. `isActive`, `hasPermission`).
- Los DTO deben ser siempre records y deben tener el sufijo 'Dto' (ej. `UserDto`, `PaymentRequestDto`).
- Las clases de dominio no deben tener sufijos (ej. `User`, `Payment`).
- Los métodos deben ser verbos en minúscula (ej. `calculateTotal()`, `processPayment()`).
- Los nombres de los paquetes deben ser en minúscula y reflejar la estructura del proyecto.