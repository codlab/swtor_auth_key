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
    private PasscodeGenerator mPasscodeGenerator;
    private Mac mLocalMac;
    private TimeProvider mTimeProvider;

    private CodeProvider() {

    }

    public CodeProvider(@NonNull String code, @NonNull TimeProvider time_provider) throws IllegalStateException {
        this();

        if(code == null) throw new IllegalStateException("code null");

        if(time_provider == null) throw new IllegalStateException("time null");

        try {
            mTimeProvider = time_provider;
            mLocalMac = Mac.getInstance(SecurityConstants.SECURITY);
            mLocalMac.init(new SecretKeySpec(Base32String.decode(code), ""));
            mPasscodeGenerator = new PasscodeGenerator(mLocalMac);
        } catch (Exception exception) {
            throw new IllegalStateException("EXCEPTION");
        }
    }

    @Nullable
    public String generateCode() {
        try {
            return mPasscodeGenerator.generateResponseCode(mTimeProvider.getCurrentInterval());
        } catch (Exception e) {
            if (BuildConfig.DEBUG) e.printStackTrace();
        }
        return null;
    }

}
