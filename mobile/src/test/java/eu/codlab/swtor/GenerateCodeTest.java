package eu.codlab.swtor;

import org.junit.Test;

import eu.codlab.swtor.internal.security.CodeProvider;
import eu.codlab.swtor.internal.security.CodeProviderFactory;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class GenerateCodeTest {
    public final static String CODE_OK = "RULAB3CHR3KDHFC3";
    private final static String CODE_ERROR = "RULAB3CHR3KDHFC";

    @Test
    public void assertCodeProviderCanBeGenerated() throws Exception {

        CodeProvider provider = CodeProviderFactory.getCodeProvider(CODE_OK);
        assertNotEquals(null, provider);
    }

    @Test
    public void assertCodeProviderGeneratedIsOK() throws Exception {
        CodeProvider provider = CodeProviderFactory.getCodeProvider(CODE_OK);
        assertNotEquals(null, provider);

        String generated = provider.generateCode();

        assertNotNull(generated);
    }

    @Test
    public void assertCodeProviderCanNotBeGenerated() throws Exception {
        CodeProvider provider = CodeProviderFactory.getCodeProvider(CODE_ERROR);
        assertNull(provider);
    }
}