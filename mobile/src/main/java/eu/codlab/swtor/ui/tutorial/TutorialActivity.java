package eu.codlab.swtor.ui.tutorial;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alexandrepiveteau.library.tutorial.ui.fragments.AbstractTutorialValidationFragment;
import com.alexandrepiveteau.library.tutorial.ui.fragments.TutorialFragment;

import eu.codlab.swtor.R;

public class TutorialActivity extends com.alexandrepiveteau.library.tutorial.ui.activities.TutorialActivity {

    private final static int background[] = new int[]{
            R.color.colorPrimary,
            R.color.colorPrimary,
            R.color.colorPrimary,
            R.color.colorPrimaryDark,
    };

    private final static int title[] = new int[]{
            R.string.title_tutorial_hey,
            R.string.title_tutorial_login,
            R.string.title_tutorial_add_key
    };

    private final static int description[] = new int[]{
            R.string.tutorial_1_description,
            R.string.tutorial_2_description,
            R.string.tutorial_2_description,
    };

    private final static int res[] = new int[]{
            R.drawable.swtor_tutorial_1,
            R.drawable.swtor_tutorial_2,
            R.drawable.swtor_tutorial_3
    };

    public static void startAndFinish(@NonNull AppCompatActivity parent) {
        Intent intent = new Intent(parent, TutorialActivity.class);

        parent.startActivity(intent);
        parent.finish();
    }

    @Override
    public void onResume() {
        super.onResume();

        findViewById(com.alexandrepiveteau.library.tutorial.R.id.tutorial_button_image_left)
                .setVisibility(View.GONE);
    }

    @Override
    public String getIgnoreText() {
        return getString(R.string.skip);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public int getBackgroundColor(int position) {
        return getResources().getColor(background[position]);
    }

    @Override
    public int getNavigationBarColor(int position) {
        return getResources().getColor(R.color.black);
    }

    @Override
    public int getStatusBarColor(int position) {
        return getResources().getColor(R.color.colorPrimaryDark);
    }

    @Override
    public AbstractTutorialValidationFragment getTutorialFragmentFor(int position) {
        switch (position) {
            case 0:
            case 1:
            case 2:
                return new TutorialFragment.Builder()
                        .setTitle(getString(title[position]))
                        .setDescription(getString(description[position]))
                        .setImageResource(res[position])
                        .setSkippable(true)
                        .build();
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
}
