package eu.codlab.swtor.internal.tutorial;

import com.alexandrepiveteau.library.tutorial.ui.fragments.ITutorialValidationFragment;

import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.security.CodeProvider;

/**
 * Created by kevinleperf on 03/02/16.
 */
public class InputKeyController implements ITutorialValidationFragment {

    private static final int CORRECT_CONTENT_LENGTH = 16;

    private String mContent;

    public InputKeyController() {
        mContent = null;
    }

    public void setContent(String content) {
        mContent = content;
    }

    @Override
    public boolean isValid() {
        String code = generateCode();

        return getContent() != null && getContent().length() == CORRECT_CONTENT_LENGTH
                && code != null;
    }

    public String getContent() {
        return mContent;
    }

    public String generateCode() {
        if (null == mContent)
            return null;

        CodeProvider provider = DependencyInjectorFactory.getDependencyInjector()
                .getCodeProvider(mContent);

        if (null == provider)
            return null;

        return provider.generateCode();
    }

    @Override
    public boolean onTryValidate() {
        return false;
    }
}
