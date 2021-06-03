package com.example.apicalls;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add extends AppCompatActivity {
    Toolbar toolbar;
    PageAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<String> name=new ArrayList<>();
    ArrayList<String> price=new ArrayList<>();
    ArrayList<String> image=new ArrayList<>();
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recycler);

        //recyclerView.setLayoutManager(layoutManager);
        //adapter=new RecyclerAdapter(DataActivity.this);
        gridLayoutManager=new GridLayoutManager(Add.this,2);
        getSupportActionBar().setTitle("Browse by Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Add.this,DataActivity.class));
                finish();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(Add.this);
        String url = "https://jbmdemo.in/westwell/westwell/api/get_category";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("rishav",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    for(int i=0;i<jsonObject.getJSONArray("data").length();i++)
                    {
                        String name1=jsonObject.getJSONArray("data").getJSONObject(i).getString("name");
                        String price1=jsonObject.getJSONArray("data").getJSONObject(i).getString("category_id");
                        String image1=jsonObject.getJSONArray("data").getJSONObject(i).getString("category_image");
                        name.add(name1);
                        price.add(price1);
                        image.add(image1);

                    }
                    recyclerView.setLayoutManager(gridLayoutManager);
                    adapter=new PageAdapter(Add.this,name,price,image);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add.this,"Some error occurred",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params=new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {  // For Headers
                Map<String,String> params=new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Keydata","43564354346767");
                return params;
            }
        };
        queue.add(stringRequest);

    }
}

