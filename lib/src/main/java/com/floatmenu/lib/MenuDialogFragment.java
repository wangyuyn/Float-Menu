package com.floatmenu.lib;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.floatmenu.lib.interfaces.OnItemClickListener;
import com.floatmenu.lib.interfaces.OnMenuItemClickListener;

/**
 * Created by hxb on 2017/12/6.
 */

public class MenuDialogFragment extends DialogFragment implements OnItemClickListener {

    public static final String TAG = MenuDialogFragment.class.getSimpleName();
    private static final String BUNDLE_MENU_PARAMS = "BUNDLE_MENU_PARAMS";
    private MenuParams mMenuParams;
    private LinearLayout mWrapperButtons;
    public ScrollView sv;
    private  MenuAdapter adapter;
    private OnMenuItemClickListener mItemClickListener;

    public static MenuDialogFragment newInstance(MenuParams menuParams) {
        MenuDialogFragment fragment = new MenuDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_MENU_PARAMS, menuParams);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.MenuFragmentStyle);
        if (getArguments() != null) {
            mMenuParams = getArguments().getParcelable(BUNDLE_MENU_PARAMS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu,container,false);

        initView(view);

        initMenuAdapter();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.menuToggle();
            }
        },0);

        if (mMenuParams.isClosableOutside()) {
            view.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAdded()) {
//                        dismiss();
                        adapter.menuToggle();
                        close(mMenuParams.getmAnimationDuration());
                    }
                }
            });
        }

        return view;
    }

    private void close(int i) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, i);
    }

    private void initMenuAdapter() {
        adapter = new MenuAdapter(getActivity(),mWrapperButtons,mMenuParams,sv);
        adapter.setOnItemClickListener(this);
    }

    private void initView(View view) {
        sv = view.findViewById(R.id.sv);
        mWrapperButtons = view.findViewById(R.id.wrapper_buttons);
    }

    @Override
    public void onItemClickListener(View view) {
        if (mItemClickListener!=null){
            mItemClickListener.onMenuItemClick(view,((ViewGroup) view.getParent()).indexOfChild(view));
        }
        close(0);
    }

    public void setItemClickListener(OnMenuItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }
}
