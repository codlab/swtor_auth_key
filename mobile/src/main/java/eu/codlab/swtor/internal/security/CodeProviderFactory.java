package eu.codlab.swtor.internal.security;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bioware.android.apps.authenticator.Base32String;

import java.util.HashMap;

/**
 * Created by kevinleperf on 15/01/16.
 */
public final class CodeProviderFactory {
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
    public static CodeProvider getCodeProvider(@NonNull String provider) {
        if (!isCorrectArray(provider) || !isCorrectLength(provider))
            return null;

        if (mProviders.containsKey(provider)) {
            return mProviders.get(provider);
        }

        CodeProvider prov = new CodeProvider(provider, new TimeProvider());
        mProviders.put(provider, prov);
        return prov;
    }

    protected static boolean isCorrectArray(@NonNull String provider) {
        return decode(provider).length > 0;
    }

    protected static boolean isCorrectLength(@NonNull String provider) {
        return provider.length() == LENGTH_TOTAL;
    }

    @NonNull
    protected static byte[] decode(@NonNull String provider) {
        try {
            return Base32String.decode(provider);
        } catch (Exception exception) {
            return new byte[]{};
        }
    }
}
