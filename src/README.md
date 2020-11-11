Pour tester le front : 

- Sans docker : `ng serve`, ajouter `--watch` pour mettre à jour automatiquement en cas de changement.
- Avec docker (prod) : `ng build --prod `, puis `docker build -t test-front .` et enfin `docker run -p 8080:80 test-front` 8080 peut être remplacé par n'importe quel numéro de port.

Pour tester le back :

- Avec docker : Il faut build le .war, dans *intellij* c'est dans **build/build artefacts** puis **back:war** (voire avec maven pour faire sans intellij). Ensuite, `docker build -t test-back .` et enfin `docker run -p 8080:8080 test-back` le premier 8080 pouvant être changé.



Pour tester le tout : `docker-compose up` (ajouter `--force-recreate --build` permet d'éviter des surprises avec docker !)

Le front doit être accessible sur `http://localhost:8080/` (pour le moment projet de base angular), et le back à `http://localhost:9000/api/v1/`.

`http://localhost:9000/api/v1/hello/world` affiche *hello world !* si tout va bien.S

Pour relancer les tests, pour le moment il faut remettre la bdd dans son état originel avec : `docker-compose up -d --build --force-recreate --renew-anon-volumes db`