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
- Assicurarsi che MySQL Server sia in esecuzione.
- Aprire MySQL Workbench o un client simile.
- Eseguire lo script SQL fornito `CareerZen_schema.sql` per creare automaticamente il database (che si chiamerà "CareerZen"), le tabelle e inserire i dati di prova (incluso l'Amministratore).
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
Grazie al dump importato avrete già dei dati pronti:

ACCESSO AMMINISTRATORE:
- Email: admin@careerzen.it (oppure Nome Utente: admin_elena)
- Password: admin123
- Spuntate sempre la casella "Accedi come Amministratore" prima di cliccare su Login.

ACCESSO CANDIDATO:
- Potete cliccare su "Registrati" per creare un nuovo account candidato di test.
- Oppure, se avete popolato il DB dal file SQL, potete creare un nuovo account candidato a vostro piacimento.
