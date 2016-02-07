package eu.codlab.swtor.ui.tutorial;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.provider.Browser;
import android.test.InstrumentationTestCase;

import com.alexandrepiveteau.library.tutorial.ui.fragments.AbstractTutorialValidationFragment;
import com.alexandrepiveteau.library.tutorial.ui.fragments.TutorialFragment;

import org.junit.Before;
import org.junit.Test;

import eu.codlab.swtor.R;
import eu.codlab.swtor.utils.Constants;

/**
 * Created by kevinleperf on 06/02/16.
 */
public class TutorialActivityTest extends InstrumentationTestCase {
    private Instrumentation.ActivityMonitor mBrowserActivityMonitor;

    @Before
    public void init() {

        mBrowserActivityMonitor = new Instrumentation.ActivityMonitor(Browser.class.getName(), null, false);
        getInstrumentation().addMonitor(mBrowserActivityMonitor);
    }

    @Test
    public void testStartActivity() {
        TutorialActivity activity = new TutorialActivity();

        TutorialActivity.startAndFinish(activity);

        Activity newActivity = mBrowserActivityMonitor.waitForActivityWithTimeout(5 * 1000);
        assertNotNull("Activity was not started", newActivity);
        assertTrue(newActivity instanceof TutorialActivity);
        assertTrue(activity.isFinishing());
    }

    @Test
    public void testGetCount() throws NoSuchFieldException {
        TutorialActivity activity = new TutorialActivity();

        assertEquals(4, activity.getCount());
    }

    @Test
    public void testcreateTutorialFragment() {
        TutorialActivity activity = new TutorialActivity();

        for (int i = 0; i < Constants.title.length; i++) {
            TutorialFragment fragment = activity.createTutorialFragment(i);
            System.out.println("having " + i + " " + fragment);
            assertNotNull(fragment);
        }
        assertNull(activity.createTutorialFragment(Constants.title.length));
    }

    @Test
    public void testgetTutorialFragmentFor() {
        TutorialActivity activity = new TutorialActivity();

        AbstractTutorialValidationFragment fragment = null;

        for (int i = 0; i < 3 && i < Constants.title.length; i++) {
            fragment = activity.getTutorialFragmentFor(i);
            System.out.println("having " + i + " " + fragment);
            assertTrue(fragment instanceof TutorialFragment);
        }

        fragment = activity.getTutorialFragmentFor(3);
        assertTrue(fragment instanceof InputKeyFragment);
    }

    @Test
    public void testUI() {
        TutorialActivity activity = new TutorialActivity();

        assertFalse(activity.isStatusBarColored());
        assertFalse(activity.isNavigationBarColored());
        assertNotNull(activity.getPageTransformer());
    }

    @Test
    public void testGetBackgroundColor() {
        TutorialActivity activity = new TutorialActivity();

        for (int i = 0; i < Constants.background.length; i++) {
            assertEquals(Constants.background[i], activity.getBackgroundColor(i));
        }
    }

    @Test
    public void testGetStatusBarColor() {
        TutorialActivity activity = new TutorialActivity();

        for (int i = 0; i < activity.getCount(); i++) {
            assertEquals(R.color.colorPrimaryDark, activity.getStatusBarColor(i));
        }
    }

    @Test
    public void testGetNavigationBarColor() {
        TutorialActivity activity = new TutorialActivity();

        for (int i = 0; i < activity.getCount(); i++) {
            assertEquals(R.color.black, activity.getNavigationBarColor(i));
        }
    }

    @Test
    public void testGetIgnoreText() {
        TutorialActivity activity = new TutorialActivity();

        assertEquals(R.string.skip, activity.getIgnoreText());
    }

    @Test
    public void testCreateIntent() {
        TutorialActivity activity = new TutorialActivity();

        Intent intent = TutorialActivity.createIntent(activity);

        assertNotNull(intent);
    }
}
