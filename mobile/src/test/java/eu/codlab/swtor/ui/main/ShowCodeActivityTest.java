package eu.codlab.swtor.ui.main;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.alexandrepiveteau.library.tutorial.ui.fragments.AbstractTutorialValidationFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowActivity;

import eu.codlab.common.dependency.DependencyInjector;
import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.dependency.listeners.IDatabaseProvider;
import eu.codlab.swtor.BuildConfig;
import eu.codlab.swtor.internal.database.impl.Key;
import eu.codlab.swtor.internal.database.provider.DatabaseProvider;
import eu.codlab.swtor.internal.injector.DependencyStandardInjector;
import eu.codlab.swtor.ui.tutorial.TutorialActivity;
import eu.codlab.test.TestUtil;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by kevinleperf on 09/02/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class ShowCodeActivityTest {

    private Key mKey;
    private DatabaseProvider mProvider;

    private ShowCodeActivity mActivity;
    private ShadowActivity mShadowActivity;

    @Before
    public void setUp() throws Exception {
        //inject a key element in the database
        mKey = new Key();
        mKey.setName("");
        mKey.setSecret("");

        mProvider = new DatabaseProvider() {
            @Override
            public Key getLastKey() {
                return mKey;
            }
        };

        DependencyInjector injector = new DependencyStandardInjector() {
            @NonNull
            @Override
            public IDatabaseProvider getDatabaseProvider() {
                return mProvider;
            }
        };

        DependencyInjectorFactory.init(mActivity, injector);

        mActivity = Robolectric.setupActivity(ShowCodeActivity.class);

        mShadowActivity = (ShadowActivity) ShadowExtractor.extract(mActivity);

    }

    @Test
    public void testOnClickActionAdd() {
        TestUtil.purgeShadowActivityStartActivity(mShadowActivity);

        mActivity.onClickActionAdd();

        Intent startedIntent = mShadowActivity.getNextStartedActivity();

        assertNotNull(startedIntent);
        assertThat(startedIntent.getComponent().getClassName(),
                equalTo(TutorialActivity.class.getName()));
    }

    @Test
    public void testOnTryValidate() {
        boolean ok = true;

        try {
            AbstractTutorialValidationFragment fragment = new AbstractTutorialValidationFragment() {
                @Override
                public boolean isValid() {
                    return false;
                }

                @Override
                public boolean onTryValidate(com.alexandrepiveteau.library.tutorial.ui.activities.TutorialActivity parent) {
                    return false;
                }
            };

            mActivity.onValidate(fragment, false);
        } catch (NoSuchMethodError error) {
            ok = false;
        }

        assertFalse(ok);
    }
}
