package eu.codlab.swtor.internal.security;

import android.support.annotation.Nullable;
import android.util.Log;

import com.bioware.android.apps.authenticator.Base32String;

import java.util.HashMap;

import eu.codlab.swtor.BuildConfig;

/**
 * Created by kevinleperf on 15/01/16.
 */
public class CodeProviderFactory {
    private static final int LENGTH_TOTAL = 16;

    private static final HashMap<String, CodeProvider> mProviders = new HashMap<>();

    /**
     * Private constructor
     * <p/>
     * the class can not be instantiated
     */
    private CodeProviderFactory() {

    }

    /**
     * Provide the given Code Generator through a specific key
     *
     * @param provider a nullable key
     * @return a proper CodeProvider or null if something wrong happened
     */
    @Nullable
    public static CodeProvider getCodeProvider(@Nullable String provider) {
        if (null == decode(provider) || provider.length() != LENGTH_TOTAL)
            return null;

        if (mProviders.containsKey(provider)) {
            return mProviders.get(provider);
        }

        try {
            CodeProvider prov = new CodeProvider(provider, new TimeProvider());
            mProviders.put(provider, prov);
            return prov;
        } catch (Exception exception) {
            if (BuildConfig.DEBUG) {
                Log.e(CodeProviderFactory.class.getSimpleName(),
                        "Error with CodeProvider ", exception);
            }
        }

        return null;
    }

    private static byte[] decode(@Nullable String provider) {
        if (null == provider)
            return null;

        try {
            return Base32String.decode(provider);
        } catch (Base32String.DecodingException exception) {
            if (BuildConfig.DEBUG) {
                Log.e(CodeProviderFactory.class.getSimpleName(),
                        "exception",
                        exception);
            }
        }
        return null;
    }
}
