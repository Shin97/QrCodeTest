package com.example.partnerpc.myapplication;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;

    private ListView listView;
    private String[] list = {"鋼鐵人","蝙蝠俠","美國隊長","黑寡婦"};
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.ListCL);

        listView = (ListView)findViewById(R.id.list_view);
        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "你選擇的是" + list[position], Toast.LENGTH_SHORT).show();
                Snackbar.make(coordinatorLayout, "你選擇的是: " + list[position],Snackbar.LENGTH_LONG).show();
            }
        });

    }
}
