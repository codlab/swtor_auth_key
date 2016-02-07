package eu.codlab.swtor.internal.tutorial;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.tutorial.non_test.DependencyInjectorImplementationWithCodeProviderNull;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Kévin on 05/02/2016.
 */
public class InputKeyControllerTestWithCodeProviderNull {
    public final String OK = "RULAB3CHR3KDHFC3";
    public Context _context;

    @Before
    public void init() {
        _context = Mockito.mock(Context.class);

        DependencyInjectorFactory.init(_context, new DependencyInjectorImplementationWithCodeProviderNull());
    }

    @Test
    public void testGenerateCodeNull() {
        InputKeyController controller = new InputKeyController();

        String code = "";
        controller.setContent(code);
        String generated = controller.generateCode();

        assertNull(generated);
        assertNull(DependencyInjectorFactory.getDependencyInjector().getCodeProvider(code));
    }
}
