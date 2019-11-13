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
for details on how Jetty is configured for embedded mode.

## Packaging for production

To package in production mode:

1. `mvn -C clean package -Pproduction`
2. `cd target`
3. `java -jar vaadin14-embedded-jetty-1.0-SNAPSHOT-uberjar.jar`

Head to [localhost:8080/](http://localhost:8080).

## About The Project

Let's look at all files that this project is composed of, and what are the points where you'll add functionality:

| Files | Meaning
| ----- | -------
| [pom.xml](pom.xml) | Maven 2 build tool configuration files. Maven is used to compile your app, download all dependency jars and build a war file
| [.travis.yml](.travis.yml) | Configuration file for [Travis-CI](http://travis-ci.org/) which tells Travis how to build the app. Travis watches your repo; it automatically builds your app and runs all the tests after every commit.
| [.gitignore](.gitignore) | Tells [Git](https://git-scm.com/) to ignore files that can be produced from your app's sources - be it files produced by Gradle, Intellij project files etc.
| [Procfile](Procfile) | Configures Heroku on how your application is launched in the cloud.
| [webpack.config.js](webpack.config.js) | TODO
| [src/main/java](src/main/java) | Place the sources of your app here.
| [MainView.java](src/main/java/com/vaadin/starter/skeleton/MainView.java) | The main view, shown when you browse for http://localhost:8080/
| [ManualJetty.java](src/main/java/com/vaadin/starter/skeleton/ManualJetty.java) | Launches the Embedded Jetty; just run the `main()` method.
| [src/main/resources/](src/main/resources) | A bunch of static files not compiled by Java in any way; see below for explanation.
| [simplelogger.properties](src/main/resources/simplelogger.properties) | Configures the logging engine; this demo uses the SLF4J logging library with slf4j-simple logger.
| [src/main/webapp/](src/main/webapp) | Static web files served as-is by the web container.
| [src/test/java/](src/test/java) | Your unit & integration tests go here.
| [MainViewTest.java](src/test/java/com/vaadin/starter/skeleton/MainViewTest.java) | Tests the Vaadin UI; uses the [Karibu-Testing](https://github.com/mvysny/karibu-testing) UI test library.
| [frontend/](frontend) | TODO
| `node_modules` | populated by `npm` - contains sources of all JavaScript web components.

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
