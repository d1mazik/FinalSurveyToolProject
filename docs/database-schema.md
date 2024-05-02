# Projektets Datamodell och Relationer

## Entiteter

### User
Beskriver en person som använder applikationen. Användare kan ha olika roller och behörigheter.

### Role
Definierar behörigheter för `User`. Exempelvis kan "Admin" skapa och hantera enkäter medan "User" bara kan svara på dem.

### Survey
Representerar en enkät och innehåller en uppsättning `Question`. Varje `Survey` är skapad av en `User`.

### Question
En fråga som finns i en `Survey`. Kan vara av olika typer, som textfrågor, flervalsfrågor eller skalafrågor.

### Option
(Om tillämpligt) Ett svarsalternativ för en `Question` av typen flervalsfråga.

### SurveyResponseSession
En session där en `User` besvarar en `Survey`. Inkluderar datum och tid för start och slutförande.

### Answer
Ett svar som en `User` ger på en `Question` i en `Survey`, kopplat till en specifik `Question` och en `SurveyResponseSession`.

## Relationer

### Flöde för Enkätbesvarande

1. **Starta Enkäten:** En `User` loggar in och startar en `SurveyResponseSession` för att besvara en `Survey`.

2. **Svara på Frågor:** `User` skapar ett `Answer` för varje `Question` i `Survey`. Varje `Answer` är kopplat till en specifik `Question` och den aktuella `SurveyResponseSession`.

3. **Slutföra Enkäten:** När alla `Question` i `Survey` har besvarats, slutför `User` sin `SurveyResponseSession`. Systemet lagrar alla `Answer` med referenser till deras `Question` och `SurveyResponseSession`.

## ER-Diagram

![ER-Diagram](images/er-diagram-surveytool.png)

*ER-diagrammet visar de visuella relationerna mellan de olika entiteterna.*uella relationerna mellan de olika entiteterna.