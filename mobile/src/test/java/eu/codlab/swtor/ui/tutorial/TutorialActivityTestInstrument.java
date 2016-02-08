package eu.codlab.swtor.ui.tutorial;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.provider.Browser;
import android.test.InstrumentationTestCase;

import com.alexandrepiveteau.library.tutorial.ui.fragments.AbstractTutorialValidationFragment;
import com.alexandrepiveteau.library.tutorial.ui.fragments.TutorialFragment;

import org.junit.Test;

import eu.codlab.swtor.R;
import eu.codlab.swtor.utils.Constants;

/**
 * Created by kevinleperf on 06/02/16.
 */
public class TutorialActivityTestInstrument extends InstrumentationTestCase {
    private Instrumentation.ActivityMonitor mBrowserActivityMonitor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mBrowserActivityMonitor = new Instrumentation.ActivityMonitor(Browser.class.getName(), null, false);
    }

    @Test
    public void testStartActivity() {
        getInstrumentation().addMonitor(mBrowserActivityMonitor);

        TutorialActivity activity = new TutorialActivity();

        TutorialActivity.startAndFinish(activity);

        Activity newActivity = mBrowserActivityMonitor.waitForActivityWithTimeout(5 * 1000);
        assertNotNull("Activity was not started", newActivity);
        assertTrue(newActivity instanceof TutorialActivity);
        assertTrue(activity.isFinishing());
    }
}
