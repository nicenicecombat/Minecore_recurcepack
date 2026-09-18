# WhatsApp Blocker

Eine kleine Android-App mit einem Dashboard: Sie zeigt deine heutige
WhatsApp-Nutzungszeit an und hat einen großen roten "AKTIVIEREN"-Button.

- **Aktiviert:** Sobald du WhatsApp öffnest, wirst du sofort wieder auf den
  Startbildschirm geschickt – WhatsApp bleibt praktisch nicht mehr offen.
- **Deaktiviert:** WhatsApp funktioniert ganz normal.

Der Zustand lässt sich jederzeit per Knopfdruck umschalten.

## Wie es technisch funktioniert

- Ein **Accessibility Service** (Bedienungshilfe) erkennt, wenn das
  Vordergrund-Fenster zu `com.whatsapp` oder `com.whatsapp.w4b`
  (WhatsApp Business) wechselt, und ruft dann `GLOBAL_ACTION_HOME` auf, um
  dich sofort zurück zum Homescreen zu schicken – nur wenn die Blockierung
  in der App aktiv ist.
- Die heutige Nutzungszeit wird über `UsageStatsManager` (Android
  "App-Nutzungsdaten") ausgelesen.
- Beides sind offiziell dokumentierte Android-APIs, die z. B. auch
  Fokus-/App-Blocker-Apps aus dem Play Store verwenden. Es wird nichts an
  Dritte gesendet – alles bleibt lokal auf dem Gerät.

## Einrichtung nach der Installation

Nach der Installation zeigt das Dashboard zwei Hinweiskarten, solange
Berechtigungen fehlen:

1. **Bedienungshilfe aktivieren** – öffnet die Systemeinstellungen, wo du
   "WhatsApp Blocker" in der Liste der Bedienungshilfen einschalten musst.
   Ohne das funktioniert der Blockier-Button nicht (er ist ausgegraut).
2. **Nutzungszugriff erlauben** – öffnet die Einstellungen für den
   App-Nutzungszugriff, damit die heutige WhatsApp-Zeit angezeigt werden
   kann (optional, nur für die Anzeige nötig).

## APK auf dein Handy bekommen

Dieses Repo enthält einen GitHub-Actions-Workflow
(`.github/workflows/build-whatsapp-blocker-apk.yml`), der bei jedem Push
automatisch eine installierbare Debug-APK baut:

1. Öffne im Browser (auch auf dem Handy) die **Releases**-Seite des Repos
   und lade dort `app-debug.apk` aus dem Release **"WhatsApp Blocker
   (latest build)"** herunter. Alternativ: Tab **Actions** → letzter
   erfolgreicher Lauf von "Build WhatsApp Blocker APK" → Artifact
   `whatsapp-blocker-debug-apk`.
2. Falls Android beim Öffnen der Datei warnt: Einstellungen → "Installation
   aus unbekannten Quellen" für den verwendeten Browser/Dateimanager
   einmalig erlauben.
3. APK installieren, App öffnen, Bedienungshilfe aktivieren (siehe oben)
   – fertig.

Das ist eine unsignierte Debug-APK für den privaten Gebrauch (kein Play
Store nötig).

## Lokal selbst bauen (optional)

Falls du Android Studio installiert hast, kannst du den Ordner
`whatsapp-blocker-app/` direkt als Projekt öffnen und über
*Build ▸ Build Bundle(s)/APK(s) ▸ Build APK(s)* selbst bauen, oder per
Kommandozeile:

```bash
cd whatsapp-blocker-app
./gradlew assembleDebug
# Ergebnis: app/build/outputs/apk/debug/app-debug.apk
```
