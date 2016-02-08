package eu.codlab.swtor.ui.tutorial;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.alexandrepiveteau.library.tutorial.ui.fragments.AbstractTutorialValidationFragment;
import com.alexandrepiveteau.library.tutorial.ui.fragments.TutorialFragment;

import eu.codlab.swtor.R;
import eu.codlab.swtor.utils.Constants;

public class TutorialActivity extends com.alexandrepiveteau.library.tutorial.ui.activities.TutorialActivity {

    @NonNull
    public static Intent createIntent(@NonNull AppCompatActivity parent) {
        return new Intent(parent, TutorialActivity.class);
    }

    public static void startAndFinish(@NonNull AppCompatActivity parent) {
        parent.startActivity(createIntent(parent));

        parent.finish();
    }

    @Override
    public int getIgnoreText() {
        return R.string.skip;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public int getBackgroundColor(int position) {
        return Constants.BACKGROUND[position];
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
            default:
                return new InputKeyFragment();
        }
    }

    @Override
    public boolean isNavigationBarColored() {
        return false;
    }

    @Override
    public boolean isStatusBarColored() {
        return false;
    }

    @Override
    public ViewPager.PageTransformer getPageTransformer() {
        return TutorialFragment.getParallaxPageTransformer(1.25f);
    }

    @Nullable
    TutorialFragment createTutorialFragment(int position) {
        if (position < Constants.TITLE.length) {
            return new TutorialFragment.Builder()
                    .setTitle(getString(Constants.TITLE[position]))
                    .setDescription(getString(Constants.DESCRIPTION[position]))
                    .setImageResource(Constants.RES[position])
                    .setSkippable(true)
                    .build();
        } else {
            return null;
        }
    }
}
