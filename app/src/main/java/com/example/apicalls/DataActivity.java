package com.example.apicalls;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class DataActivity extends AppCompatActivity {

    Button get_vendor_products,get_categoy,add_category;
    TextView vendor_products,category_products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        get_vendor_products=findViewById(R.id.get_products_vendor);
        get_categoy=findViewById(R.id.get_category);
        add_category=findViewById(R.id.add_category);
        vendor_products=findViewById(R.id.vendor_products);
        category_products=findViewById(R.id.category_products);

        // GET vendor product API
        get_vendor_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue=Volley.newRequestQueue(DataActivity.this);
                String url="https://jbmdemo.in/westwell/westwell/api/vendor_login/get_products_vendor";
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        vendor_products.setText(response);
                        // we can use recycler view and populate our data with a model class
                        // For the time being I am just printing the json response as string
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Toast.makeText(DataActivity.this,"Some error occurred",Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String,String> getParams()  // for query Parameters
                    {
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("vendor_id","2");
                        params.put("page","1");
                        params.put("limit","10");
                        return params;
                    }
                    @Override
                    public Map<String,String> getHeaders()throws AuthFailureError {  // For Headers
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("Content-Type","application/json");
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });


        // GET category API
        get_categoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue= Volley.newRequestQueue(DataActivity.this);
                String url="https://jbmdemo.in/westwell/westwell/api/vendor_login/get_category";
                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       category_products.setText(response);
                       // *********** we can also use recyclerView to populate data
                        // *********** I am only parsing JSON and printing it
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DataActivity.this,"Some error occurred",Toast.LENGTH_LONG).show();
                    }
                });
            queue.add(stringRequest);
            }

        });

        // Adding category API
        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                RequestQueue queue= Volley.newRequestQueue(DataActivity.this);
                String url="https://jbmdemo.in/westwell/westwell/api/vendor_login/get_category";
                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            String category_id = object.getString("category_id");
                            RequestQueue queue1= Volley.newRequestQueue(DataActivity.this);
                            String url1="https://jbmdemo.in/westwell/westwell/api/vendor_login/assign_product_vendor";

                            StringRequest stringRequest1=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(DataActivity.this,"Successfully added product",Toast.LENGTH_LONG).show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                   Toast.makeText(DataActivity.this,"Some error occured",Toast.LENGTH_LONG).show();
                                }
                            }){
                                @Override
                                protected Map<String,String> getParams()  // for query Parameters
                                {
                                    Map<String,String> params=new HashMap<String, String>();
                                    params.put("vendor_id","2");
                                    params.put("product_id","2200");
                                    params.put("category_id",category_id);
                                    return params;
                                }
                                @Override
                                public Map<String,String> getHeaders()throws AuthFailureError{  // For Headers
                                    Map<String,String> params=new HashMap<String, String>();
                                    params.put("Content-Type","application/json");
                                    return params;
                                }
                            };
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // *********** we can also use recyclerView to populate data
                        // *********** I am only parsing JSON and printing it
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DataActivity.this,"Some error occurred",Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest);
            }
        });
    }
}