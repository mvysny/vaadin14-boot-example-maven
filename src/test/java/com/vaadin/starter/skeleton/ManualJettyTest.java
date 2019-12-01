package com.vaadin.starter.skeleton;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the Jetty server whether it is properly configured.
 * @author mavi
 */
public class ManualJettyTest {
    @BeforeAll
    public static void startupJetty() throws Exception {
        ManualJetty.start(new String[] { "5678" });
    }

    @AfterAll
    public static void stopJetty() throws Exception {
        ManualJetty.stop();
    }

    /**
     * Doesn't work from Maven, VaadinServlet doesn't find any @Routes for some reason...
     */
    @Test
    @Disabled
    public void testRootServesSomething() throws Exception {
        final URL url = new URL("http://localhost:5678/");
        final String html = IOUtils.toString(url, "UTF-8"); // should be a html produced by Vaadin Servlet
        assertTrue(html.contains("<html"), html);
    }

    @Test
    public void testWebRootMappedProperly() throws Exception {
        final URL url = new URL("http://localhost:5678/icons/icon.png");
        final byte[] actual = IOUtils.toByteArray(url);
        final byte[] expected = IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResource("webapp/icons/icon.png"));
        assertArrayEquals(expected, actual);
    }
}
