# Documentazione Tecnica: Smart Warehouse Management System (WMS)

**Progetto Didattico: Architettura a Microservizi, NoSQL e Event-Driven**



---



## 1. Introduzione e Obiettivi

Il progetto mira alla realizzazione di un sistema di gestione magazzino (WMS) focalizzato su prodotti deperibili. L'architettura è progettata per dimostrare l'interazione tra componenti distribuiti (Microservizi), la persistenza dei dati non relazionale (NoSQL) e la comunicazione asincrona tramite Message Broker (Kafka).



### Principi Fondamentali

1.  **Logica FEFO (First-Expired-First-Out):** La priorità di uscita della merce è dettata rigorosamente dalla data di scadenza. Il lotto che scade prima deve uscire prima.

2.  **Unità di Misura:** La merce è gestita esclusivamente a **Peso (Kg)**.

3.  **Qualità:** Si assume che la merce in ingresso sia sempre in condizioni perfette (nessun controllo qualità all'ingresso).

4.  **Database Pattern:** Utilizzo di un approccio *Shared Database* per i dati operativi (MongoDB) per semplificare la gestione della consistenza dati in ambito scolastico.



---



## 2. Architettura del Sistema



Il sistema è composto da un ecosistema di microservizi sviluppati in **Java (Spring Boot)**, containerizzati tramite **Docker** e orchestrati per un ambiente On-Premise.



### 2.1 Diagramma dei Componenti

* **Client:** Webapp (Desktop per Ufficio / Mobile con Scanner per Magazzino).

* **Entry Point:** API Gateway.

* **Services:** Auth Service, Warehouse Service, Order Service, Analytics/Office Service.

* **Infrastructure:** MySQL (Auth), MongoDB (Shared Data), Apache Kafka (Events).



### 2.2 Stack Tecnologico

| Componente | Tecnologia | Ruolo |

| :--- | :--- | :--- |

| **Backend** | Java 17+ / Spring Boot 3 | Logica applicativa dei microservizi. |

| **Database Relazionale** | MySQL / PostgreSQL | Gestione Utenti e Ruoli (ACID rigoroso). |

| **Database NoSQL** | MongoDB | Gestione Lotti, Ordini e Storico (Flessibilità schema). |

| **Message Broker** | Apache Kafka + Zookeeper | Comunicazione asincrona e disaccoppiamento. |

| **Frontend** | Webapp (React/Angular o HTML+JS) | Interfaccia unica adattiva (Responsive). |

| **Deployment** | Docker & Docker Compose | Containerizzazione e orchestrazione locale. |



---



## 3. Descrizione dei Microservizi



### A. API Gateway

* **Ruolo:** Unico punto di ingresso per tutte le chiamate dal frontend.

* **Funzioni:** Indirizzamento delle richieste al microservizio corretto (Routing).



### B. Auth Service (Autenticazione)

* **Database:** MySQL (Database dedicato).

* **Funzione:** Gestisce la registrazione e il login degli operatori.

* **Logica:**

    * Verifica credenziali su DB Relazionale.

    * Genera un Token (es. JWT o Session ID) che identifica il ruolo (`MAGAZZINIERE` o `UFFICIO`).



### C. Warehouse Service (Gestione Magazzino)

* **Database:** MongoDB (Cluster Condiviso).

* **Attori:** Magazzinieri.

* **Funzionalità:**

    1.  **Ricevimento Merce:** Scansione barcode, inserimento peso, data scadenza. Assegnazione zona/scaffale.

    2.  **Produzione Eventi:** Alla conferma dello stoccaggio, produce un evento su Kafka (`goods_received`).



### D. Order Service (Ordini e Spedizioni)

* **Database:** MongoDB (Cluster Condiviso).

* **Attori:** Ufficio (Creazione), Magazzinieri (Evasione).

* **Funzionalità:**

    1.  **Creazione Ordine:** L'ufficio inserisce manualmente un ordine (Cliente X vuole 50kg di Mele).

    2.  **Algoritmo FEFO:** Il servizio interroga MongoDB per trovare i lotti di quel prodotto ordinati per scadenza crescente. "Prenota" i lotti necessari.

    3.  **Checklist Operativa:** Genera una lista di prelievo per il magazziniere.

    4.  **Evasione:** Il magazziniere scansiona i lotti indicati. Quando la lista è completa, l'ordine è "Spedito".

    5.  **Produzione Eventi:** Alla chiusura dell'ordine, produce un evento su Kafka (`order_shipped`).



### E. Office/Analytics Service (Ufficio e Storico)

* **Database:** MongoDB (Cluster Condiviso - Lettura).

* **Attori:** Impiegati Ufficio.

* **Funzionalità:**

    1.  **Consumer Kafka:** Ascolta i topic `goods_received` e `order_shipped` per aggiornare le statistiche in tempo reale.

    2.  **Dashboard Scadenze:** API che interroga il DB per mostrare i prodotti prossimi alla scadenza (Pull request dal frontend).

    3.  **Storico:** Visualizzazione dei movimenti passati.



---



## 4. Modello Dati (Data Model)



> **Nota Didattica:** Usiamo MongoDB per i dati operativi perché la struttura di un "Lotto" o di un "Ordine" potrebbe cambiare nel tempo (es. aggiunta di nuovi campi senza rompere il DB) e per la velocità di lettura. Usiamo SQL per gli utenti perché la struttura è fissa e relazionale.



### 4.1 SQL (Auth DB)

**Tabella: Users**

* `id` (PK)

* `username`

* `password_hash`

* `role` (Enum: WAREHOUSE_WORKER, OFFICE_CLERK)



### 4.2 MongoDB (Shared Operational DB)



**Collezione: `batches` (I Lotti di merce)**

Questo è il cuore del sistema FEFO.

```json

{

  "_id": "ObjectId('64b8f...')",

  "barcode": "XYZ-2023-001",

  "productType": "Mele Golden",

  "weightKg": 100.5,

  "expiryDate": ISODate("2023-12-31T00:00:00Z"), // Campo chiave per l'ordinamento FEFO

  "entryDate": ISODate("2023-11-01T10:00:00Z"),

  "status": "STORED", // Possibili valori: STORED, RESERVED, SHIPPED

  "location": {

      "zone": "A",

      "aisle": "2"

  }

}

```

Collezione: `orders` (Gli Oridini)

```json

{

  "_id": "ObjectId('75c9a...')",

  "customer": "Supermercato ABC",

  "creationDate": ISODate("2023-11-15T09:00:00Z"),

  "status": "PENDING", // PENDING, PROCESSING, COMPLETED

  "items": [

    {

      "productType": "Mele Golden",

      "requestedWeight": 50.0,

      "assignedBatches": ["ObjectId_Lotto1", "ObjectId_Lotto2"] // Link logici ai lotti prenotati

    }

  ]

}

```

## 5. Flussi Operativi e Comunicazione (Kafka)



### 5.1 Flusso Ingresso Merce (Producer)

1.  **Azione:** Il Magazziniere scansiona la merce e salva (POST su Warehouse Service).

2.  **Persistenza:** Warehouse Service salva il documento su MongoDB con stato `STORED`.

3.  **Evento:** Warehouse Service invia messaggio su Kafka Topic `goods_received`.

    * *Esempio Payload:* `{ "batchId": "...", "product": "Mele", "expiry": "2023-12-31" }`

4.  **Reazione:** Office Service (Consumer) legge il messaggio. Se la data di scadenza è vicina (< 7 gg), crea un flag o una notifica interna per evidenziarlo nella dashboard vendite.



### 5.2 Flusso Uscita Merce (FEFO Logic)

1.  **Azione:** L'Ufficio crea un ordine manuale tramite Webapp (POST su Order Service).

2.  **Query FEFO:** Order Service esegue una query su MongoDB per trovare i lotti disponibili:

    `db.batches.find({ product: "...", status: "STORED" }).sort({ expiryDate: 1 })`

3.  **Prenotazione:** I lotti più vecchi vengono aggiornati a `status: "RESERVED"`.

4.  **Checklist:** Il Magazziniere apre la checklist sul terminale e vede dove andare e cosa prendere.

5.  **Conferma:** Il Magazziniere scansiona i lotti fisici per conferma.

6.  **Evento:** Order Service chiude l'ordine e invia messaggio su Kafka Topic `order_shipped`.

7.  **Reazione:** Office Service (Consumer) legge il messaggio e aggiorna lo storico movimenti per le statistiche.



---



## 6. Gestione delle Transazioni e Consistenza



> **Nota Didattica:** In un sistema a microservizi distribuiti, garantire che "tutto avvenga o niente avvenga" (ACID) è la sfida principale.



Nel nostro progetto adottiamo le seguenti strategie:



1.  **Shared Database (MongoDB):** Poiché Warehouse e Order service scrivono sullo stesso DB fisico, le operazioni sui dati sono atomiche e sicure (non servono transazioni distribuite complesse come il pattern SAGA).

2.  **Database + Kafka (Dual Write):** Utilizziamo un approccio *"Best Effort / At-Least-Once"*.

    * **Priorità al Dato:** Prima si salva il dato nel DB (Priorità assoluta alla persistenza della merce fisica).

    * **Notifica:** Subito dopo si tenta l'invio a Kafka.

    * **Failure Handling:** In caso di fallimento di Kafka (rete giù), il sistema logga un errore critico ma non blocca l'operatività del magazzino. La dashboard dell'ufficio potrebbe essere momentaneamente non aggiornata (Eventual Consistency), ma la merce è fisicamente gestibile.



---



## 7. Interfaccia Utente (Client)



È prevista un'unica Web Application che si adatta dinamicamente in base al Device e al Ruolo utente loggato.



* **Login:** Pagina comune username/password.

* **Ruolo Magazziniere (Mobile View):**

    * Accesso alla fotocamera tramite libreria JS (es. `html5-qrcode`) per leggere i Barcode.

    * Interfaccia semplificata con bottoni grandi (Touch-friendly).

    * Flow: Scansione -> Input Peso -> Conferma.

* **Ruolo Ufficio (Desktop View):**

    * Dashboard analitica complessa.

    * **Notifiche Scadenza:** Tabella "In Scadenza" popolata tramite polling (il frontend richiede dati aggiornati ogni tot secondi all'Office Service) per mostrare i prodotti da svendere.

    * Form per inserimento manuale nuovi ordini.



---

## 8. Istruzioni di Deployment

Il progetto include un file `docker-compose.yml` per l'avvio immediato dell'intero stack.



**Prerequisiti:** Docker Desktop installato.



**Comandi:**

```Bash

# Avvio di tutta l'infrastruttura in background

docker-compose up -d



# Visualizzazione dei log

docker-compose logs -f



# Arresto e pulizia del sistema

docker-compose down

```



**Struttura `docker-compose.yml` (Sintesi dei container):**



1. `mongo`: Porta 27017 (Dati condivisi)

2. `mysql`: Porta 3306 (Dati utenti)

3. `zookeeper`: Porta 2181 (Coordina Kafka)

4. `kafka`: Porta 9092 (Broker messaggi)

5. `auth-service`: Porta 8081 (Backend Java)

6. `warehouse-service`: Porta 8082 (Backend Java)

7. `order-service`: Porta 8083 (Backend Java)

8. `office-service`: Porta 8084 (Backend Java)

9. `api-gateway`: Porta 8080 (Unico punto esposto verso l'esterno)
