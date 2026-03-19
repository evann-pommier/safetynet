# SafetyNet Alert System

Application REST développée avec Spring Boot permettant de gérer les alertes de sécurité civile. Elle expose des endpoints pour informer les services de secours sur les habitants d'une zone, leurs informations médicales et les casernes de pompiers les desservant.

---

## Prérequis

- Java 17
- Maven 3.8+

---

## Lancer le projet
```bash
mvn spring-boot:run
```

L'application démarre sur `http://localhost:8080`.

Les données sont chargées automatiquement depuis `src/main/resources/data.json` au démarrage.

> ⚠️ Les données sont stockées en mémoire. Toute modification est perdue au redémarrage.

---

## Endpoints

#### Alertes

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/firestation?stationNumber={n}` | Personnes couvertes par une caserne + décompte adultes/enfants |
| GET | `/childAlert?address={adresse}` | Enfants habitant à une adresse + membres du foyer |
| GET | `/phoneAlert?firestation={n}` | Numéros de téléphone des résidents d'une caserne |
| GET | `/fire?address={adresse}` | Habitants d'une adresse + infos médicales + caserne |
| GET | `/flood/stations?stations={n,n,...}` | Foyers desservis par plusieurs casernes, groupés par adresse |
| GET | `/personInfo?lastName={nom}` | Infos détaillées des personnes portant ce nom |
| GET | `/communityEmail?city={ville}` | Emails de tous les habitants d'une ville |

#### Personnes

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/person` | Liste de toutes les personnes |
| POST | `/person` | Ajouter une personne |
| PUT | `/person` | Modifier une personne |
| DELETE | `/person?firstName={prénom}&lastName={nom}` | Supprimer une personne |

#### Casernes

| Méthode | URL | Description |
|---------|-----|-------------|
| POST | `/firestation` | Ajouter un mapping adresse/caserne |
| PUT | `/firestation` | Modifier un mapping adresse/caserne |
| DELETE | `/firestation?address={adresse}&station={n}` | Supprimer un mapping |

#### Dossiers médicaux

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/medicalRecord` | Liste de tous les dossiers médicaux |
| POST | `/medicalRecord` | Ajouter un dossier médical |
| PUT | `/medicalRecord` | Modifier un dossier médical |
| DELETE | `/medicalRecord?firstName={prénom}&lastName={nom}` | Supprimer un dossier médical |

---

## Exemples de requêtes

#### Ajouter une personne
```json
POST /person
{
  "firstName": "John",
  "lastName": "Boyd",
  "address": "1509 Culver St",
  "city": "Culver",
  "zip": "97451",
  "phone": "841-874-6512",
  "email": "john@email.com"
}
```

#### Ajouter un mapping caserne
```json
POST /firestation
{
  "address": "1509 Culver St",
  "station": "3"
}
```

#### Ajouter un dossier médical
```json
POST /medicalRecord
{
  "firstName": "John",
  "lastName": "Boyd",
  "birthdate": "03/06/1984",
  "medications": ["aznol:350mg", "hydrapermazol:100mg"],
  "allergies": ["nillacilan"]
}
```

---

## Tests
```bash
mvn test
```

#### Rapport de couverture JaCoCo
```bash
mvn verify
```

Le rapport est généré dans `target/site/jacoco/index.html`.

---

## Stack technique

- Java 17 / Spring Boot 3.4.3 / Spring Web MVC
- Hibernate Validator / Lombok / Jackson
- JUnit 5 / Mockito / JaCoCo

---

## Structure du projet
```
src/
├── main/
│   ├── java/com/openclassrooms/safetynet/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── model/
│   │   ├── record/
│   │   ├── exception/
│   │   └── util/
│   └── resources/
│       ├── data.json
│       └── application.properties
└── test/
    └── java/com/openclassrooms/safetynet/
        ├── controller/
        └── service/
```