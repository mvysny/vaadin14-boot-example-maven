package com.vaadin.starter.skeleton;

import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.startup.ServletContextListeners;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.*;

import java.net.URI;
import java.net.URL;

/**
 * Run {@link #main(String[])} to launch your app in Embedded Jetty.
 * @author mavi
 */
public final class ManualJetty {
    public static void main(String[] args) throws Exception {
        final URI webRootUri = ManualJetty.class.getResource("/webapp/").toURI();

        final WebAppContext context = new WebAppContext();
        context.setBaseResource(Resource.newResource(webRootUri));
        context.setContextPath("/");
        context.addServlet(VaadinServlet.class, "/*");
        context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*\\.jar|.*/classes/.*");
        context.setConfigurationDiscovered(true);
        context.setConfigurations(new Configuration[] {
                new AnnotationConfiguration(),
                new WebInfConfiguration(),
                new WebXmlConfiguration(),
                new MetaInfConfiguration()
        });
        context.getServletContext().setExtendedListenerTypes(true);
        context.addEventListener(new ServletContextListeners());

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
}

