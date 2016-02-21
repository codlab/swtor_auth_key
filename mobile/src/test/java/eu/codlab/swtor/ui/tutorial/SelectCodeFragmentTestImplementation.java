package eu.codlab.swtor.ui.tutorial;

import android.content.Intent;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.lang.reflect.Field;

import de.greenrobot.event.EventBus;
import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.security.TimeProvider;
import eu.codlab.swtor.BuildConfig;
import eu.codlab.swtor.GenerateCodeTest;
import eu.codlab.swtor.internal.database.events.DatabaseEvent;
import eu.codlab.swtor.internal.database.impl.Key;
import eu.codlab.swtor.internal.injector.DependencyStandardInjector;
import eu.codlab.swtor.internal.tutorial.InputKeyController;
import eu.codlab.swtor.ui.main.ShowCodeActivity;
import eu.codlab.test.TestUtil;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 07/02/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class SelectCodeFragmentTestImplementation {

    private TutorialActivity mActivity;
    private SelectedKeyFragment mFragment;
    private ShadowActivity mShadowActivity;

    @Before
    public void setUp() throws Exception {

        mActivity = Robolectric.setupActivity(TutorialActivity.class);
        mFragment = new SelectedKeyFragment();
        SupportFragmentTestUtil.startFragment(mFragment, mActivity.getClass());

        mShadowActivity = (ShadowActivity) ShadowExtractor.extract(mActivity);
    }

    @Test
    public void testOnResume() {
        TimeProvider provider = DependencyInjectorFactory.getDependencyInjector().getTimeProvider();
        EventBus bus = DependencyInjectorFactory.getDependencyInjector().getDefaultEventBus();

        mFragment.onPause();

        assertFalse(provider.isResumed());
        assertTrue(provider.isPaused());

        assertFalse(bus.isRegistered(mFragment));

        System.out.println("state ? " + provider.isPaused() + " " + provider.isResumed() + " " + provider + " " + mFragment.getTimeProvider());
        mFragment.onResume();
        System.out.println("state ? " + provider.isPaused() + " " + provider.isResumed() + " " + provider + " " + mFragment.getTimeProvider());

        assertTrue(provider.isResumed());
        assertFalse(provider.isPaused());

        assertTrue(bus.isRegistered(mFragment));
    }

    @Test
    public void testOnPause() {
        //make sure we are paused, we will be resumed soon after to finally test the pause state
        //being consistent here with any previous calls from other test methods
        mFragment.onPause();

        TimeProvider provider = DependencyInjectorFactory.getDependencyInjector().getTimeProvider();
        EventBus bus = DependencyInjectorFactory.getDependencyInjector().getDefaultEventBus();

        provider.onResume();

        assertTrue(provider.isResumed());
        assertFalse(provider.isPaused());

        assertFalse(bus.isRegistered(mFragment));

        mFragment.onPause();

        assertFalse(provider.isResumed());
        assertTrue(provider.isPaused());

        assertFalse(bus.isRegistered(mFragment));
    }

    @Test
    public void testIsNotValid() throws IllegalAccessException, NoSuchFieldException {
        Field field = SelectedKeyFragment.class.getDeclaredField("mInputKeyController");
        assertNotNull(field);
        field.setAccessible(true);

        InputKeyController controller = (InputKeyController) field.get(mFragment);

        assertFalse(controller.isValid());
        assertFalse(mFragment.isValid());
    }

    @Test
    public void testIsValid() throws IllegalAccessException, NoSuchFieldException {
        Field field = SelectedKeyFragment.class.getDeclaredField("mInputKeyController");
        assertNotNull(field);
        field.setAccessible(true);

        String correct = GenerateCodeTest.CODE_OK;

        InputKeyController controller = (InputKeyController) field.get(mFragment);

        controller.setContent(correct);

        assertTrue(controller.isValid());
        assertTrue(mFragment.isValid());
    }

    @Test
    public void testOnTryValidate() throws NoSuchFieldException, IllegalAccessException {
        testIsValid();

        DependencyInjectorFactory.init(mActivity, new DependencyStandardInjector());
        FlowManager.init(mActivity);

        TestUtil.purgeShadowActivityStartActivity(mShadowActivity);
        assertFalse(mFragment.onTryValidate(mActivity));

        assertTrue(mFragment.isValid());

        Intent startedIntent = mShadowActivity.getNextStartedActivity();

        assertNotNull(startedIntent);
        assertThat(startedIntent.getComponent().getClassName(),
                equalTo(ShowCodeActivity.class.getName()));

        FlowManager.destroy();
        DependencyInjectorFactory.flush();
    }

    @Test
    public void testOnTryValidateFail() throws NoSuchFieldException, IllegalAccessException {
        testIsNotValid();

        TestUtil.purgeShadowActivityStartActivity(mShadowActivity);
        assertFalse(mFragment.onTryValidate(mActivity));

        assertFalse(mFragment.isValid());

        Intent startedIntent = mShadowActivity.getNextStartedActivity();

        assertNull(startedIntent);
    }

    @Test
    public void testDatabaseEvent() throws IllegalAccessException, NoSuchFieldException {
        Field field = mFragment.getClass().getDeclaredField("mInputKeyController");
        assertNotNull(field);
        field.setAccessible(true);

        InputKeyController controller = (InputKeyController) field.get(mFragment);

        Key key = new Key();
        key.setSecret(GenerateCodeTest.CODE_OK);

        mFragment.onEvent(new DatabaseEvent(key));

        assertEquals(key.getSecret(), controller.getContent());
    }

    @Test
    public void testDatabaseEventNot() throws IllegalAccessException, NoSuchFieldException {
        Field field = mFragment.getClass().getDeclaredField("mInputKeyController");
        assertNotNull(field);
        field.setAccessible(true);

        InputKeyController controller = (InputKeyController) field.get(mFragment);

        String previous = controller.getContent();

        mFragment.onEvent(new DatabaseEvent(null));

        assertEquals(previous, controller.getContent());
    }

}
