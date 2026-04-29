# Système de Gestion d'Événements - Backend API

## Description du Modèle Métier

Ce projet est une API REST pour la gestion d'événements culturels. Le système permet de gérer des utilisateurs (clients, organisateurs, administrateurs), des événements, des artistes et des tickets.

### Entités Principales

#### User (classe parente)
- Représente un utilisateur du système
- Attributs: id, name, email, password, address, phone, createdAt
- Héritage: JOINED strategy
- Sous-types: Client, Organizer, Admin

#### Client (hérite de User)
- Représente un client/acheteur de tickets
- Attribut spécifique: city
- Relation: OneToMany avec Ticket

#### Organizer (hérite de User)
- Représente un organisateur d'événements
- Attribut spécifique: companyName
- Relation: ManyToMany bidirectionnelle avec Event

#### Admin (hérite de User)
- Représente un administrateur système
- Aucun attribut spécifique supplémentaire

#### Event
- Représente un événement
- Attributs: id, place, date, description, price, imageUrl, address, city, createdAt
- Relations:
  - ManyToMany avec Organizer (bidirectionnelle avec mappedBy)
  - OneToMany avec Ticket
  - ManyToMany avec Artist

#### Ticket
- Représente un ticket d'entrée pour un événement
- Attributs: id, number, status (AVAILABLE, SOLD, RESERVED), purchaseDate, createdAt
- Relations:
  - ManyToOne avec Client
  - ManyToOne avec Event

#### Artist
- Représente un artiste participant à un événement
- Attributs: id, name, genre, bio, createdAt
- Relation: ManyToMany avec Event

## Diagramme de Classe

```
                          ┌─────────────────┐
                          │      User       │
                          ├─────────────────┤
                          │ id              │
                          │ name            │
                          │ email           │
                          │ password        │
                          │ address         │
                          │ phone           │
                          │ createdAt       │
                          └────────┬────────┘
                                   │ (Inheritance JOINED)
                   ┌───────────────┼───────────────┐
                   │               │               │
            ┌──────▼──────┐ ┌─────▼──────┐ ┌─────▼─────┐
            │   Client    │ │ Organizer  │ │   Admin   │
            ├─────────────┤ ├────────────┤ ├───────────┤
            │ city        │ │companyName │ │           │
            └──────┬──────┘ └─────┬──────┘ └───────────┘
                   │              │
                   │              │ ManyToMany
                   │              │
                   │         ┌────▼──────────────┐
                   │         │      Event        │
                   │         ├───────────────────┤
                   │         │ id                │
                   │         │ place             │
                   │         │ date              │
                   │         │ description       │
                   │         │ price             │
                   │         │ imageUrl          │
                   │         │ address           │
                   │         │ city              │
                   │         │ createdAt         │
                   │         └────┬──────┬───────┘
                   │              │      │
                   │ OneToMany    │      │ ManyToMany
                   │              │      │
            ┌──────▼──────┐      │   ┌──▼────────────┐
            │   Ticket    │◄─────┘   │    Artist     │
            ├─────────────┤          ├───────────────┤
            │ id          │          │ id            │
            │ number      │          │ name          │
            │ status      │          │ genre         │
            │ purchaseDate│          │ bio           │
            │ createdAt   │          │ createdAt     │
            └─────────────┘          └───────────────┘
```

## Architecture Technique

### Technologies
- Java 11
- JPA / Hibernate 6.2.7
- JAX-RS (RestEasy 6.2.4)
- HSQLDB / MySQL
- Swagger OpenAPI 2.2.15
- Maven

### Patterns Utilisés
- DAO (Data Access Object) pattern
- DTO (Data Transfer Object) pattern
- Generic DAO avec AbstractJpaDao
- Repository pattern

### Requêtes Implémentées
- **JPQL**: Requêtes dans EventDao (findByOrganizerAsDTO, etc.)
- **Named Query**: `findByArtisteName` dans Event.java
- **Criteria Query**: `findByCityAsDTO` dans EventDao.java

## API Endpoints

### User Resource (`/user`)

| Méthode | Endpoint | Description | Body |
|---------|----------|-------------|------|
| GET | `/user/{userId}` | Récupérer un utilisateur par ID | - |
| GET | `/user/` | Liste de tous les utilisateurs | - |
| POST | `/user/login` | Connexion utilisateur | LoginDto |
| POST | `/user/create` | Créer un utilisateur | UserDto |
| DELETE | `/user/{userId}` | Supprimer un utilisateur | - |

**LoginDto:**
```json
{
  "email": "string",
  "password": "string"
}
```

**UserDto:**
```json
{
  "name": "string",
  "email": "string",
  "password": "string",
  "address": "string",
  "phone": "string",
  "role": "user|organizer",
  "city": "string",
  "companyName": "string"
}
```

### Event Resource (`/event`)

| Méthode | Endpoint | Description | Body |
|---------|----------|-------------|------|
| GET | `/event/{eventId}` | Récupérer un événement par ID | - |
| GET | `/event/` | Liste de tous les événements | - |
| GET | `/event/organizer/{organizerId}` | Événements par organisateur | - |
| GET | `/event/city/{city}` | Événements par ville | - |
| GET | `/event/search?artist={name}` | Rechercher par artiste | - |
| GET | `/event/{eventId}/available-tickets` | Nombre de tickets disponibles | - |
| POST | `/event/create` | Créer un événement | EventDTO |
| DELETE | `/event/{eventId}` | Supprimer un événement | - |

**EventDTO:**
```json
{
  "place": 1000,
  "description": "string",
  "address": "string",
  "imageUrl": "string",
  "date": "2024-12-31T20:00:00.000Z",
  "city": "string",
  "price": 50.0,
  "organizerId": 1
}
```

### Artist Resource (`/artist`)

| Méthode | Endpoint | Description | Body |
|---------|----------|-------------|------|
| GET | `/artist/{artistId}` | Récupérer un artiste par ID | - |
| GET | `/artist/` | Liste de tous les artistes | - |
| POST | `/artist/create` | Créer un artiste | Artist |
| DELETE | `/artist/{artistId}` | Supprimer un artiste | - |

### Ticket Resource (`/ticket`)

| Méthode | Endpoint | Description | Body |
|---------|----------|-------------|------|
| GET | `/ticket/{ticketId}` | Récupérer un ticket par ID | - |
| GET | `/ticket/` | Liste de tous les tickets | - |
| GET | `/ticket/client/{clientId}` | Tickets d'un client | - |
| GET | `/ticket/event/{eventId}` | Tickets d'un événement | - |
| POST | `/ticket/purchase` | **Acheter un ticket** | TicketPurchaseDto |
| DELETE | `/ticket/{ticketId}` | Supprimer un ticket | - |

**TicketPurchaseDto:**
```json
{
  "eventId": 1,
  "clientId": 2
}
```

**Réponse (TicketDto):**
```json
{
  "id": 15,
  "number": "TICKET-abc123",
  "status": "SOLD",
  "clientId": 2,
  "clientName": "Jean Dupont",
  "eventId": 1,
  "eventDescription": "Concert de Jazz",
  "purchaseDate": "29-04-2026 20:30:45",
  "createdAt": "15-03-2026 10:00:00"
}
```

## Configuration et Démarrage

### Prérequis
- Java 11+
- Maven 3.6+
- HSQLDB ou MySQL

### Lancer la base de données HSQLDB
```bash
# Windows
run-hsqldb-server.bat

# Linux/Mac
./run-hsqldb-server.sh
```

### Lancer l'application
```bash
mvn clean install
mvn jetty:run
```

L'API sera accessible sur `http://localhost:8080`

### Documentation Swagger
Une fois l'application lancée, accédez à la documentation OpenAPI via:
`http://localhost:8080/swagger` (selon configuration)

## Points Techniques Notables

### Méthodes Métier dans les DAOs
- `EventDao.countAvailableTickets()`: Compte les tickets disponibles pour un événement
- `EventDao.findByOrganizerAsDTO()`: Recherche avec conversion en DTO
- `EventDao.findByCityAsDTO()`: Recherche par ville avec Criteria Query
- `EventDao.findByArtisteNameAsDTO()`: Recherche par nom d'artiste (Named Query)
- `EventDao.saveWithOrganizer()`: Sauvegarde avec gestion de relation bidirectionnelle
- `TicketDao.purchaseTicket()`: Logique métier complète pour l'achat de ticket (vérification disponibilité, création, association client/événement, changement de statut)
- `TicketDao.findByClient()`: Récupère tous les tickets d'un client avec conversion en DTO
- `TicketDao.findByEvent()`: Récupère tous les tickets d'un événement avec conversion en DTO

### Relations Bidirectionnelles
- Event ↔ Organizer: ManyToMany avec `mappedBy = "events"` dans Event
- Event ↔ Ticket: OneToMany avec `mappedBy = "event"` dans Event

### Héritage
- User avec stratégie JOINED
- Sous-classes: Client, Organizer, Admin

## Sécurité

**Note importante**: Dans la version actuelle, les mots de passe sont stockés en clair. En production, il est impératif de:
- Hasher les mots de passe (BCrypt, Argon2, etc.)
- Implémenter un système d'authentification JWT ou OAuth2
- Ajouter une validation des entrées
- Implémenter HTTPS

## Auteurs

Projet TP JPA 2026 - UniR
