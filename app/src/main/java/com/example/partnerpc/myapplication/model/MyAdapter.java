package com.example.partnerpc.myapplication.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.partnerpc.myapplication.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private LayoutInflater myInflater;
    ArrayList<String> title = null;
    ArrayList<String> info = null;

    public MyAdapter(Context context, ArrayList<String> title, ArrayList<String> info){
        myInflater = LayoutInflater.from(context);
        this.title = title;
        this.info = info;
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object getItem(int position) {
        return title.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //自訂類別，表達個別listItem中的view物件集合。
        ViewTag viewTag;

        if(convertView == null){
            //取得listItem容器 view
            convertView = myInflater.inflate(R.layout.list, null);

            //建構listItem內容view
            viewTag = new ViewTag(
                    (TextView) convertView.findViewById(R.id.myadaptar_firstLine),
                    (TextView) convertView.findViewById(R.id.myadaptar_secondLine)
            );

            //設置容器內容
            convertView.setTag(viewTag);
        }
        else{
            viewTag = (ViewTag) convertView.getTag();
        }

        //設定標題文字
        viewTag.title.setText(title.get(position));
        //設定內容文字
        viewTag.info.setText(title.get(position));

        return convertView;
    }

    //自訂類別，表達個別listItem中的view物件集合。
    class ViewTag{
        TextView title;
        TextView info;

        public ViewTag(TextView title, TextView info){
            this.title = title;
            this.info = info;
        }
    }
}

