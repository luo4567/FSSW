package main.gis.money.waterinfo;

import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import main.gis.money.waterinfo.consts.MenuConst;
import main.gis.money.waterinfo.entity.Region;
import main.gis.money.waterinfo.ui.Fragment.ConditionFragment;
import main.gis.money.waterinfo.ui.Fragment.MapFragment;
import main.gis.money.waterinfo.util.GJsonHelper;
import main.gis.money.waterinfo.util.TreeUtil;
import main.gis.money.waterinfo.util.volley.UrlHelper;
import main.gis.money.waterinfo.util.volley.VolleyHelper;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MainActivity extends ActionBarActivity implements ViewAnimator.ViewAnimatorListener {

    private int currentType = 0;

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
    private TreeUtil treeUtil = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        // 初始化菜单列表
        createMenuList();
        setActionBar();
        viewAnimator = new ViewAnimator<>(this, list, mapFragment, drawerLayout, this);
        initDatas();
    }

    private void getStationInfo() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", currentType);
        params.put("cityId", null);
        params.put("order", null);
        params.put("yuqingType", null);
        String url = UrlHelper.getStationsUrl(params);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) {
                    // todo 从数据库中获取数据
                    return;
                }
                // TODO: 2015/9/7
                // 1.转换返回的结果（from json）

                // 2.清空地图已绘制的点

                // 3.绘制新的点

                // 4.重新绘制树数据

                // 5.重新设置下拉选择框

                // 6.将数据存储在数据库中
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyHelper.addToRequestQueue(request);
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        //1.获取区域信息数据
        StringRequest request = new StringRequest(Request.Method.GET, UrlHelper.REGION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                List<Region> regions = GJsonHelper.getObjects(response, Region.class);
                for (Region region : regions) {
                    region.getCityName();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyHelper.addToRequestQueue(request);
        // 2.获取水情数据
        ConditionFragment fragment = new ConditionFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_condition, fragment)
                .commit();
    }

    /**
     * 设置ActionBar可见，并且切换菜单和内容视图
     */
    private void setActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.AppTitle);
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
        if (slideMenuItem.getName() != "Close") {
            toolbar.setTitle(slideMenuItem.getName());
        }
        Map<String, Object> params = new HashMap<>();
        params.put("type", position);
        params.put("cityId", null);
        treeUtil = new TreeUtil(MainActivity.this, mapFragment);
        String url = "";
        switch (slideMenuItem.getName()) {
            case MenuConst.CLOSE:
                return screenShotable;
            case MenuConst.RAIN:
                params.put("yuqingType", 0);
            default:

                url = UrlHelper.getStationsUrl(params);
                treeUtil.getDataFromServer(url, "Stations");
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
        SlideMenuItem menuItem = new SlideMenuItem(MenuConst.WATER, R.mipmap.m3);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(MenuConst.RAIN, R.mipmap.m4);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(MenuConst.FLOOD, R.mipmap.m1);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(MenuConst.SOIL, R.mipmap.m2);
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
