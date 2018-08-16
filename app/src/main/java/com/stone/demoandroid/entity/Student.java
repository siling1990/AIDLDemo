package com.stone.demoandroid.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Stone on 2018/8/16.
 */
public class Student implements Parcelable {
    public int id;
    public String name;

    protected Student(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
