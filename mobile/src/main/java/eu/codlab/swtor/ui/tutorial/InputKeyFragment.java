package eu.codlab.swtor.ui.tutorial;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alexandrepiveteau.library.tutorial.ui.fragments.AbstractTutorialValidationFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import eu.codlab.common.dependency.DependencyInjector;
import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.security.TimeProvider;
import eu.codlab.swtor.R;
import eu.codlab.swtor.internal.tutorial.EventContent;
import eu.codlab.swtor.internal.tutorial.InputKeyController;

public class InputKeyFragment extends AbstractTutorialValidationFragment {

    private final DependencyInjector mDepdencyInjector;
    private final InputKeyController mInputKeyController;

    @Bind(R.id.input_code)
    EditText mInputCode;


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

        mDepdencyInjector.getTimeProvider()
                .onResume();

        onTryValidate();
    }

    @Override
    public void onPause() {

        mDepdencyInjector.getTimeProvider()
                .onPause();

        super.onPause();
    }

    @OnTextChanged(value = R.id.input_code, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable editable) {
        Log.d("Content", "afterTExtChanged");
        mInputKeyController.setContent(editable.toString());

        String content = mInputKeyController.getContent();
        DependencyInjectorFactory.getDependencyInjector()
                .getDefaultEventBus().postSticky(new EventContent(content));

        invalidateErrorText();

        fireActivityValidateCurrent();
    }

    @Override
    public boolean isValid() {
        Log.d("Content", "isValid := " + mInputKeyController.isValid());
        invalidateErrorText();
        return mInputKeyController.isValid();
    }

    @Override
    public boolean onTryValidate() {
        invalidateErrorText();
        return mInputKeyController.onTryValidate();
    }

    public TimeProvider getTimeProvider() {
        return mDepdencyInjector.getTimeProvider();
    }

    private void invalidateErrorText() {

        if (isResumed()) {
            if (!mInputKeyController.isValid()) {
                mInputCode.setError(getString(R.string.error_invalid));
            } else {
                mInputCode.setError(null);
            }
        }
    }
}
