package com.itashiev.ogrnot.ogrnotapplication.menu.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.menu.model.MenuDrawerItem;

import java.util.ArrayList;

public class MenuDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MenuDrawerItem> menuDrawerItemArrayList;

    public MenuDrawerListAdapter(Context context, ArrayList<MenuDrawerItem> menuDrawerItemArrayList){
        this.context = context;
        this.menuDrawerItemArrayList = menuDrawerItemArrayList;
    }

    @Override
    public int getCount() {
        return this.menuDrawerItemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.menuDrawerItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_list_item, null);
        }

        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        TextView title = (TextView) convertView.findViewById(R.id.title);

        icon.setImageResource(menuDrawerItemArrayList.get(position).getIcon());
        title.setText(menuDrawerItemArrayList.get(position).getTitle());

        return convertView;
    }
}
