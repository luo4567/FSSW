package main.gis.money.waterinfo.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;

import java.util.Random;

import main.gis.money.waterinfo.R;

/**
 * Created by Bogdan Melnychuk on 2/13/15.
 */
public class SocialViewHolder extends TreeNode.BaseNodeViewHolder<SocialViewHolder.SocialItem> {
    public SocialViewHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, SocialItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_social_node, null, false);

        final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
        iconView.setIconText(context.getResources().getString(value.icon));

        TextView connectionsLabel = (TextView) view.findViewById(R.id.connections);
        connectionsLabel.setText(value.checkTime);

        TextView userNameLabel = (TextView) view.findViewById(R.id.username);
        userNameLabel.setText(value.stationName);

        TextView sizeText = (TextView) view.findViewById(R.id.size);
        sizeText.setText(value.checkValue);

        return view;
    }

    @Override
    public void toggle(boolean active) {
    }


    public static class SocialItem {
        public int icon;

        public String stationName;

        public String checkTime;

        public String checkValue;

        public SocialItem(int icon, String stationName, String checkTime, String checkValue, String unit) {
            this.icon = icon;
            this.stationName = stationName;
            this.checkTime = checkTime;
            this.checkValue = checkValue + " " + unit;
        }
        // rest will be hardcoded
    }

}
