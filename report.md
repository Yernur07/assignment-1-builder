# Assignment 1 - Builder Pattern Report

**Course:** Software Design Patterns  
**Domain:** Smart Home System  
**Individual Constraint:** Remote access requires security alarm enabled.

---

## Part A - Design Problem
Creating a `SmartHomeSystem` via long telescopic constructors led to severe design issues:
1. **Readability Deficit:** Calling `new SmartHomeSystem("SYS-1", "Alice", "PROD", loc, 10, true, true, false, 22.0, "EMAIL")` makes it impossible to understand what individual booleans and numbers represent without checking class definition.
2. **Error-Prone Argument Order:** Passing multiple adjacent `boolean` or `String` values creates silent bugs if arguments are swapped.
3. **Rigid Maintenance:** Adding optional parameters requires adding new constructor overloads or breaking existing code.

---

## Part B & C - Builder & Validation
The Builder pattern resolves this by providing a domain-oriented Fluent API (`.enableSecurityAlarm()`, `.allowRemoteAccess()`).

### Validation Rules
- **Single-field:** `systemId` non-empty, `maxDeviceCapacity` (1–500), `temperatureThreshold` (-10°C to 50°C).
- **Cross-field 1:** `PRODUCTION` environment requires both security alarm and automatic backup.
- **Cross-field 2 (Individual Constraint):** Remote access cannot be enabled without enabling the security alarm.

---

## Part D - Presets
- `BASIC`: Minimal config for development.
- `SAFE`: Production-ready with backup and alarm (prints 🍌).
- `PERFORMANCE`: High-capacity production system with remote access.

---

## Part E - Clean Code Principles Applied
1. **One Level of Abstraction:** Separated `validate()` logic from `build()` workflow.
2. **Flag Arguments Avoidance:** Replaced `.setSecurity(true, false)` with explicit `.enableSecurityAlarm()` and `.allowRemoteAccess()`.
3. **Descriptive Naming:** Used domain terms (`sendNotificationsVia`) instead of standard setters (`setNotificationChannel`).

---

## Part F - Design Decision
- **Decision:** Perform validation inside `Builder.build()` before invoking the private `SmartHomeSystem` constructor.
- **Alternative:** Validate inside `SmartHomeSystem` constructor or setters.
- **Reasoning:** Validating in `Builder.build()` guarantees that an invalid `SmartHomeSystem` object is never instantiated in memory.

---

## Part G - UML Traceability
| Pattern Role | Class Name | Responsibility |
|---|---|---|
| Product | `SmartHomeSystem` | Complex target object containing system setup |
| Builder | `SmartHomeSystem.Builder` | Constructs `SmartHomeSystem` step-by-step with validation |
| Director | `SmartHomeDirector` | Defines standard preset configurations |
| Value Object | `RoomLocation` | Immutable location representation |