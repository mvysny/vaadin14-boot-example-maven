package com.vaadin.starter.skeleton;

import com.github.mvysny.kaributesting.v10.MockVaadin;
import com.github.mvysny.kaributesting.v10.Routes;
import com.vaadin.flow.component.button.Button;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.github.mvysny.kaributesting.v10.LocatorJ.*;
import static com.github.mvysny.kaributesting.v10.NotificationsKt.expectNotifications;

/**
 * Uses the Karibu-Testing framework: https://github.com/mvysny/karibu-testing/tree/master/karibu-testing-v10
 * @author mavi
 */
public class MainViewTest {
    private static Routes routes;

    @BeforeClass
    public static void createRoutes() {
        // initialize routes only once, to avoid view auto-detection before every test and to speed up the tests
        routes = new Routes().autoDiscoverViews("com.vaadin.starter.skeleton");
    }

    @Before
    public void setupVaadin() {
        MockVaadin.setup(routes);
    }

    @After
    public void teardownVaadin() {
        MockVaadin.tearDown();
    }

    @Test
    public void clickButton() {
        // simulate a button click as if clicked by the user
        _click(_get(Button.class, spec -> spec.withCaption("Click me")));

        // check that the notification has been shown
        expectNotifications("Clicked!");
    }
}
