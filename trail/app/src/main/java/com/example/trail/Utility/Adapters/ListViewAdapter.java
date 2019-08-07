package com.example.trail.Utility.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trail.R;

public class ListViewAdapter extends BaseAdapter {
    private Context mContext;

    public ListViewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;

        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.fragment_lists, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        viewholder.textView.setText("测试" + position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView textView;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.card_title);
        }
    }
}
