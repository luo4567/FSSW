package main.gis.money.waterinfo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import main.gis.money.waterinfo.R;
import money.gis.bmlibrary.BMap;

public class ContentFragment extends Fragment {

	BMap myBaiduMap;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_layout, null);
		myBaiduMap=new BMap(view);
		myBaiduMap.InitMap(R.id.bmapView,113.255552,23.121212,12);
//		TextView tv = (TextView) view.findViewById(R.id.fragment_tag);
//		String tag = this.getArguments().getString("key");
//		tv.setText("这是选择" + tag + "的内容");
		return view;
	}

}
