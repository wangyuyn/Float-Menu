package com.floatmenu.lib;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hxb on 2017/12/6.
 */

public class MenuObject implements Parcelable {
    private int mResource;
    private String mTitle;
    private int mTextColor = Color.WHITE;
    private int mTextSize = 18;

    public MenuObject(String title,int resource) {
        this.mTitle = title;
        this.mResource = resource;
    }

    public int getmTextSize() {
        return mTextSize;
    }

    public void setmTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getmResource() {
        return mResource;
    }

    public void setmResource(int mResource) {
        this.mResource = mResource;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public static final Creator<MenuObject> CREATOR = new Creator<MenuObject>() {
        public MenuObject createFromParcel(Parcel source) {
            return new MenuObject(source);
        }

        public MenuObject[] newArray(int size) {
            return new MenuObject[size];
        }
    };
    private MenuObject(Parcel in) {
        this.mTitle = in.readString();
        this.mResource = in.readInt();
        this.mTextColor = in.readInt();
        this.mTextSize = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeInt(this.mResource);
        dest.writeInt(this.mTextColor);
        dest.writeInt(this.mTextSize);
    }
}
