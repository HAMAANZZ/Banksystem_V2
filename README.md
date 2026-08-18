<div align="center">

# 🏦 Banksystem V2

### Fullstack Banking Application

Java • Spring Boot • JavaFX • PostgreSQL

</div>

---

## 📌 Über das Projekt

Banksystem V2 ist eine Java Fullstack Anwendung mit getrenntem Frontend und Backend.

Das System unterstützt:

- Login
- Benutzerverwaltung
- Benutzerrollen
- Bankkonten
- Einzahlungen
- Auszahlungen
- Überweisungen
- Transaktionshistorie

---

# 🛠 Technologien

### Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL

### Frontend

- JavaFX
- FXML
- CSS
- Java HTTP Client
- Jackson

### Build

- Maven

---

# 🏗 Systemarchitektur

```mermaid
flowchart TD

    A[JavaFX Frontend]

    B[REST API]

    C[Spring Security]

    D[Controller]

    E[Service]

    F[Repository]

    G[(PostgreSQL)]

    A -->|HTTP / JSON| B

    B --> C

    C --> D

    D --> E

    E --> F

    F -->|JPA / Hibernate| G
```

Das Frontend kommuniziert ausschließlich über HTTP Requests mit dem Spring Boot Backend.

---

# 📦 Backend Architektur

```mermaid
flowchart LR

    Controller --> Service

    Service --> Repository

    Repository --> Database[(PostgreSQL)]
```

### Controller

Empfängt HTTP Requests und gibt Antworten an das Frontend zurück.

### Service

Enthält die Geschäftslogik des Banksystems.

### Repository

Kommuniziert über Spring Data JPA mit PostgreSQL.

---

# 🧩 Datenmodell

```mermaid
classDiagram

    class User {
        Long id
        String username
        String email
        String password
        String firstName
        String lastName
        String geburtstag
        Role role
        UserStatus status
        LocalDateTime createdAt
    }

    class Account {
        Long id
        String accountNumber
        BigDecimal balance
    }

    class BankTransaction {
        Long id
        TransactionType type
        BigDecimal amount
        BigDecimal balanceAfter
        LocalDateTime createdAt
    }

    User "1" --> "1" Account : besitzt

    Account "1" --> "*" BankTransaction : enthält
```

Ein Benutzer besitzt genau ein Konto.

Ein Konto kann mehrere Transaktionen besitzen.

---

# 🔐 Login Ablauf

```mermaid
sequenceDiagram

    actor User

    participant F as JavaFX LoginController

    participant C as AuthController

    participant S as AuthService

    participant R as UserRepository

    participant DB as PostgreSQL


    User->>F: Benutzername + Passwort

    F->>C: POST /api/auth/login

    C->>S: login()

    S->>R: findByUsername()

    R->>DB: SELECT User

    DB-->>R: User

    R-->>S: User


    S->>S: Passwort prüfen

    S->>S: Status prüfen

    S->>S: Rolle prüfen


    S-->>C: LoginResponse

    C-->>F: JSON Response


    alt Rolle USER

        F->>F: Startseite öffnen

    else Rolle ADMIN

        F->>F: Admin Dashboard öffnen

    end
```

---

# 👤 Benutzer erstellen

```mermaid
sequenceDiagram

    actor Admin

    participant F as JavaFX

    participant C as UserController

    participant S as UserService

    participant UR as UserRepository

    participant AR as AccountRepository

    participant DB as PostgreSQL


    Admin->>F: Benutzer erstellen

    F->>C: POST /api/users

    C->>S: createUser()


    S->>UR: Benutzer prüfen

    UR->>DB: SELECT

    DB-->>UR: Ergebnis


    S->>S: Passwort hashen

    S->>UR: save(User)

    UR->>DB: INSERT User


    S->>S: Kontonummer erstellen

    S->>AR: save(Account)

    AR->>DB: INSERT Account


    S-->>C: UserResponse

    C-->>F: Benutzer erstellt
```

Beim Erstellen eines Benutzers wird automatisch ein Bankkonto erzeugt.

---

# 💰 Einzahlung

```mermaid
sequenceDiagram

    actor User

    participant F as JavaFX

    participant C as AccountController

    participant S as AccountService

    participant AR as AccountRepository

    participant TR as TransactionRepository

    participant DB as PostgreSQL


    User->>F: Einzahlen

    F->>C: POST /deposit

    C->>S: deposit()


    S->>AR: findByUserId()

    AR->>DB: SELECT Account

    DB-->>AR: Account


    S->>S: Betrag prüfen

    S->>S: Kontostand aktualisieren


    S->>AR: save(Account)

    AR->>DB: UPDATE Account


    S->>TR: save(DEPOSIT)

    TR->>DB: INSERT Transaction


    S-->>C: AccountResponse

    C-->>F: Neuer Kontostand


    F->>F: Anzeige aktualisieren

    F->>F: Transaktionen neu laden
```

---


# 🔁 Überweisung

```mermaid
sequenceDiagram

    actor User

    participant F as JavaFX

    participant C as AccountController

    participant S as AccountService

    participant AR as AccountRepository

    participant TR as TransactionRepository

    participant DB as PostgreSQL


    User->>F: Empfängerkonto + Betrag

    F->>C: POST /transfer

    C->>S: transfer()


    S->>AR: Senderkonto suchen

    AR->>DB: SELECT Sender


    S->>AR: Empfängerkonto suchen

    AR->>DB: SELECT Empfänger


    S->>S: Konten prüfen

    S->>S: Guthaben prüfen


    S->>S: Sender Kontostand ändern

    S->>S: Empfänger Kontostand ändern


    S->>AR: Sender speichern

    AR->>DB: UPDATE Sender


    S->>AR: Empfänger speichern

    AR->>DB: UPDATE Empfänger


    S->>TR: TRANSFER_OUT speichern

    TR->>DB: INSERT Transaction


    S->>TR: TRANSFER_IN speichern

    TR->>DB: INSERT Transaction


    S-->>C: AccountResponse

    C-->>F: Überweisung erfolgreich
```

Die gesamte Überweisung läuft innerhalb einer Datenbanktransaktion.

Dadurch werden alle Änderungen gemeinsam gespeichert oder bei einem Fehler gemeinsam zurückgesetzt.

---

# 📜 Transaktionssystem


Unterstützte Typen:

| Typ | Bedeutung |
|---|---|
| `DEPOSIT` | Einzahlung |
| `WITHDRAWAL` | Auszahlung |
| `TRANSFER_OUT` | Überweisung gesendet |
| `TRANSFER_IN` | Überweisung erhalten |

---

# 🔗 Beziehungen

```mermaid
flowchart LR

    U[User]

    A[Account]

    T1[Transaction]

    T2[Transaction]

    T3[Transaction]


    U -->|1 : 1| A

    A -->|1 : n| T1

    A -->|1 : n| T2

    A -->|1 : n| T3
```

---

# 🌐 REST API

| Methode | Endpoint | Funktion |
|---|---|---|
| POST | `/api/auth/login` | Login |
| POST | `/api/users` | Benutzer erstellen |
| GET | `/api/users` | Benutzer laden |
| GET | `/api/accounts/user/{userId}` | Konto laden |
| POST | `/api/accounts/user/{userId}/deposit` | Einzahlung |
| POST | `/api/accounts/user/{userId}/withdraw` | Auszahlung |
| POST | `/api/accounts/user/{userId}/transfer` | Überweisung |
| GET | `/api/accounts/user/{userId}/transactions` | Transaktionen laden |

---



# ▶️ Starten

Backend:

```powershell
$env:DB_PASSWORD="DEIN_POSTGRES_PASSWORT"

mvn -pl backend spring-boot:run
```

Frontend in einem zweiten Terminal:

```powershell
mvn -pl frontend javafx:run
```

---

# 🚧 Status

Aktuell umgesetzt:

- ✅ Login
- ✅ Admin Dashboard
- ✅ Benutzer erstellen
- ✅ Bankkonto
- ✅ Einzahlung
- ✅ Auszahlung
- ✅ Überweisung
- ✅ Transaktionshistorie
- ✅ PostgreSQL
- ✅ JavaFX UI
- ✅ CSS Design

---

<div align="center">

**Banksystem V2**

Java Fullstack Lernprojekt

</div>
