package eu.codlab.swtor.internal.security;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bioware.android.apps.authenticator.Base32String;
import com.bioware.android.apps.authenticator.BuildConfig;
import com.bioware.android.apps.authenticator.PasscodeGenerator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by kevinleperf on 15/01/16.
 */
public class CodeProvider {
    private PasscodeGenerator _passcode_generator;
    private Mac _local_mac;
    private TimeProvider _time_provider;

    private CodeProvider() {

    }

    public CodeProvider(@NonNull String code, @NonNull TimeProvider time_provider) throws IllegalStateException {
        this();

        if(code == null) throw new IllegalStateException("code null");

        if(time_provider == null) throw new IllegalStateException("time null");

        try {
            _time_provider = time_provider;
            _local_mac = Mac.getInstance(SecurityConstants.SECURITY);
            _local_mac.init(new SecretKeySpec(Base32String.decode(code), ""));
            _passcode_generator = new PasscodeGenerator(_local_mac);
        } catch (Exception exception) {
            throw new IllegalStateException("EXCEPTION");
        }
    }

    @Nullable
    public String generateCode() {
        try {
            return _passcode_generator.generateResponseCode(_time_provider.getCurrentInterval());
        } catch (Exception e) {
            if (BuildConfig.DEBUG) e.printStackTrace();
        }
        return null;
    }

}
