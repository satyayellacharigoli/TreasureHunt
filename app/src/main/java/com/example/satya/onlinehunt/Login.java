package com.example.satya.onlinehunt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.satya.onlinehunt.R.id.forget_password;
import static com.example.satya.onlinehunt.R.id.login;
import static com.example.satya.onlinehunt.R.id.reg;
import static com.example.satya.onlinehunt.R.id.sign_up;
import static com.example.satya.onlinehunt.R.id.username;

public class Login extends AppCompatActivity  implements View.OnClickListener{
            SharedPreferences pref;
            SharedPreferences.Editor editor;
    public static final int CONNECTION_TIMEDOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    Button login,sign_up,forget_password;
    EditText username,password;
    String id,passwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = getApplicationContext().getSharedPreferences("pref",0);
        editor = pref.edit();
        id = pref.getString("id",null);
        if(id!=null){
            Intent chari = new Intent(Login.this,Home.class);
            startActivity(chari);
            finish();
        }
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        sign_up = (Button) findViewById(R.id.sign_up);
        login = (Button) findViewById(R.id.login);
        forget_password = (Button)findViewById(R.id.forget_password);
        login.setOnClickListener(this);
        sign_up.setOnClickListener(this);
        forget_password.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_up:
                Intent satya = new Intent(Login.this,Signup.class);
                startActivity(satya);
                break;
            case R.id.login:
                id = username.getText().toString();
                passwd = password.getText().toString();
                new AsyncLogin().execute(id,passwd);
                break;
            case R.id.forget_password:
                Intent chari = new Intent(Login.this,Forget.class);
                startActivity(chari);
        }
    }
    class AsyncLogin extends AsyncTask<String, String,String>{
        ProgressDialog pdLoading = new ProgressDialog(Login.this);
        HttpURLConnection conn;
        URL url = null;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdLoading.setMessage("Getting inside\nPlease wait....");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params){
            try{
                url = new URL("https://charihunt.000webhostapp.com/hunt/login_app.php");
            }
            catch (MalformedURLException e){
                e.printStackTrace();
                return "exception";
            }
            try {
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEDOUT);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("id",params[0])
                        .appendQueryParameter("password",params[1]);
                String query = builder.build().getEncodedQuery();
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
            }
            catch(Exception e1){
                e1.printStackTrace();
                return "Exception";
            }
            try{
                int response_code = conn.getResponseCode();
                if(response_code == HttpURLConnection.HTTP_OK){
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null)
                    {
                        result.append(line);
                    }
                    return (result.toString());
                }
                else
                {
                    return("unsuccessful");
                }
            }catch (IOException e) {
                e.printStackTrace();
                return "Exception";
            }finally {
                conn.disconnect();
            }
        }
        @Override
        protected void onPostExecute(String result){
            pdLoading.dismiss();
            //Toast.makeText(Login.this,result.toString(),Toast.LENGTH_LONG).show();
            if(result.equalsIgnoreCase("true"))
            {
                Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
                editor.putString("id",id);
                editor.commit();
                //Intent calling to mainActivity
                Intent seetha = new Intent(Login.this,Home.class);
                startActivity(seetha);
                //finishing this activity
                finish();
            }
            else if (result.equalsIgnoreCase("false"))
            {
                // If username and password does not match display a error message
                Toast.makeText(Login.this, "Login failed", Toast.LENGTH_LONG).show();
            }
            else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful"))
            {
                Toast.makeText(Login.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(Login.this, "rey "+result.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}