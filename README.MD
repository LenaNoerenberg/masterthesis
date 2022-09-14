# Masterarbeit

Dies ist der Prototyp der im Rahmen der Masterarbeit:

#### Optimierung von automatisierten Container Deployments für DevOps Test-Sets durch Einsatz von Feature Toggles

Dieses Projekt ist abhängig von zwei im Rahmen dieser Arbeit erzeugten Repositorys:

1. Test-Repository: hier liegen die JUnit Tests, mit denen in der Applikation getestet werden soll

   https://gitlab.beuth-hochschule.de/s80984/testrepository

2. Studierenden-Repository: dies Simuliert die Abgabe von Programmierübungsaufgaben.

   https://github.com/s80984/se-demo

#### Starten der Applikation

Die Backend-Applikation kann mit folgendem Befehl gestartet werden:

```
mvn spring-boot:run
```

Anschließend sollte das Feature Toggle Management unter folgender URL abrufbar sein `http://localhost:8080`.

Die Frontend-Applikation wird im Verzeichnis  `/react-frontend` mit folgendem Befehl gestartet werden:

```
npm start
```

Anschließend sollte die Applikation unter folgender URL abrufbar sein `http://localhost:3000`.



#### Starten des Prototyps

Der Prototyp braucht eine .env Konfigurationsdatei in denen die Git Tokens und die Git Usernamen gespeichert werden.

```
GITHUB_USERNAME=...
GITHUB_TOKEN=...
GITLAB_USERNAME=...
GITLAB_TOKEN=...
GITHUB_URL=...
```



Der Prototyp wird mit folgendem dem Befehl gestartet:

```
docker-compose up --build
```


Anschließend sollte sie unter folgender URL abrufbar sein `http://localhost:3000`.