package com.example.apicalls;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class Add2 extends AppCompatActivity {

    String category_id,name1;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerAdapter1 adapter;
    ArrayList<String> name=new ArrayList<>();
    ArrayList<String> price=new ArrayList<>();
    ArrayList<String> image=new ArrayList<>();
    ArrayList<String> productid=new ArrayList<>();
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add2);
       category_id=getIntent().getStringExtra("category_id");
       name1=getIntent().getStringExtra("name");
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recycler);

        //recyclerView.setLayoutManager(layoutManager);
        //adapter=new RecyclerAdapter(DataActivity.this);
        gridLayoutManager=new GridLayoutManager(Add2.this,2);
        getSupportActionBar().setTitle(name1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Add2.this,Add.class));
                finish();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(Add2.this);
        String url = "https://jbmdemo.in/westwell/westwell/api/category_product";
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
                        String product_id = jsonObject.getJSONArray("data").getJSONObject(i).getString("product_id");
                        name.add(name1);
                        price.add(price1);
                        image.add(image1);
                        productid.add(product_id);
                    }
                    recyclerView.setLayoutManager(gridLayoutManager);
                    adapter=new RecyclerAdapter1(Add2.this,name,price,image,productid,category_id);
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
                Toast.makeText(Add2.this,"Some error occurred",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params=new HashMap<String, String>();
                params.put("vendor_id","2");
                params.put("page","1");
                params.put("limit","5");
                params.put("category_id",category_id);
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