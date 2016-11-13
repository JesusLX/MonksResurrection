package com.limox.jesus.monksresurrection.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.AboutMe_Activity;
import com.limox.jesus.monksresurrection.DashPosts_Activity;
import com.limox.jesus.monksresurrection.Index_Activity;
import com.limox.jesus.monksresurrection.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesus on 13/11/16.
 */

public class StartButtonsAdapter extends BaseAdapter {
    private Context context;
    private List<String> items;

    public StartButtonsAdapter(Context context, List<String> list){
        this.context = context;
        items = new ArrayList<>(list);
    }
    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.cardview_item_sections, parent, false);
        }

        // Set data into the view.
        TextView ivItem = (TextView) rowView.findViewById(R.id.cvis_txvTitle);
        Button btnToActivity = (Button) rowView.findViewById(R.id.cvis_btnToActivity);

        String title = this.items.get(position);
        ivItem.setText(title);
        switch (position) {
            case 0:
                btnToActivity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DashPosts_Activity.class));
                    }
                });
                break;
            case 1:
                btnToActivity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case 2:
                btnToActivity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, AboutMe_Activity.class));
                    }
                });
                break;
        }
        return rowView;
    }
}
