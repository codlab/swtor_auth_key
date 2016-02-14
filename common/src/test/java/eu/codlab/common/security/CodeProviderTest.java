package eu.codlab.common.security;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Kévin on 05/02/2016.
 */
public class CodeProviderTest {
    @Test
    public void testCodeProviderConstructionCodeNull() {
        TimeProvider time_provider = new TimeProvider();
        boolean ok = true;

        try {
            CodeProvider provider = new CodeProvider(null, time_provider);
        } catch (Exception e) {
            ok = false;
        }

        assertFalse(ok);
    }

    @Test
    public void testCodeProviderConstructionTimeProviderNull() {
        boolean ok = true;

        try {
            CodeProvider provider = new CodeProvider("", null);
        } catch (Exception e) {
            ok = false;
        }

        assertFalse(ok);
    }

    @Test
    public void testCodeProviderConstructionAllNull() {
        boolean ok = true;

        try {
            CodeProvider provider = new CodeProvider(null, null);
        } catch (Exception e) {
            ok = false;
        }

        assertFalse(ok);
    }

    @Test
    public void testCodeProviderConstructionOk() {
        boolean ok = true;

        try {
            CodeProvider provider = new CodeProvider("test", new TimeProvider());
        } catch (Exception e) {
            ok = false;
        }

        assertTrue(ok);
    }

    @Test
    public void testCodeProviderGenerateOk() {
        CodeProvider provider = new CodeProvider("test", new TimeProvider());
        String generated = provider.generateCode();

        assertNotNull(generated);
        assertEquals(6, generated.length());
    }
}
