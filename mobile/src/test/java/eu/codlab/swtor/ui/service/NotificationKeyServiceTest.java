package eu.codlab.swtor.ui.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowNotificationManager;
import org.robolectric.shadows.gms.ShadowGooglePlayServicesUtil;
import org.robolectric.util.ServiceController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.greenrobot.event.EventBus;
import eu.codlab.common.dependency.DependencyInjector;
import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.dependency.listeners.IDatabaseProvider;
import eu.codlab.common.security.TimeProvider;
import eu.codlab.common.security.events.CodeInvalidateEvent;
import eu.codlab.swtor.BuildConfig;
import eu.codlab.swtor.GenerateCodeTest;
import eu.codlab.swtor.internal.database.events.DatabaseEvent;
import eu.codlab.swtor.internal.database.impl.Key;
import eu.codlab.swtor.internal.database.provider.DatabaseProvider;
import eu.codlab.swtor.internal.injector.DependencyStandardInjector;
import eu.codlab.swtor.internal.tutorial.InputKeyController;
import eu.codlab.utils.PlayServicesShadowZZC;
import eu.codlab.utils.PlayServicesShadowZZJ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 10/02/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 17, constants = BuildConfig.class,
        shadows = {PlayServicesShadowZZC.class, PlayServicesShadowZZJ.class})
public class NotificationKeyServiceTest {

    private ServiceController<NotificationKeyService> mServiceController;
    private NotificationKeyService mNotificationKeyService;
    private NotificationManager mNotificationManager;
    private ShadowNotificationManager mShadowNotificationManager;

    @Before
    public void setUp() {
        ShadowGooglePlayServicesUtil.setIsGooglePlayServicesAvailable(ConnectionResult.SUCCESS);
        mServiceController = Robolectric.buildService(NotificationKeyService.class);
        mNotificationKeyService = mServiceController.create().get();

        mNotificationManager = (NotificationManager) RuntimeEnvironment.application
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mShadowNotificationManager = (ShadowNotificationManager) ShadowExtractor.extract(mNotificationManager);

        mShadowNotificationManager.cancelAll();
    }

    @After
    public void tearDown() {
        if (mNotificationKeyService != null)
            mNotificationKeyService.stopSelf();
    }

    @Test
    public void testBindResul() {
        IBinder binder = mNotificationKeyService.onBind(new Intent());

        assertNull(binder);

    }

    @Test
    public void testCreateNotification() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Notification notification;

        Method method = mNotificationKeyService.getClass().getDeclaredMethod("createNotification", String.class);
        method.setAccessible(true);
        notification = (Notification) method.invoke(mNotificationKeyService, "");

        assertNotNull(notification);
    }

    @Test
    public void testInitInputKeyControllerNull() throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Method method = mNotificationKeyService.getClass().getDeclaredMethod("initInputKeyController");
        method.setAccessible(true);
        method.invoke(mNotificationKeyService);


        Field inputKeyController = mNotificationKeyService.getClass().getDeclaredField("mInputKeyController");
        inputKeyController.setAccessible(true);
        InputKeyController controller = (InputKeyController) inputKeyController.get(mNotificationKeyService);

        assertNotNull(controller);
        assertNull(controller.getContent());
    }

    @Test
    public void testInitInputKeyControllerOk() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        final Key key = new Key();
        key.setSecret("test");

        final DatabaseProvider provider = new DatabaseProvider() {
            @Override
            public Key getLastKey() {
                return key;
            }
        };

        DependencyInjector injector = new DependencyStandardInjector() {
            @NonNull
            @Override
            public IDatabaseProvider getDatabaseProvider() {
                return provider;
            }
        };

        DependencyInjectorFactory.init(mNotificationKeyService, injector);


        Method method = mNotificationKeyService.getClass().getDeclaredMethod("initInputKeyController");
        method.setAccessible(true);
        method.invoke(mNotificationKeyService);


        Field inputKeyController = mNotificationKeyService.getClass().getDeclaredField("mInputKeyController");
        inputKeyController.setAccessible(true);
        InputKeyController controller = (InputKeyController) inputKeyController.get(mNotificationKeyService);

        assertNotNull(controller);
        assertEquals(key.getSecret(), controller.getContent());
    }

    @Test
    public void testOnEventCodeInvalidateEventValid() throws NoSuchFieldException, IllegalAccessException {
        String expectedString = GenerateCodeTest.CODE_OK;

        Field inputKeyController = mNotificationKeyService.getClass().getDeclaredField("mInputKeyController");
        inputKeyController.setAccessible(true);
        InputKeyController controller = (InputKeyController) inputKeyController.get(mNotificationKeyService);

        controller.setContent(expectedString);


        mNotificationKeyService.onEvent(new CodeInvalidateEvent(1));
        assertTrue(controller.isValid());
        assertEquals(1, mShadowNotificationManager.size());
    }

    @Test
    public void testOnEventCodeInvalidateEventInvalid() throws NoSuchFieldException, IllegalAccessException {
        String expectedString = "test";

        Field inputKeyController = mNotificationKeyService.getClass().getDeclaredField("mInputKeyController");
        inputKeyController.setAccessible(true);
        InputKeyController controller = (InputKeyController) inputKeyController.get(mNotificationKeyService);

        controller.setContent(expectedString);


        mNotificationKeyService.onEvent(new CodeInvalidateEvent(1));
        assertFalse(controller.isValid());
        assertEquals(0, mShadowNotificationManager.size());
    }

    @Test
    public void testOnEventDatabaseNull() throws NoSuchFieldException, IllegalAccessException {
        String expectedString = "test";

        Field inputKeyController = mNotificationKeyService.getClass().getDeclaredField("mInputKeyController");
        inputKeyController.setAccessible(true);
        InputKeyController controller = (InputKeyController) inputKeyController.get(mNotificationKeyService);

        controller.setContent(expectedString);

        DatabaseEvent event = new DatabaseEvent(null);

        mNotificationKeyService.onEvent(event);

        assertNotNull(controller.getContent());
        assertEquals(expectedString, controller.getContent());
    }

    @Test
    public void testForegroundWithNotification() {
        Key key = new Key();
        key.setSecret(GenerateCodeTest.CODE_OK);

        DatabaseEvent event = new DatabaseEvent(key);
        mNotificationKeyService.onEvent(event);

        assertEquals(1, mShadowNotificationManager.size());
    }

    @Test
    public void testForegroundWithoutNotification() {
        DatabaseEvent event = new DatabaseEvent(null);

        mNotificationKeyService.onEvent(event);

        assertEquals(0, mShadowNotificationManager.size());
    }

    @Test
    public void testOnDestroy() throws NoSuchFieldException, IllegalAccessException {
        mNotificationKeyService = mServiceController.destroy().get();

        Field timeProvider = mNotificationKeyService.getClass().getDeclaredField("mTimeProvider");
        timeProvider.setAccessible(true);
        TimeProvider provider = (TimeProvider) timeProvider.get(mNotificationKeyService);

        Field eventBus = mNotificationKeyService.getClass().getDeclaredField("mEventBus");
        eventBus.setAccessible(true);
        EventBus mEventBus = (EventBus) eventBus.get(mNotificationKeyService);

        assertTrue(provider.isPaused());
        assertFalse(provider.isResumed());

        assertNull(mEventBus);
    }

    @Test
    public void testInitKey() throws NoSuchFieldException, IllegalAccessException {
        testOnEventDatabaseNull();
    }
}
