package eu.codlab.swtor.service;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.security.TimeProvider;
import eu.codlab.common.security.events.CodeInvalidateEvent;
import eu.codlab.swtor.R;
import eu.codlab.swtor.internal.database.events.DatabaseEvent;
import eu.codlab.swtor.internal.database.impl.Key;
import eu.codlab.swtor.internal.tutorial.InputKeyController;
import eu.codlab.swtor.utils.Constants;

/**
 * Created by kevinleperf on 15/02/16.
 */
public class NotificationKeyService extends Service {
    private int mNotificationId;
    private TimeProvider mTimeProvider;
    private EventBus mEventBus;
    private InputKeyController mInputKeyController;

    public static void start(Activity parent) {
        Intent intent = new Intent(parent, NotificationKeyService.class);

        parent.startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mNotificationId = (int) System.currentTimeMillis();

        mInputKeyController = new InputKeyController();

        mEventBus = DependencyInjectorFactory
                .getDependencyInjector()
                .getDefaultEventBus();

        mEventBus.register(this);

        mTimeProvider = new TimeProvider();

        initInputKeyController();

        initKey();

        updateNotification();

        mTimeProvider.onResume();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null
                && intent.hasExtra(Constants.KEY_SEND_KEY_NAME)
                && intent.hasExtra(Constants.KEY_SEND_KEY_SECRET)) {

            Key key = new Key();

            key.setName(intent.getStringExtra(Constants.KEY_SEND_KEY_NAME));
            key.setSecret(intent.getStringExtra(Constants.KEY_SEND_KEY_SECRET));

            DatabaseEvent event = new DatabaseEvent(key);
            mEventBus.postSticky(event);

            intent.removeExtra(Constants.KEY_SEND_KEY_NAME);
            intent.removeExtra(Constants.KEY_SEND_KEY_SECRET);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mTimeProvider.onPause();

        mEventBus.unregister(this);
        mEventBus = null;

        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onEvent(DatabaseEvent event) {
        Key key = event.getKey();
        if (key != null) {
            DependencyInjectorFactory
                    .getDependencyInjector()
                    .getDatabaseProvider()
                    .updateKey(key);

            mInputKeyController.setContent(key.getSecret());
            updateNotification();
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onEvent(CodeInvalidateEvent event) {
        updateNotification();
    }

    private void initKey() {
        Key key = DependencyInjectorFactory
                .getDependencyInjector()
                .getDatabaseProvider()
                .getLastKey();

        onEvent(new DatabaseEvent(key));
    }

    private void updateNotification() {
        if (mInputKeyController.isValid()) {
            foreground(mInputKeyController.generateCode());
        }
    }

    private void initInputKeyController() {
        Key key = DependencyInjectorFactory
                .getDependencyInjector()
                .getDatabaseProvider()
                .getLastKey();
        if (key != null) {
            mInputKeyController.setContent(key.getSecret());
        }
    }

    private Notification createNotification(String code) {
        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(code)
                .setContentIntent(PendingIntent.getBroadcast(this, mNotificationId, new Intent(),
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .build();
    }

    private void foreground(String code) {
        startForeground(mNotificationId, createNotification(code));
    }

}
