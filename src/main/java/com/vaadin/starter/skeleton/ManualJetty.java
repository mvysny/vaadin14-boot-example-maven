package com.vaadin.starter.skeleton;

import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.startup.ServletContextListeners;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.*;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Run {@link #main(String[])} to launch your app in Embedded Jetty.
 * @author mavi
 */
public final class ManualJetty {
    public static void main(String[] args) throws Exception {

        // detect&enable production mode
        if (isProductionMode()) {
            // fixes https://github.com/mvysny/vaadin14-embedded-jetty/issues/1
            System.out.println("Production mode detected, enforcing");
            System.setProperty("vaadin.productionMode", "true");
        }

        final WebAppContext context = new WebAppContext();
        context.setBaseResource(findWebroot());
        context.setContextPath("/");
        context.addServlet(VaadinServlet.class, "/*");
        context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*\\.jar|.*/classes/.*");
        context.setConfigurationDiscovered(true);
        context.setConfigurations(new Configuration[] {
                new AnnotationConfiguration(),
                new WebInfConfiguration(),
                new WebXmlConfiguration(),
                new MetaInfConfiguration()
                // new FragmentConfiguration() // ignores META-INF/web-fragment.xml from this jar, we have to do the production mode detection manually
        });
        context.getServletContext().setExtendedListenerTypes(true);
        context.addEventListener(new ServletContextListeners());
        WebSocketServerContainerInitializer.initialize(context); // fixes IllegalStateException: Unable to configure jsr356 at that stage. ServerContainer is null

        int port = 8080;
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }
        final Server server = new Server(port);
        server.setHandler(context);
        server.start();
        System.out.println("\n\n=================================================\n\n" +
        "Please open http://localhost:" + port + " in your browser\n\n" +
        "If you see the 'Unable to determine mode of operation' exception, just kill me and run `mvn -C clean package`\n\n" +
        "=================================================\n\n");
        server.join();
    }

    private static boolean isProductionMode() {
        final String probe = "META-INF/maven/com.vaadin/flow-server-production-mode/pom.xml";
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(probe) != null;
    }

    private static Resource findWebroot() throws URISyntaxException, MalformedURLException {
        // don't look up directory as a resource, it's unreliable: https://github.com/eclipse/jetty.project/issues/4173#issuecomment-539769734
        // instead we'll look up the /webapp/ROOT and retrieve the parent folder from that.
        final URL f = ManualJetty.class.getResource("/webapp/ROOT");
        if (f == null) {
            throw new IllegalStateException("Invalid state: the resource /webapp/ROOT doesn't exist, has webapp been packaged in as a resource?");
        }

        // Resolve file to directory
        URI webRootUri = f.toURI().resolve("./").normalize();
        System.err.println("WebRoot is " + webRootUri);
        return Resource.newResource(webRootUri);
    }
}

