# Documentazione Glossario

Enrico Fasoli, Gianluca Rocco, Roberto Dalipaj, Marco Carne, Raffaele Cristodaro

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
- Server: accetta le connessioni
- ClientHandler: un'instanza per ogni client con il suo thread. La classe
contiene la lista dei client.
- File: utility per leggere e scrivere da file
- Glossario: utility per modificare e leggere il glossario

_Client_:
- GUI
- Finestra Principale per il glossario
- Finestra opzioni (per configurare la connessione)
- devono essere salvate su file
- Manuale utente
- Interfaccia di Rete
