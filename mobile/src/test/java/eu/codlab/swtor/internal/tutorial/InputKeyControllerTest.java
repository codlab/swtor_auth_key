package eu.codlab.swtor.internal.tutorial;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.swtor.GenerateCodeTest;
import eu.codlab.swtor.internal.tutorial.non_test.DependencyInjectorImplementation;
import eu.codlab.swtor.ui.tutorial.TutorialActivity;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Kévin on 05/02/2016.
 */
public class InputKeyControllerTest {
    public final String OK = "RULAB3CHR3KDHFC3";
    public Context _context;

    private InputKeyController mController;

    @Before
    public void init() {
        _context = Mockito.mock(Context.class);

        DependencyInjectorFactory.init(_context, new DependencyInjectorImplementation());

        mController = new InputKeyController();
    }

    @Test
    public void testFailContentNull() {
        mController.setContent(null);

        assertFalse(mController.isValid());
    }

    @Test
    public void testIsValid() {
        mController.setContent(GenerateCodeTest.CODE_OK);

        assertTrue(mController.isValid());
    }

    @Test
    public void testIsValidFalse() {
        mController.setContent("RULAB3CH+?'DHF__");

        assertFalse(mController.isValid());
    }

    @Test
    public void testFailContent() {
        mController.setContent("something");

        assertFalse(mController.isValid());
    }

    @Test
    public void testOkContent() {
        mController.setContent(OK);

        assertTrue(mController.isValid());
    }

    @Test
    public void testOnTryValidate() {
        assertFalse(mController.onTryValidate(new TutorialActivity()));
    }
}
