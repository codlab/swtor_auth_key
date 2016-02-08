package eu.codlab.swtor.internal.database.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class KeyTest {

    private Key mKey;

    @Before
    public void init() {
        mKey = new Key();
    }


    @Test
    public void testId() {
        long id = System.currentTimeMillis();

        mKey.setId(id);

        assertEquals(id, mKey.getId());
    }

    @Test
    public void testName() {
        String name = Long.toString(System.currentTimeMillis());

        mKey.setName(name);

        assertNotNull(mKey.getName());
        assertEquals(name, mKey.getName());
    }

    @Test
    public void testNameNull() {
        mKey.setName(null);
        assertNull(mKey.getName());
    }

    @Test
    public void testSecret() {
        String secret = Long.toString(System.currentTimeMillis());

        mKey.setSecret(secret);

        assertNotNull(mKey.getSecret());
        assertEquals(secret, mKey.getSecret());
    }

    @Test
    public void testSecretNull() {
        mKey.setSecret(null);
        assertNull(mKey.getSecret());
    }
}
