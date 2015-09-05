package main.gis.money.waterinfo.ui.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;
import main.gis.money.waterinfo.R;
import main.gis.money.waterinfo.entity.ChartInfo;
import main.gis.money.waterinfo.entity.StationHistory;
import main.gis.money.waterinfo.holder.ListCommAdapter;
import main.gis.money.waterinfo.holder.MyAdapterWithCommViewHolder;

/**
 * Created by Administrator on 2015/8/16.
 */
public class LineChartFragment extends Fragment {
    private LineChartView chart;
    private LineChartData data;

    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = true;

    private TextView title;
    private TextView subTitle;

    private ListView mListView;
    private List<StationHistory> mDatas;
    private MyAdapterWithCommViewHolder mAdapterWithCommViewHolder;
    private ListCommAdapter mAdapterWithCommAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_line_chart, container, false);

        chart = (LineChartView) rootView.findViewById(R.id.chart);
        title = (TextView) rootView.findViewById(R.id.title);
        subTitle = (TextView) rootView.findViewById(R.id.subTitle);
        chart.setOnValueTouchListener(new ValueTouchListener());

        initDatas();
        //initView(rootView);

        Bundle bundle = getArguments();
        //ChartInfo chartInfo = (ChartInfo) bundle.get("line");
        //generateData(chartInfo);
        // Disable viewpirt recalculations, see toggleCubic() method for more info.
        chart.setViewportCalculationEnabled(false);
        return rootView;
    }

    /**
     * 初始化数据和适配器
     * @return void
     * @author Yann
     * @date 2015-8-5 下午10:15:03
     */
    private void initDatas()
    {
        mDatas = new ArrayList<StationHistory>();
        StationHistory bean = new StationHistory("2015-08-05", 10086);
        mDatas.add(bean);
        bean = new StationHistory("2015-08-06", 10086);
        mDatas.add(bean);
        bean = new StationHistory("2015-08-07", 10086);
        mDatas.add(bean);
        bean = new StationHistory("2015-08-08", 10086);
        mDatas.add(bean);
        bean = new StationHistory("2015-08-09", 10086);
        mDatas.add(bean);

        bean = new StationHistory("2015-08-10", 10086);
        mDatas.add(bean);
        bean = new StationHistory("2015-08-11", 10086);
        mDatas.add(bean);
        bean = new StationHistory("2015-08-12", 10086);
        mDatas.add(bean);
        bean = new StationHistory("2015-08-13", 10086);
        mDatas.add(bean);

        bean = new StationHistory("2015-08-14", 10086);
        mDatas.add(bean);
        bean = new StationHistory("2015-08-15", 10086);
        mDatas.add(bean);
        bean = new StationHistory("2015-08-16", 10086);
        mDatas.add(bean);
        bean = new StationHistory("2015-08-17", 10086);
        mDatas.add(bean);

        mAdapterWithCommViewHolder = new MyAdapterWithCommViewHolder(getActivity(), mDatas, R.layout.listview_data_item);
        mAdapterWithCommAdapter = new ListCommAdapter(getActivity(), mDatas, R.layout.listview_data_item);
    }

/*    *//**
     * 为列表设置适配器
     * @return void
     * @author Yann
     * @date 2015-8-5 下午10:15:04
     *//*
    private void initView(View inflater)
    {
        mListView = (ListView)inflater.findViewById(R.id.data);
//		mListView.setAdapter(mAdapterWithCommViewHolder);
        mListView.setAdapter(mAdapterWithCommAdapter);
    }*/

    private void generateData(ChartInfo chartInfo) {
        title.setText(chartInfo.getTitle());
        subTitle.setText(chartInfo.getSubTitle());
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < chartInfo.getXData().size(); i++) {
            values.add(new PointValue(i, chartInfo.getYData().get(i)));
            axisValues.add(new AxisValue(i).setLabel(chartInfo.getXData().get(i)));
        }
        List<Line> lines = new ArrayList<Line>();
        Line line = new Line(values);
        line.setColor(ChartUtils.COLORS[0]);
        line.setShape(shape);
        line.setCubic(isCubic);
        line.setFilled(isFilled);
        line.setHasLabels(hasLabels);
        line.setHasLabelsOnlyForSelected(hasLabelForSelected);
        line.setHasLines(hasLines);
        line.setHasPoints(hasPoints);
        lines.add(line);
        data = new LineChartData(lines);
        Axis axisX = new Axis(axisValues);
        Axis axisY = new Axis().setHasLines(true);
        if (hasAxesNames) {
            axisX.setName(chartInfo.getLinexName());
            axisY.setName(chartInfo.getLineyName());
        }
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);

    }

    /**
     * To animate values you have to change targets values and then call {@link Chart#startDataAnimation()}
     * method(don't confuse with View.animate()). If you operate on data that was set before you don't have to call
     * {@link LineChartView#setLineChartData(LineChartData)} again.
     */
    private void prepareDataAnimation() {
        for (Line line : data.getLines()) {
            for (PointValue value : line.getValues()) {
                // Here I modify target only for Y values but it is OK to modify X targets as well.
                value.setTarget(value.getX(), (float) Math.random() * 100);
            }
        }
    }

    private class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
}
