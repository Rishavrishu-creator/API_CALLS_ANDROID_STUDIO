package com.example.apicalls;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerAdapter1 extends RecyclerView.Adapter<RecyclerAdapter1.MyViewHolder> {
    Context context;
    ArrayList<String> name;
    ArrayList<String> price;
    ArrayList<String> image;
    String category_id;
    ArrayList<String> productid;
    public RecyclerAdapter1(Context context, ArrayList<String> name, ArrayList<String> price, ArrayList<String> image, ArrayList<String> productid,String category_id) {
       this.context=context;
       this.name=name;
       this.image=image;
       this.price=price;
       this.productid=productid;
       this.category_id=category_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample3,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(image.get(position)).into(holder.image);
        holder.text.setText(name.get(position));
        holder.price.setText("Rs "+price.get(position));

        RequestQueue queue= Volley.newRequestQueue(context);
        String url = "https://jbmdemo.in/westwell/westwell/api/assign_product_vendor";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("rishav",response);
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(response);
                    if(jsonObject.getString("data").equals("Product is already added under this Vendor"))
                    {
                       holder.check.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        holder.button.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Some error occurred",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params=new HashMap<String, String>();
                params.put("vendor_id","2");
                params.put("category_id",category_id);
                params.put("product_id",productid.get(position));
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





        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue= Volley.newRequestQueue(context);
                String url = "https://jbmdemo.in/westwell/westwell/api/assign_product_vendor";
                JSONObject params=new JSONObject();
                try {
                    params.put("vendor_id","2");
                    params.put("category_id",category_id);
                    params.put("product_id",productid.get(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String mRequestBody = params.toString();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rishav",response);
                        Toast.makeText(context,"Successfully Added",Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_LONG).show();
                    }
                }){
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
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }

                    @Override
                    public Map<String,String> getHeaders()throws AuthFailureError{  // For Headers
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("Keydata","43564354346767");
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return price.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
            TextView text,price,check;
            Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
             image=itemView.findViewById(R.id.image);
             text=itemView.findViewById(R.id.text);
             price=itemView.findViewById(R.id.price);
             button=itemView.findViewById(R.id.button);
             check=itemView.findViewById(R.id.check);
        }
    }
}
