package eu.codlab.swtor.ui.tutorial;

import android.app.Instrumentation;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowActivity;

import eu.codlab.swtor.BuildConfig;
import eu.codlab.swtor.ui.splash.LoadingActivity;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 06/02/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class TutorialActivityTestInstrument {
    private Instrumentation.ActivityMonitor mBrowserActivityMonitor;

    private ShadowActivity shadowActivity;
    private LoadingActivity mActivity;

    @Before
    public void setup() {
        mActivity = new LoadingActivity();
        shadowActivity = (ShadowActivity) ShadowExtractor.extract(mActivity);
    }

    @Test
    public void testStartActivity() {
        TutorialActivity.startAndFinish(mActivity);

        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertThat(startedIntent.getComponent().getClassName(),
                equalTo(TutorialActivity.class.getName()));

        assertTrue(mActivity.isFinishing());
    }
}
