package eu.codlab.swtor.utils;

import eu.codlab.common.R;

/**
 * Created by kevinleperf on 07/02/16.
 */
public final class Constants {
    /**
     * Contains the tutorial background colors
     */
    public static final ArrayHolder BACKGROUND = new ArrayHolder(new int[]{
            R.color.colorPrimary,
            R.color.colorPrimary,
            R.color.colorPrimary,
            R.color.colorPrimaryDark,
            R.color.colorPrimaryDark,
    });

    /**
     * Contains the title of the standard tutorial views
     */
    public static final ArrayHolder TITLE = new ArrayHolder(new int[]{
            R.string.title_tutorial_hey,
            R.string.title_tutorial_login,
            R.string.title_tutorial_add_key
    });

    /**
     * Contains the description of the standard tutorial views
     */
    public static final ArrayHolder DESCRIPTION = new ArrayHolder(new int[]{
            R.string.tutorial_1_description,
            R.string.tutorial_2_description,
            R.string.tutorial_2_description,
    });

    /**
     * Contains the placeholder of the standard tutorial views
     */
    public static final ArrayHolder RES = new ArrayHolder(new int[]{
            R.drawable.swtor_tutorial_1,
            R.drawable.swtor_tutorial_2,
            R.drawable.swtor_tutorial_3
    });

    /**
     * Prevent the Constants class instantiation
     */
    private Constants() {

    }
}
