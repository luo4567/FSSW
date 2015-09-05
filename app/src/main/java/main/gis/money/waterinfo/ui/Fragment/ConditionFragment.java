package main.gis.money.waterinfo.ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.manuelpeinado.multichoiceadapter.MultiChoiceBaseAdapter;

import java.util.ArrayList;

import main.gis.money.waterinfo.R;
import main.gis.money.waterinfo.adapter.NonCheckableItemsAdapter;

/**
 * Created by Administrator on 2015/9/4.
 */
public class ConditionFragment extends Fragment {
    private MultiChoiceBaseAdapter adapter;
    private ArrayList<String> countries = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shuiqing_condition, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.sq_condition);
        initData(rootView);
        adapter = new NonCheckableItemsAdapter(savedInstanceState, countries);
        adapter.setAdapterView(listView);
        return rootView;
    }

    private void initData(View rootView) {
        String[] arr = rootView.getResources().getStringArray(R.array.sq_condition_array);
        for (int i = 0; i < arr.length; i++) {
            countries.add(arr[i]);
        }
    }
}
