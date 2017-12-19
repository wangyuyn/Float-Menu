package sample.floatmenu.float_menu;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.floatmenu.lib.MenuDialogFragment;
import com.floatmenu.lib.MenuObject;
import com.floatmenu.lib.MenuParams;
import com.floatmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnMenuItemClickListener {

    private ImageView menu;
    private FragmentManager fragmentManager;
    private MenuDialogFragment mMenuDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        initView();
        initToolbar();
        initMenuFragment();
    }

    private void initView() {
        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(this);

    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setmItemHeight((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(true);
        menuParams.setmAnimationDuration(500);
        mMenuDialogFragment = MenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);

    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.drawable.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolBarTextView.setText("FloatMenu");
    }

    private List<MenuObject> getMenuObjects(){
        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(new MenuObject("语音开黑",R.drawable.icon_menu_audio_room));
        menuObjects.add(new MenuObject("拍个视频",R.drawable.icon_menu_publish_video));
        menuObjects.add(new MenuObject("动态发布",R.drawable.icon_menu_publish_moments));
        menuObjects.add(new MenuObject("手游直播",R.drawable.icon_menu_game_live));
        menuObjects.add(new MenuObject("摄像直播",R.drawable.icon_menu_camera_live));

//        menuObjects.add(new MenuObject("语音开黑",R.drawable.icon_menu_audio_room));
//        menuObjects.add(new MenuObject("拍个视频",R.drawable.icon_menu_publish_video));
//        menuObjects.add(new MenuObject("动态发布",R.drawable.icon_menu_publish_moments));
//        menuObjects.add(new MenuObject("手游直播",R.drawable.icon_menu_game_live));
//        menuObjects.add(new MenuObject("摄像直播",R.drawable.icon_menu_camera_live));
        return menuObjects;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu:
                if (fragmentManager.findFragmentByTag(MenuDialogFragment.TAG)==null){
                    mMenuDialogFragment.show(fragmentManager,MenuDialogFragment.TAG);
                }
                break;
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(this,position+"",Toast.LENGTH_LONG).show();
    }
}
