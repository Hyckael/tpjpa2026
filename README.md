# Auteur : Ange Marlène GOHI && Evrard Hyckael KONAN

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