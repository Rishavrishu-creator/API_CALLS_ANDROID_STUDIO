package com.example.apicalls;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    LinearLayout linearLayout;
    ArrayList<String> name=new ArrayList<>();
    ArrayList<String> price=new ArrayList<>();
    ArrayList<String> image=new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recycler);
        layoutManager=new LinearLayoutManager(DataActivity.this);
        //recyclerView.setLayoutManager(layoutManager);
        //adapter=new RecyclerAdapter(DataActivity.this);
        linearLayout=findViewById(R.id.add);
        getSupportActionBar().setTitle("Manage Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataActivity.this,Add.class));
                finish();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataActivity.this,MainActivity.class));
                finish();
            }
        });

        RequestQueue queue= Volley.newRequestQueue(DataActivity.this);
        String url = "https://jbmdemo.in/westwell/westwell/api/get_products_vendor";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("rishav",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    for(int i=0;i<jsonObject.getJSONArray("data").length();i++)
                    {
                        String name1=jsonObject.getJSONArray("data").getJSONObject(i).getString("model");
                        String price1=jsonObject.getJSONArray("data").getJSONObject(i).getString("price");
                        String image1=jsonObject.getJSONArray("data").getJSONObject(i).getString("image1");
                        name.add(name1);
                        price.add(price1);
                        image.add(image1);

                    }
                    recyclerView.setLayoutManager(layoutManager);
                    adapter=new RecyclerAdapter(DataActivity.this,name,price,image);
                    recyclerView.setAdapter(adapter);
                    Log.d("rishav",String.valueOf(name));
                    Log.d("rishav",String.valueOf(price));
                    Log.d("rishav",String.valueOf(image));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DataActivity.this,"Some error occurred",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params=new HashMap<String, String>();
                params.put("vendor_id","2");
                params.put("page","1");
                params.put("limit","5");
                return params;
            }
            /*
            @Override
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }
            /*
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
            */


            @Override
            public Map<String,String> getHeaders()throws AuthFailureError{  // For Headers
                Map<String,String> params=new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Keydata","43564354346767");
                return params;
            }
        };
        queue.add(stringRequest);

        
    }
}