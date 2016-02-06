package eu.codlab.swtor.internal.security;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by kevinleperf on 06/02/16.
 */
public class CodeProviderFactoryTest {

    @Test
    public void testGetCodeProviderWithNull(){
        String provider = null;

        CodeProvider codeProvider = CodeProviderFactory.getCodeProvider(provider);

        assertNull(codeProvider);
    }

    @Test
    public void testGetCodeProviderImproperLength(){
        String provider = "test";

        CodeProvider codeProvider = CodeProviderFactory.getCodeProvider(provider);

        assertNull(codeProvider);
    }

    @Test
    public void testGetCodeProviderValid(){
        String provider = "AZERTYUIOPAZERTY";

        CodeProvider codeProvider = CodeProviderFactory.getCodeProvider(provider);
        CodeProvider codeProvider2 = CodeProviderFactory.getCodeProvider(provider);

        assertNotNull(codeProvider);

        assertEquals(codeProvider, codeProvider2);
    }
}
