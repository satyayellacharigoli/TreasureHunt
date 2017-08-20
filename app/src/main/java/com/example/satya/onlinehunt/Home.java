package com.example.satya.onlinehunt;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
public class Home extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref = getApplicationContext().getSharedPreferences("pref",0);
        editor = pref.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void start(View V) {
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Home.this);
        myAlertBuilder.setTitle("Continue");
        myAlertBuilder.setPositiveButton("play", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "loading...", Toast.LENGTH_SHORT).show();
                Intent a = new Intent(Home.this, MainActivity.class);
                startActivity(a);
                finish();
            }
        });
        myAlertBuilder.show();
    }
    public void quit_game(View V) {
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Home.this);
            myAlertBuilder.setTitle("Are you sure you want to quit??");
                    myAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),"loading...",Toast.LENGTH_SHORT).show();
                                finish();
                        }
                    });
                    myAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
        myAlertBuilder.show();
    }
    public void help_game(View V){
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Home.this);
        myAlertBuilder.setTitle("Rule no.1\nRule no.2\nRule no.3");
        myAlertBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"loading....",Toast.LENGTH_SHORT).show();
            }
        });
        myAlertBuilder.show();
    }
}