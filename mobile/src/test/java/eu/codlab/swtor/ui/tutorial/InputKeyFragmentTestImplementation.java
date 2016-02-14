package eu.codlab.swtor.ui.tutorial;

import android.text.Editable;
import android.text.InputFilter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.lang.reflect.Field;

import de.greenrobot.event.EventBus;
import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.security.TimeProvider;
import eu.codlab.swtor.BuildConfig;
import eu.codlab.swtor.GenerateCodeTest;
import eu.codlab.swtor.internal.tutorial.InputKeyController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 07/02/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class InputKeyFragmentTestImplementation {

    private TutorialActivity mActivity;
    private InputKeyFragment mFragment;
    private ShadowActivity shadowActivity;

    @Before
    public void setUp() throws Exception {

        mActivity = Robolectric.setupActivity(TutorialActivity.class);
        mFragment = new InputKeyFragment();
        SupportFragmentTestUtil.startFragment(mFragment, mActivity.getClass());
    }

    @Test
    public void testOnResume() {
        TimeProvider provider = DependencyInjectorFactory.getDependencyInjector().getTimeProvider();
        mFragment.onPause();

        assertFalse(provider.isResumed());
        assertTrue(provider.isPaused());

        System.out.println("state ? " + provider.isPaused() + " " + provider.isResumed() + " " + provider + " " + mFragment.getTimeProvider());
        mFragment.onResume();
        System.out.println("state ? " + provider.isPaused() + " " + provider.isResumed() + " " + provider + " " + mFragment.getTimeProvider());

        assertTrue(provider.isResumed());
        assertFalse(provider.isPaused());
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
        Field field = InputKeyFragment.class.getDeclaredField("mInputKeyController");
        assertNotNull(field);
        field.setAccessible(true);

        InputKeyController controller = (InputKeyController) field.get(mFragment);

        assertFalse(controller.isValid());
        assertFalse(mFragment.isValid());
    }

    @Test
    public void testIsValid() throws IllegalAccessException, NoSuchFieldException {
        Field field = InputKeyFragment.class.getDeclaredField("mInputKeyController");
        assertNotNull(field);
        field.setAccessible(true);

        String correct = GenerateCodeTest.CODE_OK;

        InputKeyController controller = (InputKeyController) field.get(mFragment);

        controller.setContent(correct);

        assertTrue(controller.isValid());
        assertTrue(mFragment.isValid());
    }

    @Test
    public void testOnTryValidate() {
        assertFalse(mFragment.onTryValidate());
    }

    @Test
    public void testAfterTextChanged() throws IllegalAccessException, NoSuchFieldException {
        Field field = InputKeyFragment.class.getDeclaredField("mInputKeyController");
        assertNotNull(field);
        field.setAccessible(true);

        InputKeyController controller = (InputKeyController) field.get(mFragment);

        String[] tests = new String[]{
                "",
                "TEST",
                "RANDOM",
                Long.toString(System.currentTimeMillis())
        };

        for (String test : tests) {
            Editable editable = createEditable(test);
            mFragment.afterTextChanged(editable);
            assertEquals(editable.toString(), controller.getContent());
        }
    }

    private Editable createEditable(final String content) {
        return new Editable() {

            @Override
            public String toString() {
                return content;
            }

            @Override
            public Editable replace(int st, int en, CharSequence source, int start, int end) {
                return null;
            }

            @Override
            public Editable replace(int st, int en, CharSequence text) {
                return null;
            }

            @Override
            public Editable insert(int where, CharSequence text, int start, int end) {
                return null;
            }

            @Override
            public Editable insert(int where, CharSequence text) {
                return null;
            }

            @Override
            public Editable delete(int st, int en) {
                return null;
            }

            @Override
            public Editable append(CharSequence text) {
                return null;
            }

            @Override
            public Editable append(CharSequence text, int start, int end) {
                return null;
            }

            @Override
            public Editable append(char text) {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public void clearSpans() {

            }

            @Override
            public void setFilters(InputFilter[] filters) {

            }

            @Override
            public InputFilter[] getFilters() {
                return new InputFilter[0];
            }

            @Override
            public void getChars(int start, int end, char[] dest, int destoff) {

            }

            @Override
            public void setSpan(Object what, int start, int end, int flags) {

            }

            @Override
            public void removeSpan(Object what) {

            }

            @Override
            public <T> T[] getSpans(int start, int end, Class<T> type) {
                return null;
            }

            @Override
            public int getSpanStart(Object tag) {
                return 0;
            }

            @Override
            public int getSpanEnd(Object tag) {
                return 0;
            }

            @Override
            public int getSpanFlags(Object tag) {
                return 0;
            }

            @Override
            public int nextSpanTransition(int start, int limit, Class type) {
                return 0;
            }

            @Override
            public int length() {
                return 0;
            }

            @Override
            public char charAt(int index) {
                return 0;
            }

            @Override
            public CharSequence subSequence(int start, int end) {
                return null;
            }
        };
    }
}
