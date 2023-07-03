# raspy-app

Die App ist dazu da, um den Livestream des Raspberry Pi's zu empfangen, Bilder zu machen, Einstellungen zu ändern und die Bilder anzuzeigen.

## Bestandteile

### Backend
Kümmert sich um die Kommunikation mittels RestApi mit dem Server. Hier werden auch die Urls festgelegt.

### UI
Beinhaltet die Oberfläche der App. Hier werden die einzelnen Seiten definiert.
Dazu gehört:
- ImageLogsUi: kümmert sich um das Anzeigen der Bilder.
- LivestreamUi: kümmert sich um das Anzeigen des Livestreams und das aufnehmen der Bilder.
- SettingsUi: kümmert sich um das Anzeigen der Einstellungen und das abspecihern dieser auf den Server.
- loginUi: kümmert sich um das einloggen und registrieren.
- infoUi: kümmert sich um das Anzeigen von Informationen über die App.
- theme: definiert die Themes der App.


### Notifcation
Kümmert sich um die Benachrichtigungen der App. Hier werden die Benachrichtigungen von Firebase erhalten und angezeigt.

### Policiy
Beinhaltet die Policy der App.

## Anpassungen
Falls sich die Url des Servers oder des Livestreams ändern, müssen diese in der Datei `backend/dataclasses/globalValues` angepasst werden.
