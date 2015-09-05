/*
 * Copyright (C) 2013 Manuel Peinado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package main.gis.money.waterinfo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manuelpeinado.multichoiceadapter.MultiChoiceBaseAdapter;

import main.gis.money.waterinfo.R;


public class NonCheckableItemsAdapter extends MultiChoiceBaseAdapter {
    protected static final String TAG = NonCheckableItemsAdapter.class.getSimpleName();

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    private static class Item {
        public static final int HEADER = 0;
        public static final int NORMAL = 1;
        boolean isHeader;
        String text;

        public Item(String text, boolean isInitial) {
            this.isHeader = isInitial;
            this.text = text;
        }
    }

    private List<Item> items;

    public NonCheckableItemsAdapter(Bundle savedInstanceState, List<String> items) {
        super(savedInstanceState);
        this.items = addInitials(items);
    }

    private List<Item> addInitials(List<String> items) {
        List<Item> result = new ArrayList<Item>();
        for (String name : items) {
            result.add(new Item(name, false));
        }
        return result;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return Item.NORMAL;
    }
    
    @Override
    public boolean isItemCheckable(int position) {
        return true;
    }

    @Override
    protected View getViewImpl(int position, View convertView, ViewGroup parent) {
        return getNormalView(position, convertView, parent);
    }

    private View getNormalView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.baseadapter_list_item, parent, false);
        }
        ViewGroup group = (ViewGroup) convertView;
        String country = getItem(position).text;
        ((TextView) group.findViewById(android.R.id.text1)).setText(country);
        ((TextView) group.findViewById(android.R.id.text2)).setVisibility(View.GONE);
        return group;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        return false;
    }

}
