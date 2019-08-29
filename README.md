[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/vaadin-flow/Lobby#?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Build Status](https://travis-ci.org/mvysny/vaadin14-embedded-jetty.svg?branch=master)](https://travis-ci.org/mvysny/vaadin14-embedded-jetty)

# Vaadin 14 npm Polymer 3 running in Embedded Jetty

A demo project showing the possibility of running a Vaadin 14 app from an
embedded Jetty, as a simple `main()` method.

## Developing

Clone this github repository and import the project to the IDE of your choice as a Maven project. You need to have Java 8 or 11 installed.

1. Import the project into your IDE
2. Run `mvn -C clean package` in the project, to configure Vaadin for npm mode.
3. Run/Debug the `ManualJetty` class as an application (run the `main()` method).
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

## Heroku Integration

See the [Live Demo of the app running on Heroku](https://vaadin14-embedded-jetty.herokuapp.com/).

To integrate with Heroku, you need to activate two Maven profiles:

1. The `production` profile which packages Vaadin in production mode
2. The `heroku` profile which uses the `frontend-maven-plugin` to install local node+npm in order for Vaadin Maven build to successfully run webpack to package for production.

> Note: unfortunately adding the `heroku:nodejs` buildpack in Heroku project settings did not worked for me,
I had to use the `frontend-maven-plugin`

Both profiles are activated by [heroku-settings.xml](heroku-settings.xml) Maven Settings file. To use the settings
file during Heroku build, set the `MAVEN_SETTINGS_PATH` config var to `heroku-settings.xml` in Heroku project settings tab.
See [Using a Custom Maven Settings File](https://devcenter.heroku.com/articles/using-a-custom-maven-settings-xml) and
[Stack Overflow: Activate Maven Profile On Heroku](https://stackoverflow.com/questions/11162194/triggering-maven-profiles-from-heroku-configured-environment-variables) for more details.

## More info

For a full Vaadin application example, there are more choices available also from [vaadin.com/start](https://vaadin.com/start) page.
