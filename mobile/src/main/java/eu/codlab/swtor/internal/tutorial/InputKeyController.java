package eu.codlab.swtor.internal.tutorial;

import com.alexandrepiveteau.library.tutorial.ui.fragments.ITutorialValidationFragment;

import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.security.CodeProvider;

/**
 * Created by kevinleperf on 03/02/16.
 */
public class InputKeyController implements ITutorialValidationFragment {

    private static final int CORRECT_CONTENT_LENGTH = 16;
    private static final int CORRECT_CODE_LENGTH = 6;
    private String _content;

    public InputKeyController() {
    }

    public void setContent(String content) {
        _content = content;
    }

    @Override
    public boolean isValid() {
        String code = generateCode();

        return getContent() != null && getContent().length() == CORRECT_CONTENT_LENGTH
                && code != null && code.length() == CORRECT_CODE_LENGTH;
    }

    public String getContent() {
        return _content;
    }

    public String generateCode() {
        if (_content == null) return null;

        CodeProvider provider = DependencyInjectorFactory.getDependencyInjector()
                .getCodeProvider(_content);

        if (provider == null) return null;

        return provider.generateCode();
    }

    @Override
    public void onTryValidate() {
        //nothing to do, it is let to the UI
    }
}
