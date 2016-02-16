package eu.codlab.swtor.internal.tutorial;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.swtor.internal.tutorial.non_test.DependencyInjectorImplementationWithCodeProviderNull;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Kévin on 05/02/2016.
 */
public class InputKeyControllerTestWithCodeProviderNull {
    public final String OK = "RULAB3CHR3KDHFC3";
    public Context _context;

    private InputKeyController mController;

    @Before
    public void init() {
        _context = Mockito.mock(Context.class);

        DependencyInjectorFactory.init(_context, new DependencyInjectorImplementationWithCodeProviderNull());

        mController = new InputKeyController();
        mController.setContent(null);
    }

    @Test
    public void testGenerateCodeNull() {
        String code = "";
        mController.setContent(code);
        String generated = mController.generateCode();

        assertNull(generated);
        assertNull(DependencyInjectorFactory.getDependencyInjector().getCodeProvider(code));
    }

    @Test
    public void testValidNext() {
        String code = "";
        mController.setContent(code);

        assertNotNull(mController.getContent());
        assertEquals(mController.getContent(), code);

        boolean valid = mController.isValid();

        assertFalse(valid);
        assertNull(mController.generateCode());
    }

    @Test
    public void testValidImproperContentLength() {
        String content = "";
        boolean valid = mController.isValid();

        assertNotEquals(16, content.length());
        assertFalse(valid);
    }
}
