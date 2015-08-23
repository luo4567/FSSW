package main.gis.money.waterinfo;

import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import main.gis.money.waterinfo.consts.MenuConst;
import main.gis.money.waterinfo.ui.MapFragment;
import main.gis.money.waterinfo.util.TreeUtil;
import main.gis.money.waterinfo.util.volley.UrlHelper;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MainActivity extends ActionBarActivity implements ViewAnimator.ViewAnimatorListener {
    /**
     * DrawerLayout
     */
    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle drawerToggle;

    /**
     * 左边栏菜单
     */
    private LinearLayout left_drawer;

    /**
     * 右边栏
     */
    private RelativeLayout right_drawer;

    /**
     * 地图主界面
     */
    private MapFragment mapFragment;

    private Toolbar toolbar;
    private ViewAnimator viewAnimator;
    private List<SlideMenuItem> list = new ArrayList<>();
    private TreeUtil treeUtil=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        // 初始化菜单列表
        createMenuList();
        setActionBar();
        viewAnimator = new ViewAnimator<>(this, list, mapFragment, drawerLayout, this);
    }

    /**
     * 设置ActionBar可见，并且切换菜单和内容视图
     */
    private void setActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View drawerView) {
                left_drawer.removeAllViews();
                left_drawer.invalidate();
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && left_drawer.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void initViews() {
        mapFragment = MapFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mapFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        left_drawer = (LinearLayout) findViewById(R.id.left_drawer);
        right_drawer = (RelativeLayout) findViewById(R.id.right_drawer);
        left_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        toolbar.setTitle(slideMenuItem.getName());
        Map<String, Object> params = new HashMap<>();
        treeUtil=new TreeUtil(MainActivity.this,right_drawer,mapFragment);
        String url="";
        switch (slideMenuItem.getName()) {
            case MenuConst.CLOSE:
                return screenShotable;
            default:
                params.put("stationType",position);
                url= UrlHelper.getStationsUrl(params);
                treeUtil.getDataFromServer(url,"Stations");
                return setAnimator(screenShotable, position);
        }
    }

    private ScreenShotable setAnimator(ScreenShotable screenShotable, int position) {
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, position, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        return mapFragment;
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        left_drawer.addView(view);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(MenuConst.CLOSE, R.mipmap.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(MenuConst.WATER, R.mipmap.icn_1);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(MenuConst.RAIN, R.mipmap.icn_2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(MenuConst.FLOOD, R.mipmap.icn_3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(MenuConst.SOIL, R.mipmap.icn_4);
        list.add(menuItem4);
    }

    /**
     * 点击ActionBar上菜单
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
/*        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (showView == left_drawer) {
                    if (!isDirection_left) { // 左边栏菜单关闭时，打开
                        drawerLayout.openDrawer(left_drawer);
                    } else {// 左边栏菜单打开时，关闭
                        drawerLayout.closeDrawer(left_drawer);
                    }
                } else if (showView == right_drawer) {
                    if (!isDirection_right) {// 右边栏关闭时，打开
                        drawerLayout.openDrawer(right_drawer);
                    } else {// 右边栏打开时，关闭
                        drawerLayout.closeDrawer(right_drawer);
                    }
                }
                break;
            case R.id.action_personal:
                if (!isDirection_right) {// 右边栏关闭时，打开
                    if (showView == left_drawer) {
                        drawerLayout.closeDrawer(left_drawer);
                    }
                    drawerLayout.openDrawer(right_drawer);
                } else {// 右边栏打开时，关闭
                    drawerLayout.closeDrawer(right_drawer);
                }
                break;
            default:
                break;
        }*/
        return super.onOptionsItemSelected(item);
    }

    /**
     * 根据onPostCreate回调的状态，还原对应的icon state
     *
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * 加载菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
