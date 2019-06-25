package com.farhanrozali.cgpacalculator;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewGPAResult extends AppCompatActivity {

    private String noId;
    private int totalGPAScore;

    TextView edtNo, edtSubj1, edtSubj2, edtSubj3, edtSubj4, edtSubj5, edtSubj6, edtSubj7, edtGrade1, edtGrade2,
            edtGrade3, edtGrade4, edtGrade5, edtGrade6, edtGrade7, edtCredit1, edtCredit2, edtCredit3, edtCredit4,
            edtCredit5, edtCredit6, edtCredit7, scoreGPA;
    Button btnEdit;
    String strSubjName, strSubjGrade, strSubjCredit, strGpaTotal;
    int position;
    dbGPA dbMyGPA;

    ArrayList<TextView> edtSubj=new ArrayList<TextView>();
    ArrayList<TextView> edtGrade=new ArrayList<TextView>();
    ArrayList<TextView> edtCredit=new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gparesult);

        edtSubj.add((TextView)findViewById(R.id.edtSubj1));
        edtSubj.add((TextView)findViewById(R.id.edtSubj2));
        edtSubj.add((TextView)findViewById(R.id.edtSubj3));
        edtSubj.add((TextView)findViewById(R.id.edtSubj4));
        edtSubj.add((TextView)findViewById(R.id.edtSubj5));
        edtSubj.add((TextView)findViewById(R.id.edtSubj6));
        edtSubj.add((TextView)findViewById(R.id.edtSubj7));

        edtGrade.add((TextView)findViewById(R.id.edtGrade1));
        edtGrade.add((TextView)findViewById(R.id.edtGrade2));
        edtGrade.add((TextView)findViewById(R.id.edtGrade3));
        edtGrade.add((TextView)findViewById(R.id.edtGrade4));
        edtGrade.add((TextView)findViewById(R.id.edtGrade5));
        edtGrade.add((TextView)findViewById(R.id.edtGrade6));
        edtGrade.add((TextView)findViewById(R.id.edtGrade7));

        edtCredit.add((TextView)findViewById(R.id.edtCredit1));
        edtCredit.add((TextView)findViewById(R.id.edtCredit2));
        edtCredit.add((TextView)findViewById(R.id.edtCredit3));
        edtCredit.add((TextView)findViewById(R.id.edtCredit4));
        edtCredit.add((TextView)findViewById(R.id.edtCredit5));
        edtCredit.add((TextView)findViewById(R.id.edtCredit6));
        edtCredit.add((TextView)findViewById(R.id.edtCredit7));

        edtNo = (TextView) findViewById(R.id.edtNo);
        scoreGPA = (TextView) findViewById(R.id.scoreGPA);
        btnEdit = (Button) findViewById(R.id.fnEdit);

        dbMyGPA = new dbGPA(getApplication());
        Bundle b = getIntent().getExtras();
        Bundle b2 = getIntent().getExtras();
        noId = b.getString("keyid");
        totalGPAScore = b2.getInt("keyid2");
        edtNo.setText(noId);
        getGPAtotal(totalGPAScore);
        getGPAdata();
    }

    public void fnEdit (View vw)
    {
        Bundle b = new Bundle();
        Bundle b2 = new Bundle();
        b.putString("keyid", noId);
        b2.putInt("keyid2", totalGPAScore);
        Intent i = new Intent(ViewGPAResult.this,EditGPAResult.class);
        i.putExtras(b);
        i.putExtras(b2);
        startActivity(i);
        finish();
    }

    public void getGPAtotal (final int id ){
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                Cursor c = dbMyGPA.getReadableDatabase().rawQuery("SELECT * FROM dbmygpa WHERE gpa_id = "+ id +";", null);

                if (c.moveToFirst())
                {
                    do
                    {
                        strGpaTotal = c.getString(c.getColumnIndex("gpa_total"));
                    }while(c.moveToNext());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scoreGPA.setText("Your GPA is: " + strGpaTotal);
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
    }

    public void getGPAdata (){
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                Cursor c = dbMyGPA.getReadableDatabase().rawQuery("SELECT subj_name,subj_gred,subj_credit " +
                        "FROM dbmysubj WHERE subjgpa_no = "+noId+";", null);

                if (c.moveToFirst())
                {
                    do
                    {
                        strSubjName = c.getString(c.getColumnIndex("subj_name"));
                        strSubjGrade = c.getString(c.getColumnIndex("subj_gred"));
                        strSubjCredit = c.getString(c.getColumnIndex("subj_credit"));
                        position = c.getPosition();
                        setText(position, strSubjName,strSubjGrade,strSubjCredit);
                    }while(c.moveToNext());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
    }

    public void setText(int i, String name,String grade,String credit){
        edtSubj.get(i).setText(name);
        edtGrade.get(i).setText(grade);
        edtCredit.get(i).setText(credit);
    }


    @Override
    public void onBackPressed()
    {
        Intent home_intent = new Intent(getApplicationContext(), ListViewGPAResult.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
        finish();
    }
}
