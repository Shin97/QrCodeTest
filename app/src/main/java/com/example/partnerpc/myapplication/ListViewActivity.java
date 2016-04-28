package com.example.partnerpc.myapplication;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.partnerpc.myapplication.model.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;

    private RequestQueue mQueue;
    private ListView listView;
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> info = new ArrayList<String>();
    private String url ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.ListCL);
        listView = (ListView)findViewById(R.id.list_view);

        url = getString(R.string.json_source);
        mQueue = Volley.newRequestQueue(ListViewActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray dataList ;
                        try {
                            dataList = response.getJSONObject("data").getJSONArray("item_list");
                            for(int i=0;i<dataList.length();i++){
                                title.add( dataList.getJSONObject(i).getString("receiver") );
                                info.add( dataList.getJSONObject(i).getString("order_status") );
                            }
                        }catch(JSONException e){
                            Log.e("TAG",e.toString());
                        }
                        Snackbar.make(coordinatorLayout,"取得資料成功!!\n"+title.get(0)+info.get(0),Snackbar.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError ve) {
                Log.e("VolleyError",ve.toString());
                Snackbar.make(coordinatorLayout, ve.toString(), Snackbar.LENGTH_LONG);
            }
        });
        mQueue.add(jsonObjectRequest);


        listView.setAdapter(new MyAdapter(ListViewActivity.this,title,info));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(coordinatorLayout, "你選擇的是: " + title.get(position),Snackbar.LENGTH_LONG).show();
            }
        });
    }


}
