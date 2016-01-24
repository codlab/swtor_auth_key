package com.bioware.android.apps.authenticator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.ClipboardManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bioware.android.apps.authenticator.dataimport.ImportController;
//import com.bioware.android.apps.authenticator.howitworks.IntroEnterPasswordActivity;
//import com.bioware.android.apps.authenticator.testability.DependencyInjector;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
/*
    private void onCreateInternal(Bundle savedInstanceState){
        setContentView(eu.codlab.swtor.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(eu.codlab.swtor.R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(eu.codlab.swtor.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    static final String ACTION_SCAN_BARCODE = AuthenticatorActivity.class.getName() + ".ScanBarcode";
    public static final int CHECK_KEY_VALUE_ID = 0;
    static final int COPY_TO_CLIPBOARD_ID = 3;
    private static final String COUNTER_PARAM = "counter";
    static final int DIALOG_ID_SAVE_KEY = 13;
    static final int DIALOG_ID_UNINSTALL_OLD_APP = 12;
    private static final String HOTP = "hotp";
    private static final long HOTP_DISPLAY_TIMEOUT = 120000L;
    private static final long HOTP_MIN_TIME_INTERVAL_BETWEEN_CODES = 5000L;
    private static final String KEY_OLD_APP_UNINSTALL_INTENT = "oldAppUninstallIntent";
    private static final String KEY_SAVE_KEY_DIALOG_PARAMS = "saveKeyDialogParams";
    private static final String LOCAL_TAG = "AuthenticatorActivity";
    private static final String OTP_SCHEME = "otpauth";
    private static final float PIN_TEXT_SCALEX_NORMAL = 1.0F;
    private static final float PIN_TEXT_SCALEX_UNDERSCORE = 0.87F;
    public static final int REMOVE_ID = 2;
    public static final int RENAME_ID = 1;
    static final int SCAN_REQUEST = 31337;
    private static final String SECRET_PARAM = "secret";
    private static final String TOTP = "totp";
    private static final long TOTP_COUNTDOWN_REFRESH_PERIOD = 100L;
    private static final long VIBRATE_DURATION = 200L;
    private AccountDb mAccountDb;
    private View mContentAccountsPresent;
    private boolean mDataImportInProgress;
    private TextView mEnterPinPrompt;
    private Intent mOldAppUninstallIntent;
    private OtpSource mOtpProvider;
    private SaveKeyDialogParams mSaveKeyDialogParams;
    private boolean mSaveKeyIntentConfirmationInProgress;
    private TotpClock mTotpClock;
    private double mTotpCountdownPhase;
    private TotpCountdownTask mTotpCountdownTask;
    private TotpCounter mTotpCounter;
    private ListView mUserList;
    private PinInfo[] mUsers = new PinInfo[0];

    private void addAccount() {
    }

    private void displayHowItWorksInstructions() {
        this.startActivity(new Intent(this, IntroEnterPasswordActivity.class));
    }

    public static Intent getLaunchIntentActionScanBarcode(Context var0) {
        return (new Intent(ACTION_SCAN_BARCODE)).setComponent(new ComponentName(var0, AuthenticatorActivity.class));
    }

    private DialogInterface.OnClickListener getRenameClickListener(final Context var1, final String var2, final EditText var3) {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface var1x, int var2x) {
                String var3x = var3.getText().toString();
                if (var3x != var2) {
                }

            }
        };
    }

    private void handleIntent(Intent var1) {
        if (var1 != null) {
            String var2 = var1.getAction();
            if (var2 != null) {
                if (ACTION_SCAN_BARCODE.equals(var2)) {
                    this.scanBarcode();
                    return;
                }

                if (var1.getData() != null) {
                    this.interpretScanResult(var1.getData(), true);
                    return;
                }
            }
        }

    }

    private String idToEmail(long var1) {
        return this.mUsers[(int) var1].user;
    }

    private void importDataFromOldAppIfNecessary() {
        if (!this.mDataImportInProgress) {
            this.mDataImportInProgress = true;
            DependencyInjector.getDataImportController().start(this, new ImportController.Listener() {
                public void onDataImported() {
                    if (!isFinishing()) {
                        refreshUserList(true);
                        DependencyInjector.getOptionalFeatures().onDataImportedFromOldApp(MainActivity.this);
                    }
                }

                public void onFinished() {
                    if (!isFinishing()) {
                        mDataImportInProgress = false;
                    }
                }

                public void onOldAppUninstallSuggested(Intent var1) {
                    if (!isFinishing()) {
                        mOldAppUninstallIntent = var1;
                        Toast.makeText(MainActivity.this, "onOldAppUninstallSuggested", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void interpretScanResult(Uri var1, boolean var2) {
        if (!DependencyInjector.getOptionalFeatures().interpretScanResult(this, var1)) {
            if (var2) {
                if (this.mSaveKeyIntentConfirmationInProgress) {
                    Log.w("AuthenticatorActivity", "Ignoring save key Intent: previous Intent not yet confirmed by user");
                    return;
                }

                this.mSaveKeyIntentConfirmationInProgress = true;
            }

            if (var1 == null) {
                this.showDialog(3);
            } else if ("otpauth".equals(var1.getScheme()) && var1.getAuthority() != null) {
                this.parseSecret(var1, var2);
            } else {
                this.showDialog(3);
            }
        }
    }

    private void manuallyEnterAccountDetails() {
        Intent var1 = new Intent("android.intent.action.VIEW");
        var1.setClass(this, EnterKeyActivity.class);
        this.startActivity(var1);
    }

    private void markDialogAsResultOfSaveKeyIntent(Dialog var1) {
        var1.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface var1) {
                AuthenticatorActivity.this.onSaveKeyIntentConfirmationPromptDismissed();
            }
        });
    }

    private void onSaveKeyIntentConfirmationPromptDismissed() {
        this.mSaveKeyIntentConfirmationInProgress = false;
    }

    private void parseSecret(Uri var1, boolean var2) {
        String var4 = var1.getScheme().toLowerCase();
        String var6 = var1.getPath();
        String var5 = var1.getAuthority();
        if (!"otpauth".equals(var4)) {
            Log.e("SWTOR", "AuthenticatorActivity: Invalid or missing scheme in uri");
        } else {
            Integer var9;
            AccountDb.OtpType var10;
            if ("totp".equals(var5)) {
                var10 = AccountDb.OtpType.TOTP;
                var9 = AccountDb.DEFAULT_HOTP_COUNTER;
            } else {
                if (!"hotp".equals(var5)) {
                    Log.e("SWTOR", "AuthenticatorActivity: Invalid or missing authority in uri");
                    return;
                }

                var10 = AccountDb.OtpType.HOTP;
                var4 = var1.getQueryParameter("counter");
                if (var4 != null) {
                    int var3;
                    try {
                        var3 = Integer.parseInt(var4);
                    } catch (NumberFormatException var7) {
                        Log.e("SWTOR", "AuthenticatorActivity: Invalid counter in uri");
                        return;
                    }

                    var9 = Integer.valueOf(var3);
                } else {
                    var9 = AccountDb.DEFAULT_HOTP_COUNTER;
                }
            }

            var6 = validateAndGetUserInPath(var6);
            if (var6 == null) {
                Log.e("SWTOR", "AuthenticatorActivity: Missing user id in uri");
                this.showDialog(3);
                return;
            }

            String var8 = var1.getQueryParameter("secret");
            if (var8 == null || var8.length() == 0) {
                Log.e("SWTOR", "AuthenticatorActivity: Secret key not found in URI");
                this.showDialog(7);
                return;
            }

            if (AccountDb.getSigningOracle(var8) == null) {
                Log.e("SWTOR", "AuthenticatorActivity: Invalid secret key");
                this.showDialog(7);
                return;
            }

            if (!var8.equals(this.mAccountDb.getSecret(var6)) || var9 != this.mAccountDb.getCounter(var6) || var10 != this.mAccountDb.getType(var6)) {
                if (var2) {
                    this.mSaveKeyDialogParams = new SaveKeyDialogParams(var6, var8, var10, var9);
                    this.showDialog(13);
                    return;
                }

                this.saveSecretAndRefreshUserList(var6, var8, (String) null, var10, var9);
                return;
            }
        }

    }

    private void refreshVerificationCodes() {
        this.refreshUserList();
        this.setTotpCountdownPhase(1.0D);
    }

    static boolean saveSecret(Context var0, String var1, String var2, String var3, AccountDb.OtpType var4, Integer var5) {
        String var6 = var3;
        if (var3 == null) {
            var6 = var1;
        }

        if (var2 != null) {
            DependencyInjector.getAccountDb().update(var1, var2, var6, var4, var5);
            DependencyInjector.getOptionalFeatures().onAuthenticatorActivityAccountSaved(var0, var1);
            Toast.makeText(var0, "SAVED", Toast.LENGTH_SHORT).show();
            ((Vibrator) var0.getSystemService(VIBRATOR_SERVICE)).vibrate(200L);
            return true;
        } else {
            Log.e("AuthenticatorActivity", "Trying to save an empty secret key");
            Toast.makeText(var0, "Trying to save an empty secret key", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void saveSecretAndRefreshUserList(String var1, String var2, String var3, AccountDb.OtpType var4, Integer var5) {
        if (saveSecret(this, var1, var2, var3, var4, var5)) {
            this.refreshUserList(true);
        }

    }

    private void scanBarcode() {
        Intent var1 = new Intent("com.google.zxing.client.android.SCAN");
        var1.putExtra("SCAN_MODE", "QR_CODE_MODE");
        var1.putExtra("SAVE_HISTORY", false);

        try {
            this.startActivityForResult(var1, 31337);
        } catch (ActivityNotFoundException var2) {
            this.showDialog(0);
        }
    }

    private void setTotpCountdownPhase(double var1) {
        this.mTotpCountdownPhase = var1;
        this.updateCountdownIndicators();
    }

    private void setTotpCountdownPhaseFromTimeTillNextValue(long var1) {
        this.setTotpCountdownPhase((double) var1 / (double) Utilities.secondsToMillis(this.mTotpCounter.getTimeStep()));
    }

    private void showSettings() {
        Intent var1 = new Intent();
        var1.setClass(this, SettingsActivity.class);
        this.startActivity(var1);
    }

    private void stopTotpCountdownTask() {
        if (this.mTotpCountdownTask != null) {
            this.mTotpCountdownTask.stop();
            this.mTotpCountdownTask = null;
        }

    }

    private void updateCodesAndStartTotpCountdownTask() {
        this.stopTotpCountdownTask();
        this.mTotpCountdownTask = new TotpCountdownTask(this.mTotpCounter, this.mTotpClock, 100L);
        this.mTotpCountdownTask.setListener(new com.bioware.android.apps.authenticator.TotpCountdownTask.Listener() {
            public void onTotpCountdown(long var1) {
                if (!isFinishing()) {
                    setTotpCountdownPhaseFromTimeTillNextValue(var1);
                }
            }

            public void onTotpCounterValueChanged() {
                if (!isFinishing()) {
                    refreshVerificationCodes();
                }
            }
        });
        this.mTotpCountdownTask.startAndNotifyListener();
    }

    private void updateCountdownIndicators() {
        int var1 = 0;

        for (int var2 = this.mUserList.getChildCount(); var1 < var2; ++var1) {
            CountdownIndicator var3 = (CountdownIndicator) this.mUserList.getChildAt(var1).findViewById(2131361810);
            if (var3 != null) {
                var3.setPhase(this.mTotpCountdownPhase);
            }
        }

    }

    private static String validateAndGetUserInPath(String var0) {
        if (var0 != null && var0.startsWith("/")) {
            String var1 = var0.substring(1).trim();
            var0 = var1;
            if (var1.length() == 0) {
                return null;
            }
        } else {
            var0 = null;
        }

        return var0;
    }

    public void computeAndDisplayPin(String var1, int var2, boolean var3) throws OtpSourceException {
        PinInfo var5;
        if (this.mUsers[var2] != null) {
            var5 = this.mUsers[var2];
        } else {
            var5 = new PinInfo();
            var5.pin = "NONE";
            var5.hotpCodeGenerationAllowed = true;
        }

        boolean var4;
        if (this.mAccountDb.getType(var1) == AccountDb.OtpType.HOTP) {
            var4 = true;
        } else {
            var4 = false;
        }

        var5.isHotp = var4;
        var5.user = var1;
        if (!var5.isHotp || var3) {
            var5.pin = this.mOtpProvider.getNextCode(var1);
            var5.hotpCodeGenerationAllowed = true;
        }

        this.mUsers[var2] = var5;

        Log.d("SWTOR","PIN CALCULATED "+ var5.pin+" "+var5.user);
    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        Uri var4 = null;
        Log.i("SWTOR", "AuthenticatorActivity: onActivityResult");
        if (var1 == 31337 && var2 == -1) {
            String var5;
            if (var3 != null) {
                var5 = var3.getStringExtra("SCAN_RESULT");
            } else {
                var5 = null;
            }

            if (var5 != null) {
                var4 = Uri.parse(var5);
            }

            this.interpretScanResult(var4, false);
        }

    }



    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.mAccountDb = DependencyInjector.getAccountDb();
        this.mOtpProvider = DependencyInjector.getOtpProvider();
        this.setTitle(2131165187);
        this.mTotpCounter = this.mOtpProvider.getTotpCounter();
        this.mTotpClock = this.mOtpProvider.getTotpClock();

        onCreateInternal(var1);

        Object var4 = this.getLastNonConfigurationInstance();
        if (var4 != null) {
            this.mUsers = (PinInfo[]) ((PinInfo[]) var4);
            PinInfo[] var7 = this.mUsers;
            int var3 = var7.length;

            for (int var2 = 0; var2 < var3; ++var2) {
                PinInfo var5 = var7[var2];
                if (var5.isHotp) {
                    var5.hotpCodeGenerationAllowed = true;
                }
            }
        }

        if (var1 != null) {
            this.mOldAppUninstallIntent = (Intent) var1.getParcelable("oldAppUninstallIntent");
            this.mSaveKeyDialogParams = (SaveKeyDialogParams) var1.getSerializable("saveKeyDialogParams");
        }

        //this.mUserList = (ListView) this.findViewById(2131361803);
        //this.mContentAccountsPresent = this.findViewById(2131361801);
        View var8 = this.mContentAccountsPresent;
        byte var6;
        if (this.mUsers.length > 0) {
            var6 = 0;
        } else {
            var6 = 8;
        }

        if (var1 == null) {
            //DependencyInjector.getOptionalFeatures().onAuthenticatorActivityCreated(this);
            this.importDataFromOldAppIfNecessary();
            this.handleIntent(this.getIntent());
        }

    }

    protected void onNewIntent(Intent var1) {
        Log.i("SWTOR", "AuthenticatorActivity: onNewIntent");
        this.handleIntent(var1);
    }

    protected void onResume() {
        super.onResume();
        Log.i("SWTOR", "AuthenticatorActivity: onResume");
        this.importDataFromOldAppIfNecessary();
    }

    protected void onSaveInstanceState(Bundle var1) {
        super.onSaveInstanceState(var1);
        var1.putParcelable("oldAppUninstallIntent", this.mOldAppUninstallIntent);
        var1.putSerializable("saveKeyDialogParams", this.mSaveKeyDialogParams);
    }

    protected void onStart() {
        super.onStart();
        this.updateCodesAndStartTotpCountdownTask();
    }

    protected void onStop() {
        this.stopTotpCountdownTask();
        super.onStop();
    }

    protected void refreshUserList() {
        this.refreshUserList(false);
    }

    public void refreshUserList(boolean var1) {
        ArrayList var5 = new ArrayList();
        this.mAccountDb.getNames(var5);
        int var4 = var5.size();
        if (var4 > 0) {
            boolean var2;
            if (!var1 && this.mUsers.length == var4) {
                var2 = false;
            } else {
                var2 = true;
            }

            if (var2) {
                this.mUsers = new PinInfo[var4];
            }

            for (int var3 = 0; var3 < var4; ++var3) {
                String var6 = (String) var5.get(var3);

                try {
                    this.computeAndDisplayPin(var6, var3, false);
                } catch (OtpSourceException var7) {
                    ;
                }
            }

            if (var2) {
                this.mUserAdapter = new PinListAdapter(this, 2130903050, this.mUsers);
                this.mUserList.setAdapter(this.mUserAdapter);
            }

            this.mUserAdapter.notifyDataSetChanged();
            if (this.mUserList.getVisibility() != 0) {
                this.mUserList.setVisibility(0);
                this.registerForContextMenu(this.mUserList);
            }
        } else {
            this.mUsers = new AuthenticatorActivity.PinInfo[0];
            this.mUserList.setVisibility(8);
        }

        if (this.mUsers.length == 0) {
            this.manuallyEnterAccountDetails();
        }

    }

    private class NextOtpButtonListener implements android.view.View.OnClickListener {
        private final AuthenticatorActivity.PinInfo mAccount;
        private final Handler mHandler;

        private NextOtpButtonListener(AuthenticatorActivity.PinInfo var2) {
            this.mHandler = new Handler();
            this.mAccount = var2;
        }

        private int findAccountPositionInList() {
            int var1 = 0;

            for (int var2 = AuthenticatorActivity.this.mUsers.length; var1 < var2; ++var1) {
                if (AuthenticatorActivity.this.mUsers[var1] == this.mAccount) {
                    return var1;
                }
            }

            return -1;
        }

        public void onClick(View var1) {
            int var2 = this.findAccountPositionInList();
            if (var2 == -1) {
                throw new RuntimeException("Account not in list: " + this.mAccount);
            } else {
                try {
                    AuthenticatorActivity.this.computeAndDisplayPin(this.mAccount.user, var2, true);
                } catch (OtpSourceException var3) {
                    DependencyInjector.getOptionalFeatures().onAuthenticatorActivityGetNextOtpFailed(AuthenticatorActivity.this, this.mAccount.user, var3);
                    return;
                }

                final String var4 = this.mAccount.pin;
                this.mAccount.hotpCodeGenerationAllowed = false;
                AuthenticatorActivity.this.mUserAdapter.notifyDataSetChanged();
                this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        NextOtpButtonListener.this.mAccount.hotpCodeGenerationAllowed = true;
                        AuthenticatorActivity.this.mUserAdapter.notifyDataSetChanged();
                    }
                }, 5000L);
                this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        if (var4.equals(NextOtpButtonListener.this.mAccount.pin)) {
                            NextOtpButtonListener.this.mAccount.pin = AuthenticatorActivity.this.getString(2131165207);
                            AuthenticatorActivity.this.mUserAdapter.notifyDataSetChanged();
                        }
                    }
                }, 120000L);
            }
        }
    }

    private static class PinInfo {
        private boolean hotpCodeGenerationAllowed;
        private boolean isHotp;
        private String pin;
        private String user;

        private PinInfo() {
            this.isHotp = false;
        }
    }

    private static class SaveKeyDialogParams implements Serializable {
        private final Integer counter;
        private final String secret;
        private final AccountDb.OtpType type;
        private final String user;

        private SaveKeyDialogParams(String var1, String var2, AccountDb.OtpType var3, Integer var4) {
            this.user = var1;
            this.secret = var2;
            this.type = var3;
            this.counter = var4;
        }
    }*/
}
