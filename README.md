# Documentazione Glossario

Enrico Fasoli, Gianluca Rocco, Roberto Dalipaj, Marco Carne, Raffaele Cristodaro, Sara Dendena

## Funzionamento

Il server mantiene la lista di tutte le parole con una stringa per la parola e una per il significato. I client si collegano e il server crea un thread per la comunicazione.
Quando una parola viene inserita o modificata dal client viene aggiornata la lista e tutti i client ricevono l’aggiornamento via socket tcp.

_Record_: array di 2 String che rappresenta un termine e il suo significato.

_Gestione thread safe_: i dati sono in una lista che tramite `Collections.synchronizedList` viene resa thread safe automaticamente da Java.

_Protocollo_: vengono inviate stringhe per comunicare, il record viene inviato nel formato `parola: significato` con `parola` tutto minuscolo.

_Interfaccia_:
- lista di parole: clicco su una parola e vedo il significato e posso modificarlo.
- filtro: un campo di testo che posso usare per filtrare la lista
- Possibilità di aggiungere, modificare o eliminare termini

_Roadmap_ (percorso di sviluppo):
- Server che carica le parole da file
- Server che accetta le connessioni e gestisce i comandi
- Client che invia comandi e riceve risposte
- GUI Client

## Implementazione

Documentazione a livello di classi e funzioni:

### Server

- Server: accetta le connessioni (__Insieme__)
- ClientHandler: un'instanza per ogni client con il suo thread. La classe
contiene la lista dei client. (__Fasoli__)
- File: utility per leggere e scrivere da file (__Insieme__)
- Glossario: utility per modificare e leggere il glossario (__Fasoli__)
  - Salvataggio e lettura da file

### Client

- GUI
  - Finestra Principale (main class) per il glossario (__Rocco e Sara__)
    - Deve avere dei metodi per aggiornare la lista dei termini del glossario
    - Primo avvio (file di impostazioni assente):
      - Creo file con opzioni di default
      - avviso l'utente che deve configurare le opzioni
      - informo l'utente sull'uso del Manuale
    - Esportare il glossario su file in locale lato client
    - Importare termini da file lato client
  - Finestra opzioni (per configurare la connessione)
    - Devono essere salvate e caricate da file
  - Finestra manuale utente
    - contiene diverse schede

- Interfaccia di Rete per la connessione (classe Network)
  - richiede un socket collegato col server
  - deve ricevere messaggi dal server ed eseguire i comandi, aggiornando la GUI (thread separato)
  - deve poter mandare messaggi al server

### GlossarioLib

è la libreria condivisa dal client e il server che contiene delle Classi comuni, tra cui:
- FileUtil che permette di leggere e scrivere da file comodamente
- Glossary che rappresenta il glossario

__Nota Bene:__ la documentazione e il programma devono essere in inglese alla fine!
