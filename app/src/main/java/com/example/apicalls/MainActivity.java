package com.example.apicalls;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
               if(phone_number.getText().toString().length()>10 || phone_number.getText().toString().length()<10)
               {
                   Toast.makeText(MainActivity.this,"Phone number should be of 10 digits"
                   ,Toast.LENGTH_LONG).show();
                   return;
               }
               if(password.getText().toString().length()>6 || password.getText().toString().length()<6)
               {
                   Toast.makeText(MainActivity.this,"Invalid Password",Toast.LENGTH_LONG).show();
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
        String url = "https://jbmdemo.in/westwell/westwell/api/vendor_login";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
            protected Map<String,String> getParams()  // for query Parameters
            {
                Map<String,String> params=new HashMap<String, String>();
                params.put("phone_number","9874819104");
                params.put("password","As@72877");
                params.put("player_id","28bc3041-8515-4f64-aa1a-6ba30de8ed41");
                return params;
            }
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError{  // For Headers
                Map<String,String> params=new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}