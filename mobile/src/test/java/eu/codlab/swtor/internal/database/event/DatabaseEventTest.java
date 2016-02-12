package eu.codlab.swtor.internal.database.event;

import org.junit.Test;

import java.lang.reflect.Field;

import eu.codlab.swtor.internal.database.events.DatabaseEvent;
import eu.codlab.swtor.internal.database.impl.Key;

import static org.junit.Assert.assertEquals;

/**
 * Created by kevinleperf on 10/02/16.
 */
public class DatabaseEventTest {

    @Test
    public void testConstuctor() throws NoSuchFieldException, IllegalAccessException {
        Key key = new Key();

        DatabaseEvent event = new DatabaseEvent(key);

        Field field = event.getClass().getDeclaredField("mKey");
        field.setAccessible(true);

        assertEquals(key, field.get(event));
    }

    @Test
    public void testGetter() {
        Key key = new Key();

        DatabaseEvent event = new DatabaseEvent(key);

        assertEquals(key, event.getKey());
    }
}
