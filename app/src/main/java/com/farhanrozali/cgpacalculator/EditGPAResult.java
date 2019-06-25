package com.farhanrozali.cgpacalculator;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditGPAResult extends AppCompatActivity {

    private String noId;
    private int totalGPAScore;

    Spinner spnGrade1, spnCredit1, spnGrade2, spnCredit2, spnGrade3, spnCredit3, spnGrade4,
            spnCredit4, spnGrade5, spnCredit5, spnGrade6, spnCredit6, spnGrade7, spnCredit7;
    EditText edtSub1, edtSub2, edtSub3, edtSub4, edtSub5, edtSub6, edtSub7;
    Double fnlGrade1, fnlGrade2, fnlGrade3, fnlGrade4, fnlGrade5, fnlGrade6, fnlGrade7;
    Double total1, total2, totalGPA;
    String strSubjName, strSubjGrade, strSubjCredit, strGpaTotal;
    TextView scoreGPA, edtCurrNo;
    int position, subid1;

    ArrayList<TextView> edtSubj=new ArrayList<TextView>();
    ArrayList<Spinner> spnGrade=new ArrayList<Spinner>();
    ArrayList<Spinner> spnCredit=new ArrayList<Spinner>();
    ArrayList<Integer> subID=new ArrayList<Integer>();

    WebServiceCall wsc = new WebServiceCall();
    JSONObject jsnObj = new JSONObject();
    dbGPA dbMyGPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gparesult);

        edtSubj.add((TextView)findViewById(R.id.edtSub1));
        edtSubj.add((TextView)findViewById(R.id.edtSub2));
        edtSubj.add((TextView)findViewById(R.id.edtSub3));
        edtSubj.add((TextView)findViewById(R.id.edtSub4));
        edtSubj.add((TextView)findViewById(R.id.edtSub5));
        edtSubj.add((TextView)findViewById(R.id.edtSub6));
        edtSubj.add((TextView)findViewById(R.id.edtSub7));

        spnGrade.add((Spinner) findViewById(R.id.spnGrade1));
        spnGrade.add((Spinner)findViewById(R.id.spnGrade2));
        spnGrade.add((Spinner)findViewById(R.id.spnGrade3));
        spnGrade.add((Spinner)findViewById(R.id.spnGrade4));
        spnGrade.add((Spinner)findViewById(R.id.spnGrade5));
        spnGrade.add((Spinner)findViewById(R.id.spnGrade6));
        spnGrade.add((Spinner)findViewById(R.id.spnGrade7));

        spnCredit.add((Spinner)findViewById(R.id.spnCredit1));
        spnCredit.add((Spinner)findViewById(R.id.spnCredit2));
        spnCredit.add((Spinner)findViewById(R.id.spnCredit3));
        spnCredit.add((Spinner)findViewById(R.id.spnCredit4));
        spnCredit.add((Spinner)findViewById(R.id.spnCredit5));
        spnCredit.add((Spinner)findViewById(R.id.spnCredit6));
        spnCredit.add((Spinner)findViewById(R.id.spnCredit7));

        edtSub1 = (EditText) findViewById(R.id.edtSub1);edtSub2 = (EditText) findViewById(R.id.edtSub2);
        edtSub3 = (EditText) findViewById(R.id.edtSub3);edtSub4 = (EditText) findViewById(R.id.edtSub4);
        edtSub5 = (EditText) findViewById(R.id.edtSub5);edtSub6 = (EditText) findViewById(R.id.edtSub6);
        edtSub7 = (EditText) findViewById(R.id.edtSub7);
        spnGrade1 = (Spinner) findViewById(R.id.spnGrade1);spnGrade2 = (Spinner) findViewById(R.id.spnGrade2);
        spnGrade3 = (Spinner) findViewById(R.id.spnGrade3);spnGrade4 = (Spinner) findViewById(R.id.spnGrade4);
        spnGrade5 = (Spinner) findViewById(R.id.spnGrade5);spnGrade6 = (Spinner) findViewById(R.id.spnGrade6);
        spnGrade7 = (Spinner) findViewById(R.id.spnGrade7);
        spnCredit1 = (Spinner) findViewById(R.id.spnCredit1);spnCredit2 = (Spinner) findViewById(R.id.spnCredit2);
        spnCredit3 = (Spinner) findViewById(R.id.spnCredit3);spnCredit4 = (Spinner) findViewById(R.id.spnCredit4);
        spnCredit5 = (Spinner) findViewById(R.id.spnCredit5);spnCredit6 = (Spinner) findViewById(R.id.spnCredit6);
        spnCredit7 = (Spinner) findViewById(R.id.spnCredit7);
        edtCurrNo = (TextView) findViewById(R.id.edtCurrNo);
        scoreGPA = (TextView) findViewById(R.id.scoreGPA);

        dbMyGPA = new dbGPA(getApplication());
        Bundle b = getIntent().getExtras();
        Bundle b2 = getIntent().getExtras();
        noId = b.getString("keyid");
        totalGPAScore = b2.getInt("keyid2");
        edtCurrNo.setText(noId);
        getGPAtotal (totalGPAScore);
        getGPAdata();
    }

    public void fnUpdate(View vw){
        if(totalGPA == null){
            String str = "Please click calculate button first!";
            fnDisplayToastMsg(str);
        }else{
            updateGPArecord();
            returnHome();
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), ListViewGPAResult.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
        finish();
    }

    public void updateGPArecord(){

        Runnable run = new Runnable() {
            @Override
            public void run() {
                Double dblGPA = Double.parseDouble(String.format("%.2f", totalGPA));

                String subj1 = edtSub1.getText().toString();
                String subj2 = edtSub2.getText().toString();
                String subj3 = edtSub3.getText().toString();
                String subj4 = edtSub4.getText().toString();
                String subj5 = edtSub5.getText().toString();
                String subj6 = edtSub6.getText().toString();
                String subj7 = edtSub7.getText().toString();

                String strGrd1 = spnGrade1.getSelectedItem().toString();
                String strGrd2 = spnGrade2.getSelectedItem().toString();
                String strGrd3 = spnGrade3.getSelectedItem().toString();
                String strGrd4 = spnGrade4.getSelectedItem().toString();
                String strGrd5 = spnGrade5.getSelectedItem().toString();
                String strGrd6 = spnGrade6.getSelectedItem().toString();
                String strGrd7 = spnGrade7.getSelectedItem().toString();

                String strCrd1 = spnCredit1.getSelectedItem().toString();
                String strCrd2 = spnCredit2.getSelectedItem().toString();
                String strCrd3 = spnCredit3.getSelectedItem().toString();
                String strCrd4 = spnCredit4.getSelectedItem().toString();
                String strCrd5 = spnCredit5.getSelectedItem().toString();
                String strCrd6 = spnCredit6.getSelectedItem().toString();
                String strCrd7 = spnCredit7.getSelectedItem().toString();

                String strSubQry0 = "UPDATE dbmygpa SET gpa_total = '" + dblGPA + "' WHERE gpa_no = " + noId + ";";
                dbMyGPA.fnExecuteSql(strSubQry0, getApplicationContext());

                String strSubQry1 = "UPDATE dbmysubj SET subj_name = '" + subj1 + "', subj_gred = '" + strGrd1 + "', subj_credit = '" + strCrd1 + "' WHERE subj_id = " + subID.get(0) + ";";
                dbMyGPA.fnExecuteSql(strSubQry1, getApplicationContext());

                String strSubQry2 = "UPDATE dbmysubj SET subj_name = '" + subj2 + "', subj_gred = '" + strGrd2 + "', subj_credit = '" + strCrd2 + "' WHERE subj_id = " + subID.get(1) + ";";
                dbMyGPA.fnExecuteSql(strSubQry2, getApplicationContext());

                String strSubQry3 = "UPDATE dbmysubj SET subj_name = '" + subj3 + "', subj_gred = '" + strGrd3 + "', subj_credit = '" + strCrd3 + "' WHERE subj_id = " + subID.get(2) + ";";
                dbMyGPA.fnExecuteSql(strSubQry3, getApplicationContext());

                String strSubQry4 = "UPDATE dbmysubj SET subj_name = '" + subj4 + "', subj_gred = '" + strGrd4 + "', subj_credit = '" + strCrd4 + "' WHERE subj_id = " + subID.get(3) + ";";
                dbMyGPA.fnExecuteSql(strSubQry4, getApplicationContext());

                String strSubQry5 = "UPDATE dbmysubj SET subj_name = '" + subj5 + "', subj_gred = '" + strGrd5 + "', subj_credit = '" + strCrd5 + "' WHERE subj_id = " + subID.get(4) + ";";
                dbMyGPA.fnExecuteSql(strSubQry5, getApplicationContext());

                String strSubQry6 = "UPDATE dbmysubj SET subj_name = '" + subj6 + "', subj_gred = '" + strGrd6 + "', subj_credit = '" + strCrd6 + "' WHERE subj_id = " + subID.get(5) + ";";
                dbMyGPA.fnExecuteSql(strSubQry6, getApplicationContext());

                String strSubQry7 = "UPDATE dbmysubj SET subj_name = '" + subj7 + "', subj_gred = '" + strGrd7 + "', subj_credit = '" + strCrd7 + "' WHERE subj_id = " + subID.get(6) + ";";
                dbMyGPA.fnExecuteSql(strSubQry7, getApplicationContext());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String strMsg = "Information successfully updated!";
                        fnDisplayToastMsg(strMsg);
                    }
                });
            }
        };
        Thread thrSave = new Thread(run);
        thrSave.start();
    }


    public int getGrdPos (String pos)
    {
        if (pos.equals("A"))
        {
            return 1;
        }else if (pos.equals("A-"))
        {
            return 2;
        }else if (pos.equals("B+"))
        {
            return 3;
        }else if (pos.equals("B"))
        {
            return 4;
        }else if (pos.equals("B-"))
        {
            return 5;
        }else if (pos.equals("C+"))
        {
            return 6;
        }else if (pos.equals("C"))
        {
            return 7;
        }else if (pos.equals("C-"))
        {
            return 8;
        }else if (pos.equals("D+"))
        {
            return 9;
        }else if (pos.equals("D"))
        {
            return 10;
        }else if (pos.equals("E"))
        {
            return 11;
        }else
        {
            return 0;
        }
    }

    public void fnCalculate(View vw)
    {
        String strGrade1 = spnGrade1.getSelectedItem().toString();
        String strGrade2 = spnGrade2.getSelectedItem().toString();
        String strGrade3 = spnGrade3.getSelectedItem().toString();
        String strGrade4 = spnGrade4.getSelectedItem().toString();
        String strGrade5 = spnGrade5.getSelectedItem().toString();
        String strGrade6 = spnGrade6.getSelectedItem().toString();
        String strGrade7 = spnGrade7.getSelectedItem().toString();

        final Double dblCredit1 = Double.parseDouble(spnCredit1.getSelectedItem().toString());
        final Double dblCredit2 = Double.parseDouble(spnCredit2.getSelectedItem().toString());
        final Double dblCredit3 = Double.parseDouble(spnCredit3.getSelectedItem().toString());
        final Double dblCredit4 = Double.parseDouble(spnCredit4.getSelectedItem().toString());
        final Double dblCredit5 = Double.parseDouble(spnCredit5.getSelectedItem().toString());
        final Double dblCredit6 = Double.parseDouble(spnCredit6.getSelectedItem().toString());
        final Double dblCredit7 = Double.parseDouble(spnCredit7.getSelectedItem().toString());

        final String dblGrade1 = getGradeValue(strGrade1);
        final String dblGrade2 = getGradeValue(strGrade2);
        final String dblGrade3 = getGradeValue(strGrade3);
        final String dblGrade4 = getGradeValue(strGrade4);
        final String dblGrade5 = getGradeValue(strGrade5);
        final String dblGrade6 = getGradeValue(strGrade6);
        final String dblGrade7 = getGradeValue(strGrade7);

        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("selectFn", "fnGetGrade"));

                try{
                    jsnObj = wsc.makeHttpRequest(wsc.fnGetURL(), "POST", params);
                    fnlGrade1 = jsnObj.getDouble(dblGrade1);
                    fnlGrade2 = jsnObj.getDouble(dblGrade2);
                    fnlGrade3 = jsnObj.getDouble(dblGrade3);
                    fnlGrade4 = jsnObj.getDouble(dblGrade4);
                    fnlGrade5 = jsnObj.getDouble(dblGrade5);
                    fnlGrade6 = jsnObj.getDouble(dblGrade6);
                    fnlGrade7 = jsnObj.getDouble(dblGrade7);

                } catch (Exception e){
                    String strMsg = "No internet connection, please turn on your Mobile Data/WiFi!";
                    fnDisplayToastMsg(strMsg);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String strMsg = "Grade credit successfully retrieve and calculated!";
                        fnDisplayToastMsg(strMsg);

                        total1 = (fnlGrade1 * dblCredit1) + (fnlGrade2 * dblCredit2) + (fnlGrade3 * dblCredit3) +
                                (fnlGrade4 * dblCredit4) + (fnlGrade5 * dblCredit5) + (fnlGrade6 * dblCredit6) +
                                (fnlGrade7 * dblCredit7);
                        total2 = (dblCredit1 + dblCredit2 + dblCredit3 + dblCredit4 +
                                dblCredit5 + dblCredit6 + dblCredit7);
                        totalGPA = total1 / total2;

                        scoreGPA.setText("Your GPA is: " + String.format("%.2f", totalGPA));
                    }
                });
            }
        };
        Thread thr = new Thread(run);
        thr.start();
    }

    public void fnDisplayToastMsg (String strText)
    {
        Toast tst = Toast.makeText(getApplicationContext(), strText, Toast.LENGTH_SHORT);
        tst.show();
    }

    public String getGradeValue(String grade) {

        if (grade.equals("A")) {
            return "gradeA";
        } else if (grade.equals("A-")) {
            return "gradeA-";
        } else if (grade.equals("B+")) {
            return "gradeB+";
        } else if (grade.equals("B")) {
            return "gradeB";
        } else if (grade.equals("B-")) {
            return "gradeB-";
        } else if (grade.equals("C+")) {
            return "gradeC+";
        } else if (grade.equals("C")) {
            return "gradeC";
        } else if (grade.equals("C-")) {
            return "gradeC-";
        } else if (grade.equals("D+")) {
            return "gradeD+";
        } else if (grade.equals("D")) {
            return "gradeD";
        }  else {
            return "gradeE";
        }
    }

    public void fnDelete(View vw)
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                String strQry1 = "DELETE FROM dbmysubj WHERE subjgpa_no = '"+noId+"';";
                String strQry2 = "DELETE FROM dbmygpa WHERE gpa_no = '"+noId+"';";

                dbMyGPA.fnExecuteSql(strQry1, getApplicationContext());
                dbMyGPA.fnExecuteSql(strQry2, getApplicationContext());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast showSuccess = Toast.makeText(getApplicationContext(), "Information successfully deleted!\n", Toast.LENGTH_SHORT);
                        showSuccess.show();
                        returnHome();
                    }
                });
            }
        };
        Thread thrSave = new Thread(run);
        thrSave.start();
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
                Cursor c = dbMyGPA.getReadableDatabase().rawQuery("SELECT subj_id,subj_name,subj_gred,subj_credit " +
                        "FROM dbmysubj WHERE subjgpa_no = "+noId+";", null);

                if (c.moveToFirst())
                {
                    do
                    {
                        subid1 = c.getInt(c.getColumnIndex("subj_id"));
                        strSubjName = c.getString(c.getColumnIndex("subj_name"));
                        strSubjGrade = c.getString(c.getColumnIndex("subj_gred"));
                        strSubjCredit = c.getString(c.getColumnIndex("subj_credit"));
                        position = c.getPosition();
                        setText(position, strSubjName,strSubjGrade,strSubjCredit,subid1);
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

    public void setText(int i, String name,String grade,String credit, int subjid){
        edtSubj.get(i).setText(name);
        spnGrade.get(i).setSelection(getGrdPos(grade));
        spnCredit.get(i).setSelection(Integer.parseInt(credit));
        subID.add(subjid);
    }

    public void onBackPressed()
    {
        Intent home_intent = new Intent(getApplicationContext(), ListViewGPAResult.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
        finish();
    }
}
