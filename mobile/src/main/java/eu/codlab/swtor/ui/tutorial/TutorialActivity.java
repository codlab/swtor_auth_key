package eu.codlab.swtor.ui.tutorial;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.alexandrepiveteau.library.tutorial.ui.fragments.AbstractTutorialValidationFragment;
import com.alexandrepiveteau.library.tutorial.ui.fragments.TutorialFragment;
import com.alexandrepiveteau.library.tutorial.widgets.LinePageIndicatorEngine;
import com.alexandrepiveteau.library.tutorial.widgets.PageIndicator;

import eu.codlab.swtor.R;
import eu.codlab.swtor.utils.Constants;

public class TutorialActivity extends com.alexandrepiveteau.library.tutorial.ui.activities.TutorialActivity {

    @NonNull
    public static Intent createIntent(@NonNull Activity parent) {
        return new Intent(parent, TutorialActivity.class);
    }

    public static void start(@NonNull Activity parent) {
        parent.startActivity(createIntent(parent));
    }

    public static void startAndFinish(@NonNull Activity parent) {
        start(parent);

        parent.finish();
    }

    @Override
    public int getIgnoreText() {
        return R.string.skip;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public int getBackgroundColor(int position) {
        return Constants.BACKGROUND.getValue(position);
    }

    @Override
    public int getNavigationBarColor(int position) {
        return R.color.black;
    }

    @Override
    public int getStatusBarColor(int position) {
        return R.color.colorPrimaryDark;
    }

    @Override
    public AbstractTutorialValidationFragment getTutorialFragmentFor(int position) {
        switch (position) {
            case 0:
            case 1:
            case 2:
                return createTutorialFragment(position);
            case 3:
                return new InputKeyFragment();
            default:
                return new SelectedKeyFragment();
        }
    }

    @Override
    public boolean isNavigationBarColored() {
        return true;
    }

    @Override
    public boolean isStatusBarColored() {
        return true;
    }

    @Override
    public ViewPager.PageTransformer getPageTransformer() {
        return TutorialFragment.getParallaxPageTransformer(1.25f);
    }

    @Override
    public PageIndicator.Engine getPageIndicatorEngine() {
        return new LinePageIndicatorEngine();
    }

    @Nullable
    TutorialFragment createTutorialFragment(int position) {
        if (position < Constants.TITLE.getSize()) {
            return new TutorialFragment.Builder()
                    .setTitle(getString(Constants.TITLE.getValue(position)))
                    .setDescription(getString(Constants.DESCRIPTION.getValue(position)))
                    .setImageResource(Constants.RES.getValue(position))
                    .setSkippable(true)
                    .build();
        } else {
            return null;
        }
    }
}
