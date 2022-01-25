package com.study.android.communicate;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    public String title;

    Book() {
        this("默认的书名《Java编程思想》");
    }

    Book(String name) {
        this.title = name;
    }

    protected Book(Parcel in) {
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}