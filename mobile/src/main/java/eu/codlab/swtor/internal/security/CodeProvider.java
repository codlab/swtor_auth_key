package eu.codlab.swtor.internal.security;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bioware.android.apps.authenticator.Base32String;
import com.bioware.android.apps.authenticator.PasscodeGenerator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import eu.codlab.swtor.BuildConfig;

/**
 * Created by kevinleperf on 15/01/16.
 */
public class CodeProvider {
    private PasscodeGenerator mPasscodeGenerator;
    private Mac mLocalMac;
    private TimeProvider mTimeProvider;

    private CodeProvider() {

    }

    public CodeProvider(@NonNull String code, @NonNull TimeProvider time) {
        this();

        try {
            mTimeProvider = time;
            mLocalMac = Mac.getInstance(SecurityConstants.SECURITY);
            mLocalMac.init(new SecretKeySpec(Base32String.decode(code), ""));
            mPasscodeGenerator = new PasscodeGenerator(mLocalMac);
        } catch (Exception exception) {
            if (BuildConfig.DEBUG) {
                Log.e(CodeProvider.class.getSimpleName(), "exception ", exception);
            }
            throw new IllegalStateException("EXCEPTION");
        }
    }

    @Nullable
    public String generateCode() {
        try {
            return mPasscodeGenerator.generateResponseCode(mTimeProvider.getCurrentInterval());
        } catch (Exception exception) {
            if (BuildConfig.DEBUG) {
                Log.e(CodeProvider.class.getSimpleName(), "exception ", exception);
            }
        }
        return null;
    }

}
