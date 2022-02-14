package com.study.android.communicate;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Book implements Parcelable {
    public int id;
    public String author;
    public String title;

    Book() {
        this("默认的书名《Java编程思想》");
    }

    Book(String name) {
        this(0, name);
    }

    Book(int id, String name) {
        this(0, "ljz", name);
    }

    public Book(int id, String author, String name) {
        this.id = id;
        this.author = author;
        this.title = name;
    }

    protected Book(Parcel in) {
        Log.d("xxxll", "Book: in = " + in);
        id = in.readInt();
        author = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.d("xxxll", "writeToParcel: dest = " + dest + ", flags = " + flags);
        dest.writeInt(id);
        dest.writeString(author);
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