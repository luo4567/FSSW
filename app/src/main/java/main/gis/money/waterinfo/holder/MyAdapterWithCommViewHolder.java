package main.gis.money.waterinfo.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import main.gis.money.baseadapter.ViewHolder;
import main.gis.money.waterinfo.R;
import main.gis.money.waterinfo.entity.StationHistory;

/**
 * Created by Administrator on 2015/8/24.
 */
public class MyAdapterWithCommViewHolder extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<StationHistory> mDatas;
    private Context mContext;
    private int mLayoutId;

    public MyAdapterWithCommViewHolder(Context context, List<StationHistory> datas, int layoutId) {
        mContext = context;
        mDatas = datas;
        mLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, mLayoutId, position);
        StationHistory stationHistory = mDatas.get(position);

        ((TextView) holder.getView(R.id.checkTime)).setText(stationHistory.getCheckTime());
        ((TextView) holder.getView(R.id.checkValue)).setText(String.valueOf(stationHistory.getCheckValue()));
        return holder.getConvertView();
    }
}
