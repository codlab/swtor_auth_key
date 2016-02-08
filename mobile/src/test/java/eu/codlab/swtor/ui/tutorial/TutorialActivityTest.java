package eu.codlab.swtor.ui.tutorial;

import android.content.Intent;

import com.alexandrepiveteau.library.tutorial.ui.fragments.AbstractTutorialValidationFragment;
import com.alexandrepiveteau.library.tutorial.ui.fragments.TutorialFragment;

import org.junit.Test;

import eu.codlab.swtor.R;
import eu.codlab.swtor.utils.Constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 06/02/16.
 */
public class TutorialActivityTest {

    @Test
    public void testGetCount() throws NoSuchFieldException {
        TutorialActivity activity = new TutorialActivity();

        assertEquals(4, activity.getCount());
    }

    @Test
    public void testcreateTutorialFragment() {
        TutorialActivity activity = new TutorialActivity();

        for (int i = 0; i < Constants.TITLE.length; i++) {
            TutorialFragment fragment = activity.createTutorialFragment(i);
            System.out.println("having " + i + " " + fragment);
            assertNotNull(fragment);
        }
        assertNull(activity.createTutorialFragment(Constants.TITLE.length));
    }

    @Test
    public void testgetTutorialFragmentFor() {
        TutorialActivity activity = new TutorialActivity();

        AbstractTutorialValidationFragment fragment = null;

        for (int i = 0; i < 3 && i < Constants.TITLE.length; i++) {
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

        for (int i = 0; i < Constants.BACKGROUND.length; i++) {
            assertEquals(Constants.BACKGROUND[i], activity.getBackgroundColor(i));
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
