package eu.codlab.swtor.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import eu.codlab.swtor.R;

public class ShowCodeActivity extends WearableActivity {

    @Bind(R.id.container)
    BoxInsetLayout mContainerView;

    @Bind(R.id.get_code)
    ProgressBar mProgressBar;

    @Bind(R.id.code_next_time)
    TextView mCode;

    @NonNull
    public static Intent createIntent(@NonNull Activity parent) {
        return new Intent(parent, ShowCodeActivity.class);
    }

    public static void startAndFinish(@NonNull Activity parent) {
        parent.startActivity(createIntent(parent));

        parent.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        ButterKnife.bind(this);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mCode.setTextColor(getResources().getColor(android.R.color.white));
            mProgressBar.setVisibility(View.GONE);
        } else {
            mContainerView.setBackground(null);
            mCode.setTextColor(getResources().getColor(android.R.color.black));
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }
}
