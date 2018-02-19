package com.hiroshi.cimoc.misc;

import android.support.annotation.NonNull;
import android.support.v4.util.LongSparseArray;

import java.util.Iterator;

/**
 * Created by Hiroshi on 2018/2/17.
 */

public class IterableLongSparseArray<T> implements Iterable<T> {

    private LongSparseArray<T> mArray;

    public IterableLongSparseArray() {
        mArray = new LongSparseArray<>();
    }

    public void put(long key, T value) {
        mArray.put(key, value);
    }

    public T get(long key) {
        return mArray.get(key);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < mArray.size();
            }

            @Override
            public T next() {
                return mArray.valueAt(index++);
            }
        };
    }

}
