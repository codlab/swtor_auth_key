package eu.codlab.swtor.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import eu.codlab.intercom.AbstractWearListenerService;
import eu.codlab.swtor.utils.Constants;

/**
 * Created by kevinleperf on 15/02/16.
 */
public class MobileKeyListenerService extends AbstractWearListenerService {

    public MobileKeyListenerService() {
        super();
    }

    @Override
    public boolean isManaged(@NonNull String path) {
        return Constants.KEY_SEND_WEAR.equals(path);
    }

    @Override
    public void onNewMessage(@NonNull String path, @NonNull Bundle bundle) {
        Intent intent = new Intent(this, NotificationKeyService.class);
        intent.putExtras(bundle);

        startService(intent);
    }
}
