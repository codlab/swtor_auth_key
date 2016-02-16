package eu.codlab.swtor.ui.service;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import eu.codlab.swtor.internal.database.impl.Key;

import static org.junit.Assert.assertEquals;

/**
 * Created by kevinleperf on 16/02/16.
 */
public class SendMessageEventTest {

    private Key pKey;

    @Before
    public void setUp() {
        pKey = new Key();
    }

    @Test
    public void testConstructor() throws NoSuchFieldException, IllegalAccessException {
        SendMessageEvent event = new SendMessageEvent(pKey);

        Field mKey = event.getClass().getDeclaredField("mKey");
        mKey.setAccessible(true);

        assertEquals(pKey, mKey.get(event));
    }

    @Test
    public void testGetter() {
        SendMessageEvent event = new SendMessageEvent(pKey);

        assertEquals(pKey, event.getKey());
    }
}
