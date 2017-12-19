package com.floatmenu.lib;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hxb on 2017/12/6.
 */

public class MenuParams implements Parcelable {
    /**
     * 条目高度
     */

    private int mItemHeight = 0;
    /**
     * 动画执行时间
     */
    private int mAnimationDuration = 0;
    /**
     * 菜单对象
     */
    private List<MenuObject> mMenuObjects;
    /**
     * 点击空白处是否消失
     */
    private boolean isClosableOutside = false;

    private int rightDistance;

    public int getmItemHeight() {
        return mItemHeight;
    }

    public void setmItemHeight(int mItemHeight) {
        this.mItemHeight = mItemHeight;
    }

    public List<MenuObject> getmMenuObjects() {
        return mMenuObjects;
    }

    public void setMenuObjects(List<MenuObject> mMenuObjects) {
        this.mMenuObjects = mMenuObjects;
    }

    public boolean isClosableOutside() {
        return isClosableOutside;
    }

    public void setClosableOutside(boolean closableOutside) {
        isClosableOutside = closableOutside;
    }

    public int getmAnimationDuration() {
        return mAnimationDuration;
    }

    public void setmAnimationDuration(int mAnimationDuration) {
        this.mAnimationDuration = mAnimationDuration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mMenuObjects);
        dest.writeInt(this.mItemHeight);
        dest.writeInt(this.mAnimationDuration);
        dest.writeByte(isClosableOutside?(byte)1:(byte)0);
    }
    public MenuParams() {
    }

    private MenuParams(Parcel in) {
        this.mItemHeight = in.readInt();
        in.readTypedList(mMenuObjects, MenuObject.CREATOR);
        this.mAnimationDuration = in.readInt();
        this.isClosableOutside = in.readByte()!=0;

    }
    public static final Parcelable.Creator<MenuParams> CREATOR = new Parcelable.Creator<MenuParams>() {
        public MenuParams createFromParcel(Parcel source) {
            return new MenuParams(source);
        }

        public MenuParams[] newArray(int size) {
            return new MenuParams[size];
        }
    };
}
