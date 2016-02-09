package eu.codlab.swtor.utils;

import eu.codlab.swtor.R;

/**
 * Created by kevinleperf on 07/02/16.
 */
public final class Constants {
    public static final ArrayHolder BACKGROUND = new ArrayHolder(new int[]{
            R.color.colorPrimary,
            R.color.colorPrimary,
            R.color.colorPrimary,
            R.color.colorPrimaryDark,
            R.color.colorPrimaryDark,
    });

    public static final ArrayHolder TITLE = new ArrayHolder(new int[]{
            R.string.title_tutorial_hey,
            R.string.title_tutorial_login,
            R.string.title_tutorial_add_key
    });

    public static final ArrayHolder DESCRIPTION = new ArrayHolder(new int[]{
            R.string.tutorial_1_description,
            R.string.tutorial_2_description,
            R.string.tutorial_2_description,
    });

    public static final ArrayHolder RES = new ArrayHolder(new int[]{
            R.drawable.swtor_tutorial_1,
            R.drawable.swtor_tutorial_2,
            R.drawable.swtor_tutorial_3
    });

    private Constants() {

    }
}
