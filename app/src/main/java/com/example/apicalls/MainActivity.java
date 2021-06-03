package com.example.apicalls;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText phone_number;
    EditText password;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone_number=findViewById(R.id.phone_number);
        password=findViewById(R.id.password);
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(phone_number.getText().toString()))
                {
                    phone_number.setError("Please enter");
                    return;
                }
                if(TextUtils.isEmpty(password.getText().toString()))
                {
                    password.setError("Please enter");
                   return;
                }
               if(phone_number.getText().toString().length()>10 || phone_number.getText().toString().length()<10)
               {
                   Toast.makeText(MainActivity.this,"Phone number should be of 10 digits"
                   ,Toast.LENGTH_LONG).show();
                   return;
               }
               if(password.getText().toString().length()<6)
               {
                   password.setError("Please enter more than 6 digit password");
                   return;
               }
               String pattern ="^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=])$";
               Pattern pattern1;
               pattern1=Pattern.compile(pattern);
               if(!pattern1.matcher(password.getText().toString()).matches())
               {
                   Toast.makeText(MainActivity.this,"Invalid password",Toast.LENGTH_LONG).show();
                   return;
               }
               login(phone_number.getText().toString(),password.getText().toString());
            }
        });
    }

    // using Volley Library for all GET and POST requests
    public void login(String phone_number,String password)
    {
        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        JSONObject params=new JSONObject();
        try {
            params.put("password",password);
            params.put("player_id","28bc3041-8515-4f64-aa1a-6ba30de8ed41");
            params.put("phone_number",phone_number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String mRequestBody = params.toString();
        String url = "https://jbmdemo.in/westwell/westwell/api/vendor_login";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("rishav",response);
                Toast.makeText(MainActivity.this,"Successfully Logged In",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,DataActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 Toast.makeText(MainActivity.this,"Some error occurred",Toast.LENGTH_LONG).show();
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
}