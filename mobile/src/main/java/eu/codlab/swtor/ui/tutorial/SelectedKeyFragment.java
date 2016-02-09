package eu.codlab.swtor.ui.tutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alexandrepiveteau.library.tutorial.ui.fragments.AbstractTutorialValidationFragment;
import com.nineoldandroids.animation.ValueAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import eu.codlab.swtor.R;
import eu.codlab.swtor.internal.injector.DependencyInjector;
import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.security.TimeProvider;
import eu.codlab.swtor.internal.tutorial.CodeInvalidateEvent;
import eu.codlab.swtor.internal.tutorial.EventContent;
import eu.codlab.swtor.internal.tutorial.InputKeyController;

/**
 * Created by kevinleperf on 09/02/16.
 */
public class SelectedKeyFragment extends AbstractTutorialValidationFragment {
    private final DependencyInjector mDepdencyInjector;
    private final InputKeyController mInputKeyController;

    @Bind(R.id.get_code)
    TextView mGetCode;

    @Bind(R.id.code_next_time)
    ProgressBar mCodeNextTime;

    public SelectedKeyFragment() {
        mInputKeyController = new InputKeyController();
        mDepdencyInjector = DependencyInjectorFactory.getDependencyInjector();

        EventContent event = mDepdencyInjector.getDefaultEventBus()
                .getStickyEvent(EventContent.class);
        if (event != null) {
            mInputKeyController.setContent(event.getContent());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_get_code, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();

        mDepdencyInjector.getDefaultEventBus()
                .register(this);

        mDepdencyInjector.getTimeProvider()
                .onResume();
    }

    @Override
    public void onPause() {

        mDepdencyInjector.getTimeProvider()
                .onPause();

        mDepdencyInjector.getDefaultEventBus()
                .unregister(this);

        super.onPause();
    }

    @Override
    public boolean isValid() {
        return mInputKeyController.isValid();
    }

    @Override
    public boolean onTryValidate() {
        return mInputKeyController.onTryValidate();
    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onEvent(EventContent event) {
        mInputKeyController.setContent(event.getContent());

        mGetCode.setText(mInputKeyController.generateCode());
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

    public TimeProvider getTimeProvider() {
        return mDepdencyInjector.getTimeProvider();
    }
}
