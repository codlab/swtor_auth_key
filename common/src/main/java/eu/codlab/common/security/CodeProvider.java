package eu.codlab.common.security;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bioware.android.apps.authenticator.Base32String;
import com.bioware.android.apps.authenticator.PasscodeOverride;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by kevinleperf on 15/01/16.
 */
public class CodeProvider {
    private PasscodeOverride mPasscodeGenerator;
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
            mPasscodeGenerator = new PasscodeOverride(mLocalMac);
        } catch (Exception exception) {
            throw new IllegalStateException("EXCEPTION");
        }
    }

    @Nullable
    public String generateCode() {
        return mPasscodeGenerator.generateResponseCode(mTimeProvider.getCurrentInterval());
    }

}
