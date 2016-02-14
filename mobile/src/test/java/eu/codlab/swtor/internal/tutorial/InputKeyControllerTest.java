package eu.codlab.swtor.internal.tutorial;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.swtor.GenerateCodeTest;
import eu.codlab.swtor.internal.tutorial.non_test.DependencyInjectorImplementation;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Kévin on 05/02/2016.
 */
public class InputKeyControllerTest {
    public final String OK = "RULAB3CHR3KDHFC3";
    public Context _context;

    @Before
    public void init() {
        _context = Mockito.mock(Context.class);

        DependencyInjectorFactory.init(_context, new DependencyInjectorImplementation());
    }

    @Test
    public void testFailContentNull() {
        InputKeyController controller = new InputKeyController();
        controller.setContent(null);

        assertFalse(controller.isValid());
    }

    @Test
    public void testIsValid() {
        InputKeyController controller = new InputKeyController();
        controller.setContent(GenerateCodeTest.CODE_OK);

        assertTrue(controller.isValid());
    }

    @Test
    public void testIsValidFalse() {
        InputKeyController controller = new InputKeyController();
        controller.setContent("RULAB3CH+?'DHF__");

        assertFalse(controller.isValid());
    }

    @Test
    public void testFailContent() {
        InputKeyController controller = new InputKeyController();
        controller.setContent("something");

        assertFalse(controller.isValid());
    }

    @Test
    public void testOkContent() {
        InputKeyController controller = new InputKeyController();
        controller.setContent(OK);

        assertTrue(controller.isValid());
    }

    @Test
    public void testOnTryValidate() {
        InputKeyController controller = new InputKeyController();

        assertFalse(controller.onTryValidate());
    }
}
