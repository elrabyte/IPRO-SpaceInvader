# IPro-Bericht: Shoot'em Up Game

<img width="80%" alt="Demo" src="demo.gif" />

---

## 1. Projektbeschreibung

### 1.1 Ausgangslage / Ziel
Das Ziel dieses IPro war es, die Programmiersprache **Java** besser kennenzulernen. Als
Projekt habe ich mich für die Entwicklung eines eigenen **Shoot'em Up-Spiels** nach
Vorbild des Klassikers *Galaga* entschieden.

### 1.2 Umsetzung
Das Spiel wurde bewusst einfach gehalten, um den Fokus auf das Erlernen von Java zu
legen:
- Das Spielerschiff sowie die gegnerischen Schiffe werden als **Dreiecke** dargestellt.
- Die Asteroiden/Hindernisse werden als **Kreise** dargestellt.
- Steuerung über die Tasten **W/A/S/D** (Bewegung) und **Leertaste** (Schiessen). Dazu
  wird bei jedem Tastendruck-Event gemerkt, welche Tasten aktuell gedrückt sind (in
  einem Set). Im Main Loop wird dann bei jedem Tick geprüft, welche Tasten sich
  gerade darin befinden, und die entsprechende Logik (Bewegen, Schiessen)
  ausgeführt.
- Für die Kollisionserkennung (Hitboxes) bekommt jede Form ein umschliessendes
  **Rechteck**, da dieses bereits eine Methode mitbringt, die prüft, ob sich zwei
  Hitboxes überschneiden (`intersects`). So musste ich keine eigene
  Kollisionsberechnung für Dreiecke/Kreise implementieren.
- Punkte-/Scoring-System: Jeder abgeschossene Asteroid und jedes gegnerische Schiff
  gibt Punkte, zusätzlich fliesst auch die vergangene Spielzeit in die Punktzahl mit
  ein.
- Speicherung der Scores/Leaderboard lokal als **JSON-Datei**.

### 1.3 Vorgehen
Ich habe mich für dieses Projekt für **Java Swing** (`java.awt` / `javax.swing`)
entschieden. Ausserdem wollte ich bewusst mit einfachen geometrischen Formen
(Dreiecke, Kreise) arbeiten, da sich diese direkt mit den Bordmitteln von Swing/AWT
(`Graphics.fillPolygon`, `fillOval`, `fillRect`) zeichnen lassen, ohne eine zusätzliche
Grafik-Library einbinden zu müssen.

Als Main Loop des Spiels habe ich mit einem `javax.swing.Timer` gearbeitet, der in
regelmässigen Abständen (~60 mal pro Sekunde) den Spielzustand aktualisiert und neu
zeichnet.

Für das Laden/Speichern der Scores als JSON wollte ich das nicht selbst von Grund auf
programmieren und habe stattdessen eine Library gesucht. Dabei bin ich auf **Googles
Gson** gestossen. In diesem Zusammenhang habe ich auch gelernt, dass man in Java
**Maven** verwendet, um solche Libraries/Abhängigkeiten herunterzuladen und ins
Projekt einzubinden.

Wo immer sinnvoll möglich, habe ich mit **Interfaces** gearbeitet, damit sich Logik gut
teilen und wiederverwenden lässt (z. B. für Entitäten, Projektile, Gegner).

### 1.4 Verwendete Werkzeuge
- **Java** mit **Swing/AWT** als UI-/Grafik-Framework.
- **Maven** als Build-Tool zur Verwaltung von Abhängigkeiten.
- **Gson** (Google) zum Laden/Speichern der Highscores als JSON.
- **GitHub Copilot**: zur Unterstützung bei Code Reviews und für Verbesserungsvorschläge
  am bestehenden Code.

---

## 2. Reflexion

### 2.1 Was habe ich gelernt?
Im Rahmen dieses Projekts habe ich die Programmiersprache **Java** gelernt, von den
Grundlagen (Syntax, Klassen, Objektorientierung) bis hin zur praktischen Anwendung in
einem eigenen, funktionierenden Spiel. Konkret habe ich unter anderem gelernt bzw.
vertieft:
- Arbeiten mit **Swing/AWT** für Grafik, Zeichnen von Formen und Event-Handling.
- Aufbau eines eigenen **Main Loops** mit `javax.swing.Timer`/Events zur laufenden
  Aktualisierung des Spielzustands.
- Umgang mit **Hitboxes/Kollisionserkennung** über Rechtecke und deren
  `intersects`-Methode.
- Einsatz von **Interfaces**, um Logik zwischen verschiedenen Klassen (z. B. Entitäten,
  Projektile) zu teilen und wiederzuverwenden.
- Verwendung von **Maven** zur Verwaltung externer Abhängigkeiten sowie Einbindung
  der Library **Gson** zum Laden/Speichern von Daten (Highscores) als JSON.

### 2.2 Bezug zu OOP, Polymorphismus und Interfaces
Die Grundlagen für diesen Teil des Projekts stammen aus dem Modul **Programmieren 1**.
Ein zentraler Lerneffekt dieses Projekts war, dieses Wissen praktisch in einem eigenen
Projekt anzuwenden:
- Alle Spielobjekte (Spieler, Gegner, Projektile) implementieren das gemeinsame
  Interface `IEntity` (u. a. `render`, `takeDamage`, `isAlive`, `getHitBox`). Dadurch kann
  z. B. die Kollisionslogik im `GamePanel` generisch mit allen Objekten arbeiten, ohne
  deren konkreten Typ zu kennen.
- **Polymorphismus** zeigt sich z. B. bei den Gegnern: `EnemyShip` und `Asteroid`
  implementieren beide `IEnemy`, verhalten sich aber unterschiedlich (`updateState`,
  `render`). Nur `EnemyShip` implementiert zusätzlich `IShooting`, weshalb im Code
  per `instanceof`-Prüfung (`enemy instanceof IShooting`) entschieden wird, ob ein
  Gegner schiessen kann. Die restliche Logik bleibt für beide Gegnertypen identisch.
- **Interfaces für geteilte Logik**: `IProjectile` wird sowohl von
  `PlayerSingleShotProjectile` als auch von `EnemySingleShotProjectile` implementiert,
  wodurch die Kollisions- und Update-Logik im `GamePanel` nur einmal geschrieben
  werden musste, statt für Spieler- und Gegnerprojektile separat.

### 2.3 Einsatz von GitHub Copilot
Ich habe **GitHub Copilot** hauptsächlich für Implementierungsvorschläge eingesetzt,
wenn ich nicht genau wusste, wie ich ein bestimmtes Problem in Java lösen sollte (z. B.
bei der Struktur der Interfaces oder beim Umgang mit den Swing-Klassen). Die
Vorschläge habe ich jeweils nicht direkt übernommen, sondern in meinem eigenen Stil
umgesetzt und angepasst. Copilot diente somit eher als Ausgangspunkt/Denkanstoss,
den konkreten Code habe ich selbst geschrieben und verstehe ihn entsprechend.

### 2.4 Fazit
Insgesamt bin ich jetzt etwas bekannter mit Java. Die grösste neue Erkenntnis aus diesem
Projekt war jedoch, **wie** und **dass** man in Java überhaupt externe Bibliotheken mit
**Maven** einbinden kann. Dieses Wissen nehme ich als wichtigsten Lerneffekt aus dem
IPro mit.
