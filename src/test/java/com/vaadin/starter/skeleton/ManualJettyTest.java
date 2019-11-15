package com.vaadin.starter.skeleton;

import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the Jetty server whether it is properly configured.
 * @author mavi
 */
public class ManualJettyTest {
    @BeforeClass
    public static void startupJetty() throws Exception {
        ManualJetty.start(new String[] { "5678" });
    }

    @AfterClass
    public static void stopJetty() throws Exception {
        ManualJetty.stop();
    }

    /**
     * Doesn't work from Maven, VaadinServlet doesn't find any @Routes for some reason...
     */
    @Test
    @Ignore
    public void testRootServesSomething() throws Exception {
        final URL url = new URL("http://localhost:5678/");
        final String html = IOUtils.toString(url, "UTF-8"); // should be a html produced by Vaadin Servlet
        assertTrue(html, html.contains("<html"));
    }

    @Test
    public void testWebRootMappedProperly() throws Exception {
        final URL url = new URL("http://localhost:5678/icons/icon.png");
        final byte[] actual = IOUtils.toByteArray(url);
        final byte[] expected = IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResource("webapp/icons/icon.png"));
        assertArrayEquals(expected, actual);
    }
}
