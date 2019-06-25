package com.farhanrozali.cgpacalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fnAddGPA (View view){
        Intent intent = new Intent(this,AddNewExamResult.class);
        startActivity(intent);
    }

    public void fnViewGPA (View view){
        Intent intent = new Intent(this,ListViewGPAResult.class);
        startActivity(intent);
    }
}
