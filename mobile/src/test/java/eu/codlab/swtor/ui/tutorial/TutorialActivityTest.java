package eu.codlab.swtor.ui.tutorial;

import org.junit.Test;

import eu.codlab.swtor.TestUtil;

/**
 * Created by kevinleperf on 06/02/16.
 */
public class TutorialActivityTest {

    @Test
    public void testArrays() throws NoSuchFieldException {

        TestUtil.assertArrayValid(TutorialActivity.class, "background", int[].class, true, true);
        TestUtil.assertArrayValid(TutorialActivity.class, "title", int[].class, true, true);
        TestUtil.assertArrayValid(TutorialActivity.class, "description", int[].class, true, true);
    }
}
