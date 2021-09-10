// IBookAidlInterface.aidl
package com.study.android.communicate;

import com.study.android.communicate.Book;

// Declare any non-default types here with import statements

interface IBookAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    String getTitle();
    void setTitle(String title);
    Book getBook();
    void setBook(in Book book);
}