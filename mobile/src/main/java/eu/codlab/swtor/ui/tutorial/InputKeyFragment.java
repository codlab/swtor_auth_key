package eu.codlab.swtor.ui.tutorial;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import com.alexandrepiveteau.library.tutorial.ui.fragments.AbstractTutorialValidationFragment;
import com.nineoldandroids.animation.ValueAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import eu.codlab.swtor.R;
import eu.codlab.swtor.internal.injector.DependencyInjector;
import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.tutorial.CodeInvalidateEvent;
import eu.codlab.swtor.internal.tutorial.InputKeyController;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputKeyFragment extends AbstractTutorialValidationFragment {

    private final DependencyInjector mDepdencyInjector;
    private final InputKeyController mInputKeyController;

    @Bind(R.id.code_next_time)
    ProgressBar _code_next_time;

    public InputKeyFragment() {
        mInputKeyController = new InputKeyController();
        mDepdencyInjector = DependencyInjectorFactory.getDependencyInjector();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input_key, container, false);
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

    @OnTextChanged(R.id.input_code)
    public void afterTextChanged(Editable content) {
        mInputKeyController.setContent(content.toString());
    }

    @Override
    public boolean isValid() {
        return mInputKeyController.isValid();
    }

    @Override
    public void onTryValidate() {

        Log.d(InputKeyFragment.class.getSimpleName(), "onTryValidate ");

        mInputKeyController.onTryValidate();

    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onEvent(CodeInvalidateEvent event) {
        Log.d(InputKeyFragment.class.getSimpleName(), "next in :: " + event.getNextIterationIn());

        ValueAnimator animator = ValueAnimator.ofInt((int) event.getNextIterationIn(), 0);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                _code_next_time.setProgress((Integer) animation.getAnimatedValue());
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(event.getNextIterationIn());
        animator.start();
    }
}
