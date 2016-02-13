package eu.codlab.swtor.utils;

import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class ArrayHolder {

    /**
     * The array values
     */
    @Nullable
    @AnyRes
    private int[] mValues;

    /**
     * Private instantiation
     */
    private ArrayHolder() {
        mValues = null;
    }

    /**
     * Construct an holder
     *
     * @param array a non null array containing resources
     */
    public ArrayHolder(@NonNull @AnyRes int[] array) {
        this();
        mValues = Arrays.copyOf(array, array.length);
    }

    /**
     * Get the containment size
     * @return the size of the array, >= 0
     */
    public int getSize() {
        return mValues.length;
    }

    /**
     * Get the current resource value
     *
     * @param position the given position <= getSize()
     * @return the corresponding resources
     */
    @AnyRes
    public int getValue(int position) {
        return mValues[position];
    }
}
