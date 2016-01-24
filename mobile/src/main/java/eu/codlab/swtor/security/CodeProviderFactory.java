package eu.codlab.swtor.security;

import android.support.annotation.Nullable;

import com.bioware.android.apps.authenticator.Base32String;

import java.util.HashMap;

/**
 * Created by kevinleperf on 15/01/16.
 */
public class CodeProviderFactory {
    private final static int LENGTH_TOTAL = 16;

    private final static HashMap<String, CodeProvider> _providers = new HashMap<>();


    @Nullable
    public static CodeProvider getCodeProvider(@Nullable String provider) {
        if (provider == null) return null;

        if (_providers.containsKey(provider)) {
            return _providers.get(provider);
        }

        try {
            if (null == Base32String.decode(provider) || provider.length() != LENGTH_TOTAL)
                return null;

            CodeProvider prov = new CodeProvider(provider);
            _providers.put(provider, prov);
            return prov;
        } catch (Exception exception) {
            System.out.println("CodeProviderFactory :: issue " + exception.getMessage());
            exception.printStackTrace();
        }

        return null;
    }
}
