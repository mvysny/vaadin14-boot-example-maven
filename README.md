[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/vaadin-flow/Lobby#?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Build Status](https://travis-ci.org/mvysny/vaadin14-embedded-jetty.svg?branch=master)](https://travis-ci.org/mvysny/vaadin14-embedded-jetty)

# Vaadin 14 npm Polymer 3 running in Embedded Jetty

A demo project showing the possibility of running a Vaadin 14 app from an
embedded Jetty, as a simple `main()` method.

## Developing

To access it directly from github, clone the repository and import the project to the IDE of your choice as a Maven project. You need to have Java 8 or 11 installed.

1. Import the project into your IDE
2. Run `mvn -C clean package` in the project
3. Run the `ManualJetty` class as an application (run the `main()` method).
   The app will use npm to download all javascript libraries (will take a long time)
   and will start in development mode.

See [ManualJetty.java](src/main/java/com/vaadin/starter/skeleton/ManualJetty.java)
for details on how to configure Jetty properly.

## Packaging for production

To package in production mode:

1. `mvn -C clean package -Pproduction`
2. `cd target`
3. `java -jar vaadin14-embedded-jetty-1.0-SNAPSHOT-jar-with-dependencies.jar`

Head to [localhost:8080/](http://localhost:8080).

## More info

Run using `mvn jetty:run` and open [http://localhost:8080](http://localhost:8080) in the browser.

If you want to run your app locally in the production mode, run `mvn jetty:run -Pproduction`.

For a full Vaadin application example, there are more choices available also from [vaadin.com/start](https://vaadin.com/start) page.
