# Documentation

## Comment lancer l'application

Nous utilisons [*docker*](https://docs.docker.com/get-docker/) et [*docker-compose*](https://docs.docker.com/compose/install/) pour le déployement.  

### Utilisateur

Pour lancer notre application, s'il s'agit d'une release, il suffit de lancer `docker-compose up`, et de se rendre sur [localhost:8080](http://localhost:8080). Sinon, il va falloir passer par l'étape de **Build**.

### Build

Pour pouvoir build notre projet, il faut avoir installé *maven* et *npm*.

La procédure d'installation (testé sous ubuntu 20.04):
```bash
sudo apt install maven npm;
```

Pour la première fois, il faut installer les dépendances pour le *front*. Il faut pour cela aller dans le dossier `src/front` et lancer la commande `npm install`. Il suffit alors de lancer `scripts/run-prod.sh` pour lancer notre application, accessible sur [localhost:8080](http://localhost:8080).

Pour faire à la Main : pour build le *front*, il faut lancer dans `src/front` la commande `ng build --prod`, et pour la *back*,  il faut lancer dans `src/back` la commande `mvn war:war`.


Pour lancer les tests unitaires, il faut lancer le script `scripts/run-test.sh`.

Pour ne lancer que le *back* et la *db*, afin de tester le *front* avec `ng serve --watch`: `docker-compose up --build --force-recreate --renew-anon-volumes back db`. Il faut ajouter `app` dans la liste des services pour ajouter *phpmyadmin*, accessible sur le port `4242`.

Pour lancer les tests avec *cypress*, il faut lancer : `$(npm bin)/cypress open `.

## Générale

### Architecture du projet

- 3 images docker dans leur dossier respectif :
    - Back-end (Java)
    - Front-end (Angular)
    - Base de données (MySQL)

#### Back-end

- main
    - domain : Définition des value object
    - dao : Définition abstraite des data access object
        - sql : Implémentation concrète des data access object et l'accès à la db
    - routes : Implémentation des routes de l'api
- test
    - dao/sql : Tests des comportements attendus lors des intéractions avec la base de données
    - domain : Tests de la validité des objets du domaine

#### API

Nous utilisons *swagger* pour décrire notre api. Les sources de notre swagger sont dans `/resources/api`.

#### Front-end

- src : Point d'entrée de l'application
    - environments : Configuration du front-end
    - app : Sources du front-end
        - components : Implémentation des controlleurs
        - models : Définition du modèle
        - services : Implémentation des services
- cypress : Tests end to end

#### Database

- sql-scripts : Scripts qui seront copiés dans l'initialisation du docker
    - xx_name.sql : Fichier sql qui sera exécuté par ordre alphabétique des noms de fichier, `xx` sert à les ordonner

### Workflow

#### Linters

- Pour développer en back-end, nous utilisons checkstyle dont la configuration se trouve dans le fichier `.github/linters/sun_check.xml`
- Pour développer en front-end, nous utilisons tslint dont la configuration se trouve dans le fichier `src/front/tslint.json`
- Pour tester la propreté du code, des actions github pour le back et le front sont exécutées à chaque push et pull-request (voir dans `.github/workflows` les fichiers `back-lint.yml` et `front-lint.yml`)

#### Commits

Les commits ne doivent concerner qu'une seule tâche/fonctionnalité, dans la mesure où segmenter les commits ne crée pas un état instable sur l'un d'entre eux. Si le commit n'est pas stable, ne pas l'envoyer sur `main` et annoter WIP (work in progress).

Le titre du commit doit contenir entre crochets la partie du projet concernée, exemple :
- [Kaban] -> modification des .md pour l'organisation
- [Refactor] -> indiquer des changements importants pour le projet
- [Back] -> modification de code dans `src/back`
- [DB] -> modification de code dans `src/bdd`
- [Tests] -> ajout de tests dans le projet
- [Actions] -> gestion des actions github
- [Cleaning] -> retouches apportées au code pour le rendre plus lisble, linter
- etc...

Lorsque l'on travaille sur une longue fonctionnalité, utiliser une branche à cet effet et push régulièrement pour que tous les développeurs puissent observer et potentiellement intervenir.

#### Tests

**Tester intégralement**

La commande `docker-compose --file docker-compose.test.yml up --exit-code-from tests` effectue les groupes de tests suivants, de manière séquentielle :
- Création de la base de données
- Utilisation (directe) de la base de données
- Compilation du back-end
- Utilisation (depuis le back) de la base de données
- Utilisation des routes du back-end

Pour l'instant, les tests end to end avec cypress ne sont pas dockerisés, il faut les lancer à la main.

**Intégration automatique github :**

L'action `docker-publish.yml` lance le docker-compose des tests automatiquement à chaque push et pull-request.

**Tests pendant le développement :**
- Pour tester/reinitialiser manuellement la **base de données**, lancer `docker-compose -f docker-compose.yml up --build --force-recreate --renew-anon-volumes db app`
    - Cela va reconstruire entièrement la base de données et son schéma, si il fonctionne.
    - Ensuite cela va faire des insertions qui doivent elles aussi fonctionner
    - Pour visualiser et manipuler la base de données, phpmyadmin est disponible à `localhost:4242` (package app)
    - La base de données est accessible à `localhost:3307` (package db)
- Pour tester manuellement le **front-end**, lancer `docker-compose up db back`, il suffit ensuite d'utiliser `ng serve` pour compiler le front en temps réel.
- Pour tester manuellement le **back-end**, il faut utiliser la même commande que pour les tests complets.
- Pour les tests **end to end**, il faut utiliser cypress dans `src/front/cypress`