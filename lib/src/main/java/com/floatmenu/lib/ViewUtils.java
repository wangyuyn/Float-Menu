package com.floatmenu.lib;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by hxb on 2017/12/7.
 */

public class ViewUtils {
    public static View getItemView(Context context, View.OnClickListener clickListener, int mMenuItemHeight, MenuObject menuObject,boolean islast) {
        TextView tv = new TextView(context);
        tv.setCompoundDrawablesWithIntrinsicBounds(null,null,context.getResources().getDrawable(menuObject.getmResource()),null);
        tv.setCompoundDrawablePadding((int) context.getResources().getDimension(R.dimen.dp_20));
        tv.setText(menuObject.getmTitle());
        tv.setTextColor(menuObject.getmTextColor());
        tv.setTextSize(menuObject.getmTextSize());
        tv.setOnClickListener(clickListener);
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) context.getResources().getDimension(R.dimen.dp_10);
        if (islast){
            params.bottomMargin = (int) context.getResources().getDimension(R.dimen.dp_100);
        }
        tv.setLayoutParams(params);
        return tv;
    }
}
