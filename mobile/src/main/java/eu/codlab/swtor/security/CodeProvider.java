package eu.codlab.swtor.security;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bioware.android.apps.authenticator.Base32String;
import com.bioware.android.apps.authenticator.PasscodeGenerator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by kevinleperf on 15/01/16.
 */
public class CodeProvider {
    private PasscodeGenerator _passcode_generator;
    private Mac _local_mac;

    private CodeProvider() {

    }

    public CodeProvider(@NonNull String code) throws IllegalStateException {
        this();

        try {
            _local_mac = Mac.getInstance(SecurityConstants.SECURITY);
            _local_mac.init(new SecretKeySpec(Base32String.decode(code), ""));
            _passcode_generator = new PasscodeGenerator(_local_mac);
        } catch (Exception exception) {
            throw new IllegalStateException("EXCEPTION");
        }
    }

    @Nullable
    public String generateCode() {
        long timestamp = System.currentTimeMillis();
        try {
            return _passcode_generator.generateResponseCode(timestamp / SecurityConstants.INTERVAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
