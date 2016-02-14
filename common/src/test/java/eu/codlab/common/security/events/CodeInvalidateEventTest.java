package eu.codlab.common.security.events;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Kévin on 05/02/2016.
 */
public class CodeInvalidateEventTest {

    @Test
    public void testCodeInvalidateContruction() {
        long time = System.currentTimeMillis();
        CodeInvalidateEvent event = new CodeInvalidateEvent(time);

        assertEquals(time, event.getNextIterationIn());
    }
}
