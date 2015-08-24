package main.gis.money.waterinfo.holder;

import android.content.Context;

import java.util.List;

import main.gis.money.baseadapter.CommonAdapter;
import main.gis.money.baseadapter.ViewHolder;
import main.gis.money.waterinfo.R;
import main.gis.money.waterinfo.entity.StationHistory;

/**
 * Created by Administrator on 2015/8/24.
 */
public class ListCommAdapter extends CommonAdapter<StationHistory>{
    public ListCommAdapter(Context context, List<StationHistory> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, StationHistory stationHistory) {
        holder.setText(R.id.checkTime,stationHistory.getCheckTime());
        holder.setText(R.id.checkValue, String.valueOf(stationHistory.getCheckValue()));
    }
}
