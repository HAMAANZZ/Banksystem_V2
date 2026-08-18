# 🏗 Architektur

Das Projekt besteht aus drei Hauptteilen:

```text
JavaFX Frontend
↓
Spring Boot Backend
↓
PostgreSQL
```

Das Frontend sendet HTTP Anfragen als JSON an das Backend.

Das Backend verarbeitet die Anfrage über:

```text
Controller
↓
Service
↓
Repository
↓
PostgreSQL
```

---

# 🔄 Wie funktioniert eine Anfrage?

Beispiel: Der Benutzer zahlt 100 € ein.

```text
Benutzer klickt auf „Einzahlen“
↓
JavaFX liest den Betrag
↓
POST /api/accounts/user/{userId}/deposit
↓
AccountController
↓
AccountService
↓
AccountRepository
↓
PostgreSQL
↓
Kontostand wird aktualisiert
↓
Transaktion wird gespeichert
↓
Backend sendet neuen Kontostand zurück
↓
JavaFX aktualisiert die Anzeige
```

Beispiel Request:

```json
{
  "amount": 100.00
}
```

---

# 💰 Einzahlung

Die Einzahlung läuft im Backend über den `AccountService`.

Beispiel:

```text
Alter Kontostand:
500,00 €

Einzahlung:
100,00 €

Neuer Kontostand:
600,00 €
```

Intern:

```text
Account suchen
↓
aktuellen Kontostand laden
↓
Betrag addieren
↓
Account speichern
↓
DEPOSIT Transaktion speichern
```

Dadurch wird nicht nur der Kontostand geändert, sondern die Einzahlung erscheint auch in der Transaktionshistorie.

---

# 💸 Auszahlung

Bei einer Auszahlung wird zuerst geprüft, ob genügend Guthaben vorhanden ist.

```text
Kontostand:
500,00 €

Auszahlung:
200,00 €
↓
genug Guthaben?
↓
JA
↓
500 € - 200 €
↓
300 €
```

Wenn der Benutzer mehr auszahlen möchte als vorhanden ist:

```text
Kontostand:
500 €

Auszahlung:
700 €
↓
Nicht genügend Guthaben
↓
Auszahlung wird abgebrochen
```

Eine erfolgreiche Auszahlung wird als:

```text
WITHDRAWAL
```

gespeichert.

---

# 🔁 Überweisung

Bei einer Überweisung sind zwei Konten beteiligt:

```text
Sender
↓
Empfänger
```

Der Benutzer gibt ein:

```text
Empfänger Kontonummer
+
Betrag
```

Danach passiert:

```text
Senderkonto suchen
↓
Empfängerkonto über Kontonummer suchen
↓
prüfen, ob beide Konten verschieden sind
↓
Guthaben des Senders prüfen
↓
Betrag beim Sender abziehen
↓
Betrag beim Empfänger hinzufügen
↓
beide Konten speichern
```

Beispiel:

```text
Sender:
1.000 €

Empfänger:
500 €

Überweisung:
200 €
```

Danach:

```text
Sender:
800 €

Empfänger:
700 €
```

Zusätzlich werden zwei Transaktionen gespeichert:

```text
Sender
↓
TRANSFER_OUT
↓
-200 €

Empfänger
↓
TRANSFER_IN
↓
+200 €
```

Die komplette Überweisung läuft innerhalb einer Datenbank Transaktion.

Das bedeutet:

```text
entweder alles funktioniert
↓
Sender und Empfänger werden aktualisiert

oder

es tritt ein Fehler auf
↓
alles wird zurückgesetzt
```

Dadurch kann nicht passieren, dass beim Sender Geld abgezogen wird, aber der Empfänger kein Geld erhält.

---

# 📜 Transaktionen

Jede Kontobewegung wird in PostgreSQL gespeichert.

Aktuell gibt es:

```text
DEPOSIT       → Einzahlung
WITHDRAWAL    → Auszahlung
TRANSFER_IN   → Überweisung erhalten
TRANSFER_OUT  → Überweisung gesendet
```

Eine Transaktion enthält zum Beispiel:

```text
ID
Typ
Betrag
Kontostand danach
Datum
Uhrzeit
Konto
```

Beispiel:

```text
Überweisung erhalten
+100,00 €
18.08.2026 13:21
Kontostand danach: 1.500,00 €
```

---

# 👤 Benutzer und Konto

Jeder Benutzer besitzt genau ein Konto.

```text
User
↓
1 : 1
↓
Account
```

Der `User` enthält zum Beispiel:

```text
Benutzername
E-Mail
Passwort
Vorname
Nachname
Rolle
Status
```

Der `Account` enthält:

```text
Kontonummer
Kontostand
Benutzer
```

Beim Erstellen eines neuen Benutzers wird automatisch ein Konto mit:

```text
0,00 €
```

angelegt.

---

# 🔐 Login

Beim Login sendet JavaFX Benutzername und Passwort an das Backend.

```text
Login.fxml
↓
LoginController
↓
POST /api/auth/login
↓
AuthController
↓
AuthService
↓
UserRepository
↓
PostgreSQL
```

Das Backend prüft:

```text
Benutzer vorhanden?
↓
Passwort korrekt?
↓
Benutzer ACTIVE?
↓
Rolle prüfen
```

Danach wird entschieden:

```text
USER
↓
Benutzer Dashboard

ADMIN
↓
Admin Dashboard
```
