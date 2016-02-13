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

    /**
     * Test that asking for a specific value into the array, we obtain
     * the corresponding one
     */
    @Test
    public void testGetValue() {
        int[] array = new int[]{0, 1, 2, 3};
        ArrayHolder arrayHolder = new ArrayHolder(array);

        for (int i = 0; i < array.length; i++) {
            assertEquals(array[i], arrayHolder.getValue(i));
        }
    }

}
