package eu.codlab.swtor.utils;

import android.support.annotation.NonNull;

import java.util.Arrays;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class ArrayHolder {

    private int[] mValues;

    private ArrayHolder() {
        mValues = null;
    }

    public ArrayHolder(@NonNull int[] array) {
        this();
        mValues = Arrays.copyOf(array, array.length);
    }

    public int getSize() {
        return mValues.length;
    }

    public int getValue(int position) {
        return mValues[position];
    }
}
