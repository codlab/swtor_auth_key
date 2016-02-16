package eu.codlab.swtor.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nineoldandroids.animation.ValueAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.security.TimeProvider;
import eu.codlab.common.security.events.CodeInvalidateEvent;
import eu.codlab.swtor.R;
import eu.codlab.swtor.internal.database.events.DatabaseEvent;
import eu.codlab.swtor.internal.database.impl.Key;
import eu.codlab.swtor.internal.tutorial.EventContent;
import eu.codlab.swtor.internal.tutorial.InputKeyController;
import eu.codlab.swtor.service.NotificationKeyService;
import eu.codlab.swtor.ui.abstracts.AbstractKeysActivity;

public class ShowCodeActivity extends AbstractKeysActivity {

    @Bind(R.id.container)
    BoxInsetLayout mContainerView;

    @Bind(R.id.code_next_time)
    ProgressBar mCodeNextTime;

    @Bind(R.id.get_code)
    TextView mGetCode;

    private InputKeyController mInputKeyController;

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

        mInputKeyController = new InputKeyController();

        EventContent event = getDependencyInjector().getDefaultEventBus()
                .getStickyEvent(EventContent.class);
        if (event != null) {
            mInputKeyController.setContent(event.getContent());
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        setContentKey();

        getDependencyInjector().getDefaultEventBus()
                .register(this);

        getDependencyInjector().getTimeProvider()
                .onResume();

        Log.d("Wear","onResume");
        Intent intent = new Intent(this, NotificationKeyService.class);
        startService(intent);
    }

    @Override
    public void onPause() {

        getDependencyInjector().getTimeProvider()
                .onPause();

        getDependencyInjector().getDefaultEventBus()
                .unregister(this);

        super.onPause();
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
            mGetCode.setTextColor(getResources().getColor(android.R.color.white));
            mCodeNextTime.setVisibility(View.GONE);
        } else {
            mContainerView.setBackground(null);
            mGetCode.setTextColor(getResources().getColor(android.R.color.black));
            mCodeNextTime.setVisibility(View.VISIBLE);
        }
    }

    /**
     * this whole code can be refactored with its counterpart in the mobile unit
     */

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onEvent(DatabaseEvent event) {
        Key key = event.getKey();
        if (key != null) {
            String content = event.getKey().getSecret();
            onEvent(new EventContent(content));
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onEvent(EventContent event) {
        String previous = mInputKeyController.getContent();

        mInputKeyController.setContent(event.getContent());

        if (mInputKeyController.isValid()) {
            mGetCode.setText(mInputKeyController.generateCode());
        } else {
            //restore the previous code if not valid
            mInputKeyController.setContent(previous);
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onEvent(CodeInvalidateEvent event) {
        ValueAnimator animator = ValueAnimator.ofInt((int) event.getNextIterationIn(), 0);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCodeNextTime.setProgress((Integer) animation.getAnimatedValue());
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(event.getNextIterationIn());
        animator.start();

        mGetCode.setText(mInputKeyController.generateCode());
    }

    public void setContentKey() {
        Key key = DependencyInjectorFactory.getDependencyInjector()
                .getDatabaseProvider()
                .getLastKey();

        if (key != null) {
            mInputKeyController.setContent(key.getSecret());
            mGetCode.setText(mInputKeyController.generateCode());
        }
    }

    public TimeProvider getTimeProvider() {
        return getDependencyInjector().getTimeProvider();
    }
}
