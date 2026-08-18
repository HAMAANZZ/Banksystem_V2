<div align="center">

# 🏦 Banksystem V2

### Fullstack Banking Application mit Java, Spring Boot, JavaFX und PostgreSQL

Ein Lernprojekt zur Entwicklung eines vollständigen Banksystems mit Frontend, Backend, Datenbank und REST API.

</div>

---

## 📌 Über das Projekt

**Banksystem V2** ist eine Fullstack Banking Anwendung, die vollständig mit Java entwickelt wird.

Das Projekt besteht aus einem **JavaFX Frontend** und einem **Spring Boot Backend**.

Die Daten werden dauerhaft in einer **PostgreSQL Datenbank** gespeichert.

Das Ziel des Projekts ist es, typische Funktionen eines Banksystems umzusetzen und gleichzeitig moderne Softwarearchitektur praktisch zu lernen.

---

## ✨ Funktionen

### 👤 Benutzer

- Benutzer Login
- Benutzername und Passwort
- Benutzerrollen
- Benutzerstatus
- Persönliche Benutzerdaten
- Automatische Kontoerstellung

### 👨‍💼 Administrator

Administratoren besitzen ein eigenes Dashboard.

Der Admin kann:

- Alle Benutzer anzeigen
- Neue Benutzer erstellen
- Benutzerinformationen sehen
- Benutzerrollen anzeigen
- Benutzerstatus anzeigen
- Benutzerliste aktualisieren

---

## 💳 Konten

Jeder Benutzer besitzt genau **ein Bankkonto**.

Ein Konto enthält unter anderem:

- Eindeutige Kontonummer
- Aktuellen Kontostand
- Zugehörigen Benutzer

Beispiel:

```text
Benutzer
    ↓
Account
    ↓
Kontonummer
    ↓
Kontostand
```

---

## 💰 Einzahlung

Benutzer können Geld auf ihr Konto einzahlen.

Beispiel:

```text
Kontostand vorher:
500,00 €

Einzahlung:
100,00 €

Kontostand danach:
600,00 €
```

Ablauf:

```text
JavaFX
↓
POST Request
↓
AccountController
↓
AccountService
↓
AccountRepository
↓
PostgreSQL
↓
Kontostand aktualisieren
↓
Transaktion speichern
```

---

## 💸 Auszahlung

Benutzer können Geld von ihrem Konto auszahlen.

Vor einer Auszahlung wird geprüft, ob genügend Guthaben vorhanden ist.

Beispiel:

```text
Kontostand:
500,00 €

Auszahlung:
200,00 €

Neuer Kontostand:
300,00 €
```

Wenn nicht genügend Guthaben vorhanden ist, wird die Auszahlung abgelehnt.

---

## 🔁 Überweisung

Benutzer können Geld an andere Konten überweisen.

Für eine Überweisung werden benötigt:

- Kontonummer des Empfängers
- Überweisungsbetrag

Beispiel:

```text
Sender
1.000,00 €
↓
Überweisung 200,00 €
↓
Empfänger
500,00 €
```

Nach der Überweisung:

```text
Sender:
800,00 €

Empfänger:
700,00 €
```

Eine Überweisung auf das eigene Konto wird verhindert.

Außerdem wird geprüft, ob der Sender genügend Guthaben besitzt.

---

## 📜 Transaktionen

Alle neuen Kontobewegungen werden als Transaktionen gespeichert.

Unterstützte Typen:

| Typ | Bedeutung |
|---|---|
| `DEPOSIT` | Einzahlung |
| `WITHDRAWAL` | Auszahlung |
| `TRANSFER_IN` | Eingehende Überweisung |
| `TRANSFER_OUT` | Ausgehende Überweisung |

Eine Transaktion enthält unter anderem:

- Transaktions ID
- Transaktionstyp
- Betrag
- Kontostand nach der Transaktion
- Datum
- Uhrzeit
- Zugehöriges Konto

Die Transaktionen werden auf der Benutzeroberfläche angezeigt.

Beispiel:

```text
+100,00 €   Einzahlung              18.08.2026 13:20
-50,00 €    Auszahlung              18.08.2026 13:25
-200,00 €   Überweisung gesendet    18.08.2026 13:30
+500,00 €   Überweisung erhalten    18.08.2026 13:35
```

---

# 🛠 Technologien

## Backend

| Technologie | Verwendung |
|---|---|
| Java 21 | Programmiersprache |
| Spring Boot | Backend Framework |
| Spring Security | Sicherheitskonfiguration |
| Spring Data JPA | Datenbankzugriff |
| Hibernate | ORM |
| PostgreSQL | Datenbank |
| Maven | Build und Dependency Management |

---

## Frontend

| Technologie | Verwendung |
|---|---|
| JavaFX | Desktop Benutzeroberfläche |
| FXML | Aufbau der Benutzeroberfläche |
| CSS | Design und Styling |
| Java HTTP Client | Kommunikation mit dem Backend |
| Jackson | Verarbeitung von JSON |

---

# 🏗 Architektur

Das Projekt verwendet eine klare Trennung zwischen Frontend, Backend und Datenbank.

```text
┌─────────────────────────────┐
│       JavaFX Frontend       │
│                             │
│ Login                       │
│ Benutzer Dashboard          │
│ Admin Dashboard             │
└──────────────┬──────────────┘
               │
               │ HTTP / JSON
               ↓
┌─────────────────────────────┐
│      Spring Boot Backend    │
│                             │
│ Controller                  │
│ Service                     │
│ Repository                  │
└──────────────┬──────────────┘
               │
               │ JPA / Hibernate
               ↓
┌─────────────────────────────┐
│         PostgreSQL          │
│                             │
│ users                       │
│ accounts                    │
│ transactions                │
└─────────────────────────────┘
```

---

# 📂 Projektstruktur

```text
Banksystem_V2
│
├── backend
│   │
│   ├── src/main/java/com/banking/backend
│   │   │
│   │   ├── account
│   │   │   ├── controller
│   │   │   ├── dto
│   │   │   ├── model
│   │   │   ├── repository
│   │   │   └── service
│   │   │
│   │   ├── auth
│   │   │   ├── controller
│   │   │   ├── dto
│   │   │   └── service
│   │   │
│   │   ├── transaction
│   │   │   ├── dto
│   │   │   ├── model
│   │   │   ├── repository
│   │   │   └── service
│   │   │
│   │   ├── user
│   │   │   ├── controller
│   │   │   ├── dto
│   │   │   ├── model
│   │   │   ├── repository
│   │   │   └── service
│   │   │
│   │   └── config
│   │
│   └── src/main/resources
│       └── application.yml
│
├── frontend
│   │
│   ├── src/main/java/com/banking/frontend
│   │   ├── account
│   │   ├── admin
│   │   ├── auth
│   │   ├── dashboard
│   │   └── transaction
│   │
│   └── src/main/resources
│       ├── css
│       └── views
│
└── pom.xml
```

---

# 🔄 Backend Architektur

Das Backend folgt dem Prinzip:

```text
Request
↓
Controller
↓
Service
↓
Repository
↓
PostgreSQL
```

### Controller

Der Controller nimmt HTTP Anfragen entgegen.

Beispiel:

```text
POST /api/accounts/user/{userId}/deposit
```

### Service

Der Service enthält die eigentliche Geschäftslogik.

Zum Beispiel:

```text
Kontostand prüfen
↓
Betrag berechnen
↓
Kontostand ändern
↓
Transaktion erzeugen
```

### Repository

Das Repository kommuniziert über Spring Data JPA mit PostgreSQL.

---

# 🗄 Datenbank

Das Projekt verwendet:

```text
PostgreSQL
```

Standarddatenbank:

```text
banksystem
```

Standardport:

```text
5432
```

Wichtige Tabellen:

```text
users
accounts
transactions
```

---

# 🔐 Datenbank Passwort

Das PostgreSQL Passwort wird nicht direkt in den Quellcode geschrieben.

Es wird über eine Umgebungsvariable gesetzt.

PowerShell:

```powershell
$env:DB_PASSWORD="DEIN_POSTGRES_PASSWORT"
```

Die `application.yml` verwendet anschließend:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/banksystem
    username: postgres
    password: ${DB_PASSWORD}
```

---

# ▶️ Projekt starten

## 1. PostgreSQL starten

PostgreSQL muss lokal laufen.

Die Datenbank muss vorhanden sein:

```text
banksystem
```

---

## 2. Backend starten

Im Hauptordner:

```powershell
$env:DB_PASSWORD="DEIN_POSTGRES_PASSWORT"
```

Danach:

```powershell
mvn -pl backend spring-boot:run
```

Das Backend läuft anschließend unter:

```text
http://localhost:8080
```

---

## 3. Frontend starten

Ein zweites Terminal öffnen.

```powershell
mvn -pl frontend javafx:run
```

Danach öffnet sich die JavaFX Anwendung.

---

# 🔌 REST API

Beispiele für vorhandene Endpoints:

### Login

```text
POST /api/auth/login
```

### Benutzer erstellen

```text
POST /api/users
```

### Benutzer anzeigen

```text
GET /api/users
```

### Konto anzeigen

```text
GET /api/accounts/user/{userId}
```

### Einzahlung

```text
POST /api/accounts/user/{userId}/deposit
```

### Auszahlung

```text
POST /api/accounts/user/{userId}/withdraw
```

### Überweisung

```text
POST /api/accounts/user/{userId}/transfer
```

### Transaktionen

```text
GET /api/accounts/user/{userId}/transactions
```

---

# 🖥 Benutzeroberfläche

Das Frontend besteht aktuell aus mehreren Bereichen:

### Login

```text
Login.fxml
```

### Benutzer Dashboard

```text
Startseite.fxml
```

### Admin Dashboard

```text
AdminStartseite.fxml
```

### Benutzer erstellen

```text
CreateUser.fxml
```

Das Design wird mit JavaFX CSS umgesetzt.

---

# 👨‍💼 Rollen

Aktuell existieren zwei Rollen:

```text
ADMIN
USER
```

### USER

Ein normaler Benutzer kann sein Konto verwenden.

Zum Beispiel:

```text
Kontostand anzeigen
Einzahlen
Auszahlen
Überweisen
Transaktionen anzeigen
```

### ADMIN

Ein Administrator besitzt Zugriff auf die Benutzerverwaltung.

---

# 🧠 Was ich mit diesem Projekt lerne

Das Projekt dient insbesondere zum Lernen von:

- Java
- Objektorientierter Programmierung
- Spring Boot
- REST APIs
- PostgreSQL
- SQL
- Spring Data JPA
- Hibernate
- JavaFX
- FXML
- CSS
- HTTP Kommunikation
- JSON
- DTOs
- Service Layer
- Repository Pattern
- Datenbankbeziehungen
- Transaktionen
- Fullstack Entwicklung
- Softwarearchitektur

---

# 🚧 Projektstatus

Das Projekt befindet sich aktuell in Entwicklung.

Bereits umgesetzt:

- ✅ Login
- ✅ Benutzerrollen
- ✅ Admin Dashboard
- ✅ Benutzer erstellen
- ✅ Automatische Kontoerstellung
- ✅ Kontostand
- ✅ Einzahlung
- ✅ Auszahlung
- ✅ Überweisung
- ✅ Transaktionshistorie
- ✅ PostgreSQL Integration
- ✅ JavaFX Oberfläche
- ✅ CSS Design

Geplante Verbesserungen:

- ⏳ Authentifizierung und Autorisierung weiter absichern
- ⏳ Benutzerverwaltung erweitern
- ⏳ Detailliertere Transaktionsinformationen
- ⏳ Überweisungsdetails
- ⏳ Bessere Fehlerbehandlung
- ⏳ Weitere UI Verbesserungen
- ⏳ Tests

---

# 🎯 Ziel

Das langfristige Ziel ist eine übersichtliche Banking Anwendung mit:

```text
Benutzerverwaltung
+
Kontoverwaltung
+
Einzahlungen
+
Auszahlungen
+
Überweisungen
+
Transaktionshistorie
+
Sicherheit
```

---

<div align="center">

### 🏦 Banksystem V2

Entwickelt als Java Fullstack Lernprojekt.

</div>