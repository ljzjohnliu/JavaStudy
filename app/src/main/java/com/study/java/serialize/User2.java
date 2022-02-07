package com.study.java.serialize;

import android.os.Parcel;
import android.os.Parcelable;

public class User2 implements Parcelable {
    private int id;

    private String name;

    protected User2() {

    }

    /**
     * 从序列化后的对象中创建原始对象
     */
    protected User2(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    /**
     * 将当前对象写入序列化结构中
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    /**
     * 当前对象的内容描述,一般返回0即可
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * public static final一个都不能少，内部对象CREATOR的名称也不能改变，必须全部大写。
     * 重写接口中的两个方法：
     * createFromParcel(Parcel in) 实现从Parcel容器中读取传递数据值,封装成Parcelable对象返回逻辑层，
     * newArray(int size) 创建一个类型为T，长度为size的数组，供外部类反序列化本类数组使用。
     */
    public static final Creator<User2> CREATOR = new Creator<User2>() {
        /**
         * 从序列化后的对象中创建原始对象
         */
        @Override
        public User2 createFromParcel(Parcel in) {
            return new User2(in);
        }

        /**
         * 创建指定长度的原始对象数组
         */
        @Override
        public User2[] newArray(int size) {
            return new User2[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
