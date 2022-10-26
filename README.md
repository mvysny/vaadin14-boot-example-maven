[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/vaadin-flow/Lobby#?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/mvysny/vaadin14-embedded-jetty)

# Vaadin 14 npm Polymer 3 running in Embedded Jetty

A demo project showing the possibility of running a Vaadin 14 app from an
embedded Jetty, as a simple `main()` method. Uses [Vaadin Boot](https://github.com/mvysny/vaadin-boot).

Both the development and production modes are supported. Also, the project
demoes packaging itself both into a flatten uberjar and a zip file containing
a list of jars and a runner script. See "Packaging for production" below
for more details.

> Looking for **Vaadin 14 Gradle** version? See [vaadin14-embedded-jetty-gradle](https://github.com/mvysny/vaadin14-embedded-jetty-gradle)

> Looking for **Vaadin 23 Maven** version? See [vaadin-embedded-jetty](https://github.com/mvysny/vaadin-embedded-jetty)

## Developing

Clone this github repository and import the project to the IDE of your choice as a Maven project. You need to have Java 8 or 11 installed.

To run quickly from the command-line in development mode:

1. Run `./mvnw -C clean package exec:java`
2. Your app will be running on [http://localhost:8080](http://localhost:8080).

To run the app from your IDE:

1. Import the project into your IDE
2. Run `mvn -C clean package` in the project, to configure Vaadin for npm mode.
3. Run/Debug the `Main` class as an application (run the `main()` method).
   The app will use npm to download all javascript libraries (will take a long time)
   and will start in development mode.
4. Your app will be running on [http://localhost:8080](http://localhost:8080).
   
See [Main.java](src/main/java/com/vaadin/starter/skeleton/Main.java)
for details on how Jetty is configured for embedded mode.

### Missing `/src/main/webapp`?

Yeah, since we're not packaging to WAR but to uberjar/zip+jar, the `webapp` folder needs to be
served from the jar itself, and therefore it needs to reside in `src/main/resources/webapp`.

## Packaging for production

To package in production mode:

1. `mvn -C clean package -Pproduction`

The project packages itself in two ways:

1. As a flatten uberjar (a jar with all dependencies unpacked inside, which you can simply launch with `java -jar`).
   Please read below regarding inherent issues with flat uberjars.
   The deployable file is in `target/vaadin14-embedded-jetty-1.0-SNAPSHOT-uberjar.jar`
2. As a zip file with dependencies. The file is in `target/vaadin14-embedded-jetty-1.0-SNAPSHOT-zip.zip`

## Running in production mode

To build&run the flat uberjar:

1. `mvn -C clean package -Pproduction`
2. `cd target`
3. `java -jar vaadin14-embedded-jetty-1.0-SNAPSHOT-uberjar.jar`

To build&run the zip file:

1. `mvn -C clean package -Pproduction`
2. `cd target`
3. `unzip vaadin14-embedded-jetty-1.0-SNAPSHOT-zip.zip`
4. `./run`

Head to [localhost:8080/](http://localhost:8080).

## Warning Regarding Flat Uberjar

There is an inherent problem with flat uberjar (everything unpacked, then packed as a single jar):
it disallows repeated resources or duplicate files. That can be problematic especially for Java Service API
property files located under `META-INF/services/`, since the flat uberjar will simply
throw away any duplicate property files, which can cause certain libraries to remain unconfigured.
You should therefore always prefer the zip+jar distribution; if you keep using
flat uberjar then please keep these limitations in mind.

Another inherent issue is that it's impossible to see the dependencies of the app
as a list of jars in the `lib/` folder, since everything is unpacked into one huge jar file.

## About The Project

Let's look at all files that this project is composed of, and what are the points where you'll add functionality:

| Files | Meaning
| ----- | -------
| [pom.xml](pom.xml) | Maven 3 build tool configuration files. Maven is used to compile your app, download all dependency jars and build the zip+uberjar file
| [.travis.yml](.travis.yml) | Configuration file for [Travis-CI](http://travis-ci.org/) which tells Travis how to build the app. Travis watches your repo; it automatically builds your app and runs all the tests after every commit.
| [.gitignore](.gitignore) | Tells [Git](https://git-scm.com/) to ignore files that can be produced from your app's sources - be it files produced by Gradle, Intellij project files etc.
| [Procfile](Procfile) | Configures Heroku on how your application is launched in the cloud.
| [webpack.config.js](webpack.config.js) | TODO
| [src/main/java](src/main/java) | Place the sources of your app here.
| [MainView.java](src/main/java/com/vaadin/starter/skeleton/MainView.java) | The main view, shown when you browse for http://localhost:8080/
| [Main.java](src/main/java/com/vaadin/starter/skeleton/Main.java) | Launches the Embedded Jetty; just run the `main()` method.
| [src/main/resources/](src/main/resources) | A bunch of static files not compiled by Java in any way; see below for explanation.
| [simplelogger.properties](src/main/resources/simplelogger.properties) | Configures the logging engine; this demo uses the SLF4J logging library with slf4j-simple logger.
| [src/main/webapp/](src/main/webapp) | Static web files served as-is by the web container.
| [src/test/java/](src/test/java) | Your unit & integration tests go here.
| [MainViewTest.java](src/test/java/com/vaadin/starter/skeleton/MainViewTest.java) | Tests the Vaadin UI; uses the [Karibu-Testing](https://github.com/mvysny/karibu-testing) UI test library.
| [frontend/](frontend) | TODO
| `node_modules` | populated by `npm` - contains sources of all JavaScript web components.

## Heroku Integration

See the [Live Demo of the app running on Heroku](https://vaadin14-embedded-jetty.herokuapp.com/).

To integrate with Heroku, you need to activate the `production` Maven profile
which packages Vaadin in production mode

The profile is activated by [heroku-settings.xml](heroku-settings.xml) Maven Settings file. To use the settings
file during Heroku build, set the `MAVEN_SETTINGS_PATH` config var to `heroku-settings.xml` in Heroku project settings tab.
See [Using a Custom Maven Settings File](https://devcenter.heroku.com/articles/using-a-custom-maven-settings-xml) and
[Stack Overflow: Activate Maven Profile On Heroku](https://stackoverflow.com/questions/11162194/triggering-maven-profiles-from-heroku-configured-environment-variables) for more details.

## More info

For a full Vaadin application example, there are more choices available also from [vaadin.com/start](https://vaadin.com/start) page.
