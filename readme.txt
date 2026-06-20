================================================
           PROGETTO: CAREER ZEN
================================================

1. PREREQUISITI
------------------------------------------------
- Java Development Kit (JDK) 17 o superiore
- MySQL Server (versione 8+ consigliata)
- Connessione a Internet (per scaricare le dipendenze Gradle la prima volta)

2. CONFIGURAZIONE DATABASE
------------------------------------------------
- Assicurarsi che MySQL Server sia in esecuzione sulla porta 3306.
- Creare lo schema database denominato "CareerZen" ed eseguire lo script SQL fornito (se presente) per generare tabelle e dati di prova.
- Credenziali di default del Database (cablate nel file `app/src/main/java/db_lab/data/DAOUtils.java`):
  - Utente: root
  - Password: vinderei2005
  (Se la vostra installazione MySQL ha credenziali diverse, siete pregati di modificarle in DAOUtils.java prima di avviare l'applicativo).

3. ESECUZIONE DELL'APPLICATIVO
------------------------------------------------
Il progetto utilizza Gradle come sistema di build. Non e' necessario avere Gradle preinstallato, e' sufficiente usare il wrapper incluso.

- Da Terminale / Prompt dei Comandi (posizionandosi nella cartella radice del progetto, dove si trova questo readme.txt):
  - Mac/Linux: ./gradlew run
  - Windows: gradlew.bat run

In alternativa, e' possibile importare l'intera cartella in un IDE (IntelliJ IDEA, Eclipse, VSCode) come "Progetto Gradle" ed eseguire la classe principale `db_lab.App`.

4. CREDENZIALI APPLICATIVO E UTILIZZO
------------------------------------------------
- Se il database non contiene dati, utilizzare il pulsante "Registrati" nella schermata iniziale per creare un nuovo account Candidato.
- Per l'accesso come Amministratore, spuntare la casella "Accedi come Amministratore" e usare le credenziali inserite preventivamente nella tabella `Amministratore` del database.
