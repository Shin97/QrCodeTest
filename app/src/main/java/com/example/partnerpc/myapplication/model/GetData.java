package com.example.partnerpc.myapplication.model;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;


public class GetData extends Activity{

    private JSONObject data;

    public GetData() {  }

    public JSONObject getData(String url,Activity activity) {

        RequestQueue mQueue = Volley.newRequestQueue(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        data = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mQueue.add(jsonObjectRequest);
        return data;
    }

}
