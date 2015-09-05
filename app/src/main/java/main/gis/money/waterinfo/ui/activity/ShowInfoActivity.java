package main.gis.money.waterinfo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import main.gis.money.waterinfo.R;
import main.gis.money.waterinfo.ui.Fragment.LineChartFragment;

/**
 * Created by Administrator on 2015/9/3.
 */
public class ShowInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinfo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitle("马口站信息");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LineChartFragment chart = new LineChartFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_chart, chart)
                .commit();
    }


}
