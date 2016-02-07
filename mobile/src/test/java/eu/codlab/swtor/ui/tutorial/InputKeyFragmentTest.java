package eu.codlab.swtor.ui.tutorial;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by kevinleperf on 07/02/16.
 */
public class InputKeyFragmentTest {

    private InputKeyFragment mFragment;

    @Before
    public void init() {
        mFragment = new InputKeyFragment();
    }

    @Test
    public void testOnTryValidate() {
        assertFalse(mFragment.onTryValidate());
    }
}
