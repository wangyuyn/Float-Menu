package com.floatmenu.lib;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.floatmenu.lib.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxb on 2017/12/7.
 */

public class MenuAdapter {

    public static final int ANIMATION_DURATION_MILLIS = 100;

    private OnItemClickListener clickListener;
    private Context context;
    private LinearLayout mWrapperButtons;
    private ScrollView sv;
    private List<MenuObject> mMenuObjects;
    private int mMenuItemHeight;
    private MenuParams mMenuParams;

    private boolean isAnimationRun = false;
    private boolean mIsMenuOpen = false;

    private AnimatorSet mAnimatorSetShowMenu;
    private AnimatorSet mAnimatorSetHideMenu;
    private View mClickItemView;

    public MenuAdapter(Context context, LinearLayout mWrapperButtons, MenuParams mMenuParams, ScrollView sv) {
        this.context = context;
        this.mWrapperButtons = mWrapperButtons;
        this.mMenuObjects = mMenuParams.getmMenuObjects();
        this.sv = sv;
        this.mMenuParams = mMenuParams;

        this.mMenuItemHeight = mMenuParams.getmItemHeight();

        setViews();

        mAnimatorSetShowMenu = setOpenAnimation(true);
        mAnimatorSetHideMenu = setOpenAnimation(false);
    }

    private AnimatorSet setOpenAnimation(boolean isOpen) {
        List<Animator> animations = new ArrayList<>();
        for (int i = 0;i<mMenuObjects.size();i++){
            AnimatorSet textAnimatorSet = new AnimatorSet();
            Animator tran;
            Animator alpha;
            if (isOpen) {
                tran = ObjectAnimator.ofFloat(mWrapperButtons.getChildAt(i), "translationY", context.getResources().getDimension(R.dimen.dp_100) * (i + 1) / mMenuObjects.size(), 0);
                alpha = ObjectAnimator.ofFloat(mWrapperButtons.getChildAt(i), "alpha", 0, 1);
            }else{
                tran = ObjectAnimator.ofFloat(mWrapperButtons.getChildAt(i), "translationY", 0, context.getResources().getDimension(R.dimen.dp_100) * (i + 1) / mMenuObjects.size());
                alpha = ObjectAnimator.ofFloat(mWrapperButtons.getChildAt(i), "alpha", 1, 0);
            }
            textAnimatorSet.playTogether(tran,alpha);
//            textAnimatorSet.setStartDelay(i*500);
            animations.add(textAnimatorSet);
        }
        ValueAnimator animator;
        if (isOpen){
            animator = ValueAnimator.ofInt(0,180);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
//                    sv.setBackgroundColor((Integer) animation.getAnimatedValue());
                    sv.setBackgroundColor(Color.argb((Integer)animation.getAnimatedValue(), 0, 0, 0));
                }
            });
            animations.add(animator);
        }else{
            animator = ValueAnimator.ofInt(180,0);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
//                    sv.setBackgroundColor((Integer) animation.getAnimatedValue());
                    sv.setBackgroundColor(Color.argb((Integer)animation.getAnimatedValue(), 0, 0, 0));
                }
            });
            animations.add(animator);
        }


        AnimatorSet togetherSet = new AnimatorSet();
        togetherSet.playTogether(animations);
        togetherSet.setDuration(mMenuParams.getmAnimationDuration());
        togetherSet.setStartDelay(0);
        togetherSet.setInterpolator(new AccelerateDecelerateInterpolator());
        togetherSet.addListener(mCloseOpenAnimatorListener);
//        togetherSet.setInterpolator(new OvershootInterpolator());

        return togetherSet;
    }

    private Animator.AnimatorListener mCloseOpenAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            toggleIsAnimationRun();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    };

    private void setViews() {
        for (int i = 0;i<mMenuObjects.size();i++){
            MenuObject menuObject = mMenuObjects.get(i);
            boolean isLast = false;
            if (i==mMenuObjects.size()-1){
                isLast = true;
            }
            mWrapperButtons.addView(ViewUtils.getItemView(context,onClick,mMenuItemHeight,menuObject,isLast));
        }
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

//            if (clickListener!=null){
//                clickListener.onItemClickListener(v);
//            }
            itemClick(v);
        }
    };

    private void itemClick(View v) {
        if (mIsMenuOpen && !isAnimationRun) {
            mClickItemView = v;
            int childIndex = ((ViewGroup) v.getParent()).indexOfChild(v);
            toggleIsAnimationRun();
            buildChosenAnimation(childIndex);
            toggleIsMenuOpen();
        }
    }

    private void buildChosenAnimation(int childIndex) {
        List<Animator> fadeOutTopAnimatorList = new ArrayList<>();
        for (int i=0;i<childIndex;i++){
            AnimatorSet textAnimatorSet = new AnimatorSet();
            Animator tran = ObjectAnimator.ofFloat(mWrapperButtons.getChildAt(i),"translationX",0,200);
            Animator alpha = ObjectAnimator.ofFloat(mWrapperButtons.getChildAt(i), "alpha", 1, 0);
            textAnimatorSet.playTogether(tran,alpha);
            textAnimatorSet.setStartDelay(i*100);
            fadeOutTopAnimatorList.add(textAnimatorSet);
        }

        AnimatorSet topClose = new AnimatorSet();
        topClose.playTogether(fadeOutTopAnimatorList);

        List<Animator> fadeOutBottomAnimatorList = new ArrayList<>();
        for (int i=mMenuObjects.size()-1;i>childIndex;i--){
            AnimatorSet textAnimatorSet = new AnimatorSet();
            Animator tran = ObjectAnimator.ofFloat(mWrapperButtons.getChildAt(i),"translationX",0,200);
            Animator alpha = ObjectAnimator.ofFloat(mWrapperButtons.getChildAt(i), "alpha", 1, 0);
            textAnimatorSet.playTogether(tran,alpha);
            textAnimatorSet.setStartDelay((mMenuObjects.size()-1-i)*100);
            fadeOutBottomAnimatorList.add(textAnimatorSet);
        }

        AnimatorSet bottomClose = new AnimatorSet();
        bottomClose.playTogether(fadeOutBottomAnimatorList);

        AnimatorSet indexItem = new AnimatorSet();
        Animator tran = ObjectAnimator.ofFloat(mWrapperButtons.getChildAt(childIndex),"translationX",0,200);
        Animator alpha = ObjectAnimator.ofFloat(mWrapperButtons.getChildAt(childIndex), "alpha", 1, 0);
        indexItem.playTogether(tran,alpha);

        ValueAnimator animator;
        animator = ValueAnimator.ofInt(180,0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sv.setBackgroundColor(Color.argb((Integer)animation.getAnimatedValue(), 0, 0, 0));
            }
        });

        AnimatorSet finaClose = new AnimatorSet();
        finaClose.play(topClose).with(bottomClose).with(indexItem).with(animator);
        if (fadeOutTopAnimatorList.size()>=fadeOutBottomAnimatorList.size()){
//            finaClose.play(topClose).before(indexItem);
            indexItem.setStartDelay(fadeOutTopAnimatorList.size()*100);
        }else{
//            finaClose.play(bottomClose).before(indexItem);
            indexItem.setStartDelay(fadeOutBottomAnimatorList.size()*100);
        }

        finaClose.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            if (clickListener!=null){
                clickListener.onItemClickListener(mClickItemView);
            }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        finaClose.setDuration(500);
        finaClose.start();

    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void menuToggle() {
        if (!isAnimationRun) {
            isAnimationRun = true;
            if (!mIsMenuOpen){
                mAnimatorSetShowMenu.start();
            }else {
                mAnimatorSetHideMenu.start();
            }
            toggleIsMenuOpen();
        }
    }
    private void toggleIsMenuOpen() {
        mIsMenuOpen = !mIsMenuOpen;
    }
    private void toggleIsAnimationRun() {
        isAnimationRun = !isAnimationRun;
    }
}
