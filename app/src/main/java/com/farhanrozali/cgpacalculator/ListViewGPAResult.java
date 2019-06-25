package com.farhanrozali.cgpacalculator;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewGPAResult extends AppCompatActivity {

    String strGpaId = dbGPA.colGpaId;
    String strGpaNo = dbGPA.colGpaNo;
    String strGpaTotal = dbGPA.colGpaTotal;
    Cursor cursor;
    TextView txtVwGpaSem, txtVwGpaTotal, txtVwGpaID;

    dbGPA dbMyGPA;
    ListView lvExp;
    ArrayList<HashMap<String, String>> alExp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_gparesult);

        dbMyGPA = new dbGPA(getApplicationContext());
        lvExp = (ListView)findViewById(R.id.lstVwExpense);
        alExp = new ArrayList<HashMap<String,String>>();
        txtVwGpaSem = (TextView) findViewById(R.id.txtVwGpaSem);
        getGPAList();

        lvExp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Bundle b = new Bundle();
                Bundle b2 = new Bundle();
                txtVwGpaSem = (TextView) arg1.findViewById(R.id.txtVwGpaSem);
                txtVwGpaID = (TextView) arg1.findViewById(R.id.txtVwGpaID);
                String id = txtVwGpaSem.getText().toString();
                int total = Integer.parseInt(txtVwGpaID.getText().toString());
                b.putString("keyid", id);
                b2.putInt("keyid2", total);
                Intent i = new Intent(ListViewGPAResult.this,ViewGPAResult.class);
                i.putExtras(b);
                i.putExtras(b2);
                startActivity(i);
                finish();
            }
        });

    }

    public void getGPAList()
    {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                String strSql = "SELECT * FROM dbmygpa;";
                cursor = dbMyGPA.getReadableDatabase().rawQuery(strSql, null);
                while(cursor.moveToNext())
                {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(strGpaId, cursor.getString(cursor.getColumnIndex(strGpaId)));
                    map.put(strGpaNo, cursor.getString(cursor.getColumnIndex(strGpaNo)));
                    map.put(strGpaTotal, cursor.getString(cursor.getColumnIndex(strGpaTotal)));

                    alExp.add(map);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter adapter = new SimpleAdapter(ListViewGPAResult.this,alExp,R.layout.listviewgparesult,
                                new String[] {strGpaId,strGpaNo,strGpaTotal},
                                new int [] {R.id.txtVwGpaID,R.id.txtVwGpaSem,R.id.txtVwGpaTotal});

                        lvExp.setAdapter(adapter);
                    }
                });
            }
        };
        Thread thr = new Thread(run);
        thr.start();
    }
}
