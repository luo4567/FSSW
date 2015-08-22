package main.gis.money.waterinfo.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import main.gis.money.waterinfo.R;
import money.gis.bmlibrary.BMap;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class MapFragment extends Fragment implements ScreenShotable{

	public BMap getMyBaiduMap() {
		return myBaiduMap;
	}

	BMap myBaiduMap;
	private Bitmap bitmap;
	private View containerView;

	public static MapFragment newInstance() {
		MapFragment contentFragment = new MapFragment();
		return contentFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_layout, null);
		myBaiduMap=new BMap(view);
		myBaiduMap.initMap(R.id.bmapView, 113.255552, 23.121212, 12);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.containerView = view.findViewById(R.id.container);
	}

	@Override
	public void takeScreenShot() {
		// TODO: 2015/8/16 根据点击的类型获取数据
//		Thread thread = new Thread() {
//			@Override
//			public void run() {
//				Log.d("view",containerView.toString());
//				Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
//						containerView.getHeight(), Bitmap.Config.ARGB_8888);
//				Canvas canvas = new Canvas(bitmap);
//				containerView.draw(canvas);
//				MapFragment.this.bitmap = bitmap;
//			}
//		};
//
//		thread.start();
	}

	@Override
	public Bitmap getBitmap() {
		return bitmap;
	}
}
