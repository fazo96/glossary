# Documentazione Glossario

Enrico Fasoli, Gianluca Rocco, Roberto Dalipaj, Marco Carne, Raffaele Cristodaro, Sara Dendena

## Funzionamento

Il server mantiene la lista di tutte le parole con una stringa per la parola e una per il significato. I client si collegano e il server crea un thread per la comunicazione.
Quando una parola viene inserita o modificata dal client viene aggiornata la lista e tutti i client ricevono l’aggiornamento via socket tcp.

_Record_: classe java che rappresenta la combinazione di una parola e il suo significato.

_Gestione thread safe_: i dati sono in una lista che tramite Collections.synchronizedList viene resa thread safe automaticamente da Java.

_Protocollo_: vengono inviate stringhe per comunicare, il record viene inviato nel formato “parola: significato” con “parola” tutto minuscolo.

_Interfaccia_:
- lista di parole: clicco su una parola e vedo il significato e posso modificarlo.
- filtro: un campo di testo che posso usare per filtrare la lista
- tasto inserisci per inserire una nuova parola
- tasto modifica per modificare il significato di una parola

_Roadmap_ (percorso di sviluppo):
- Server che carica le parole da file
- Server che accetta le connessioni e gestisce i comandi
- Client che invia comandi e riceve risposte
- GUI Client

## Implementazione

_Server_:
- Server: accetta le connessioni (__Insieme__)
- ClientHandler: un'instanza per ogni client con il suo thread. La classe
contiene la lista dei client. (__Fasoli__)
- File: utility per leggere e scrivere da file (__Insieme__)
- Glossario: utility per modificare e leggere il glossario (__Fasoli__)
  - Salvataggio e lettura da file

_Client_:
- GUI
  - Finestra Principale per il glossario (__Rocco e Sara__)
  - Finestra opzioni (per configurare la connessione)
    - Devono essere salvate su file
  - Finestra manuale utente
  - Esportare il glossario in locale lato client
  - Importare termini lato client
- Interfaccia di Rete per la connessione

__Nota Bene:__ la documentazione e il programma devono essere in inglese alla fine!
