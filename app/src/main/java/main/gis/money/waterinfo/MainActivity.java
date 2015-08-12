package main.gis.money.waterinfo;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import main.gis.money.materialmenu.MaterialMenuDrawable;
import main.gis.money.materialmenu.MaterialMenuIcon;
import main.gis.money.waterinfo.holder.HeaderHolder;
import main.gis.money.waterinfo.holder.IconTreeItemHolder;
import main.gis.money.waterinfo.holder.PlaceHolderHolder;
import main.gis.money.waterinfo.holder.ProfileHolder;
import main.gis.money.waterinfo.holder.SocialViewHolder;
import main.gis.money.waterinfo.ui.ContentFragment;

public class MainActivity extends FragmentActivity {
    private AndroidTreeView tView;
    /**
     * DrawerLayout
     */
    private DrawerLayout mDrawerLayout;
    /**
     * 左边栏菜单
     */
    private ListView mMenuListView;
    /**
     * 右边栏
     */
    private RelativeLayout right_drawer;
    /**
     * 菜单列表
     */
    private String[] mMenuTitles;
    /**
     * Material Design风格
     */
    private MaterialMenuIcon mMaterialMenuIcon;
    /**
     * 菜单打开/关闭状态
     */
    private boolean isDirection_left = false;
    /**
     * 右边栏打开/关闭状态
     */
    private boolean isDirection_right = false;
    private Fragment MapFragment;
    private View showView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mMenuListView = (ListView) findViewById(R.id.left_drawer);
        right_drawer = (RelativeLayout) findViewById(R.id.right_drawer);
        this.showView = mMenuListView;

        // 初始化菜单列表
        mMenuTitles = getResources().getStringArray(R.array.menu_array);
        mMenuListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuTitles));
        mMenuListView.setOnItemClickListener(new DrawerItemClickListener());

        // 设置抽屉打开时，主要内容区被自定义阴影覆盖
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        // 设置ActionBar可见，并且切换菜单和内容视图
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mMaterialMenuIcon = new MaterialMenuIcon(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        mDrawerLayout.setDrawerListener(new DrawerLayoutStateListener());

        if (savedInstanceState == null) {
            MapFragment = new ContentFragment();
            //selectItem(0);
        }
        initTree(savedInstanceState);
    }

    private void initTree(Bundle savedInstanceState) {
        final ViewGroup containerView = (ViewGroup) right_drawer.findViewById(R.id.container);

        final TreeNode root = TreeNode.root();

        TreeNode myProfile = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_person, "My Profile")).setViewHolder(new ProfileHolder(this));
        TreeNode bruce = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_person, "Bruce Wayne")).setViewHolder(new ProfileHolder(this));
        TreeNode clark = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_person, "Clark Kent")).setViewHolder(new ProfileHolder(this));

        //TreeNode barry = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_person, "Barry Allen")).setViewHolder(new ProfileHolder(this));
        addProfileData(myProfile);
        addProfileData(clark);
        addProfileData(bruce);
        //addProfileData(barry);
        root.addChildren(myProfile, bruce, clark);

        tView = new AndroidTreeView(MainActivity.this, root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
        containerView.addView(tView.getView());

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }
    }

    private void addProfileData(TreeNode profile) {
        TreeNode socialNetworks = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_people, "Social")).setViewHolder(new HeaderHolder(this));
        TreeNode places = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_place, "Places")).setViewHolder(new HeaderHolder(this));

        TreeNode facebook = new TreeNode(new SocialViewHolder.SocialItem(R.string.ic_post_facebook)).setViewHolder(new SocialViewHolder(this));
        TreeNode linkedin = new TreeNode(new SocialViewHolder.SocialItem(R.string.ic_post_linkedin)).setViewHolder(new SocialViewHolder(this));
        TreeNode google = new TreeNode(new SocialViewHolder.SocialItem(R.string.ic_post_gplus)).setViewHolder(new SocialViewHolder(this));
        TreeNode twitter = new TreeNode(new SocialViewHolder.SocialItem(R.string.ic_post_twitter)).setViewHolder(new SocialViewHolder(this));

        TreeNode lake = new TreeNode(new PlaceHolderHolder.PlaceItem("A rose garden")).setViewHolder(new PlaceHolderHolder(this));
        TreeNode mountains = new TreeNode(new PlaceHolderHolder.PlaceItem("The white house")).setViewHolder(new PlaceHolderHolder(this));

        places.addChildren(lake, mountains);
        socialNetworks.addChildren(facebook, google, twitter, linkedin);
        profile.addChildren(socialNetworks, places);
    }

    /**
     * ListView上的Item点击事件
     */
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
        }
    }

    /**
     * DrawerLayout状态变化监听
     */
    private class DrawerLayoutStateListener extends
            DrawerLayout.SimpleDrawerListener {
        /**
         * 当导航菜单滑动的时候被执行
         */
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            showView = drawerView;
            if (drawerView == mMenuListView) {// 根据isDirection_left决定执行动画
                mMaterialMenuIcon.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        isDirection_left ? 2 - slideOffset : slideOffset);
            } else if (drawerView == right_drawer) {// 根据isDirection_right决定执行动画
                mMaterialMenuIcon.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        isDirection_right ? 2 - slideOffset : slideOffset);
            }
        }

        /**
         * 当导航菜单打开时执行
         */
        @Override
        public void onDrawerOpened(android.view.View drawerView) {
            if (drawerView == mMenuListView) {
                isDirection_left = true;
            } else if (drawerView == right_drawer) {
                isDirection_right = true;
            }
        }

        /**
         * 当导航菜单关闭时执行
         */
        @Override
        public void onDrawerClosed(android.view.View drawerView) {
            if (drawerView == mMenuListView) {
                isDirection_left = false;
            } else if (drawerView == right_drawer) {
                isDirection_right = false;
                showView = mMenuListView;
            }
        }
    }

    /**
     * 切换主视图区域的Fragment
     *
     * @param position
     */
    private void selectItem(int position) {
        Bundle args = new Bundle();
        switch (position) {
            case 0:
                args.putString("key", mMenuTitles[position]);
                break;
            case 1:
                args.putString("key", mMenuTitles[position]);
                break;
            case 2:
                args.putString("key", mMenuTitles[position]);
                break;
            case 3:
                args.putString("key", mMenuTitles[position]);
                break;
            default:
                break;
        }
        MapFragment.setArguments(args); // FragmentActivity将点击的菜单列表标题传递给Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, MapFragment).commit();

        // 更新选择后的item和title，然后关闭菜单
        mMenuListView.setItemChecked(position, true);
        setTitle(mMenuTitles[position]);
        mDrawerLayout.closeDrawer(mMenuListView);
    }

    /**
     * 点击ActionBar上菜单
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (showView == mMenuListView) {
                    if (!isDirection_left) { // 左边栏菜单关闭时，打开
                        mDrawerLayout.openDrawer(mMenuListView);
                    } else {// 左边栏菜单打开时，关闭
                        mDrawerLayout.closeDrawer(mMenuListView);
                    }
                } else if (showView == right_drawer) {
                    if (!isDirection_right) {// 右边栏关闭时，打开
                        mDrawerLayout.openDrawer(right_drawer);
                    } else {// 右边栏打开时，关闭
                        mDrawerLayout.closeDrawer(right_drawer);
                    }
                }
                break;
            case R.id.action_personal:
                if (!isDirection_right) {// 右边栏关闭时，打开
                    if (showView == mMenuListView) {
                        mDrawerLayout.closeDrawer(mMenuListView);
                    }
                    mDrawerLayout.openDrawer(right_drawer);
                } else {// 右边栏打开时，关闭
                    mDrawerLayout.closeDrawer(right_drawer);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 根据onPostCreate回调的状态，还原对应的icon state
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mMaterialMenuIcon.syncState(savedInstanceState);
    }

    /**
     * 根据onSaveInstanceState回调的状态，保存当前icon state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mMaterialMenuIcon.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
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