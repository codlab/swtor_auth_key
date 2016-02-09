package eu.codlab.swtor.utils;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class ArrayHolderTest {
    @Test
    public void testConstructor() {
        int[] array = new int[4];
        ArrayHolder arrayHolder = new ArrayHolder(array);

        assertEquals(array.length, arrayHolder.getSize());
    }
}
