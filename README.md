# raspy-app
Die App is dazu da, um den Livestream des Raspberry Pi's zu empfangen, Bilder zu machen, 
Einstellungen zu ändern und die Bilder anzuzeigen.

## Bestandteile

### backend
Kümmert sich um die Kommunikation mittels RestApi mit dem Server. Hier werden auch die Urls festgelegt.

### ui
Beinhaltet die Oberfläche der App. Hier werden die einzelnen Seiten definiert.
Dazu gehört: - ImageLogsUi welche sich um das Anzeigen der Bilder kümmert.
             - LivestreamUi welche sich um das Anzeigen des Livestreams kümmert.
             - SettingsUi welche sich um das Anzeigen der Einstellungen kümmert.
             - loginUi welche sich um das Anzeigen des Logins und Registrieren kümmert.
             - infoUi welche sich um das Anzeigen der Informationen kümmert.
             - theme in dem die Themes der App definiert sind.

### notifcation
Kümmert sich um die Benachrichtigungen der App. Hier werden die Benachrichtigungen von Firebase
erhalten und angezeigt.

### policiy
Beinhaltet die Policy der App.

## Anpassungen
Falls sich die Urls des Servers ändern, müssen diese in der Datei `backend/dataclasses/globalValues` angepasst werden.