package eu.codlab.utils;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Created by kevinleperf on 15/02/16.
 */
@Implements(com.google.android.gms.common.zzc.class)
public class PlayServicesShadowZZC {

    @Implementation
    public int isGooglePlayServicesAvailable(Context context) {
        return ConnectionResult.SERVICE_MISSING;
    }
}
