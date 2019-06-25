package com.farhanrozali.cgpacalculator;

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

public class AddNewExamResult extends AppCompatActivity {

    Spinner spnGrade1, spnCredit1, spnGrade2, spnCredit2, spnGrade3, spnCredit3, spnGrade4,
            spnCredit4, spnGrade5, spnCredit5, spnGrade6, spnCredit6, spnGrade7, spnCredit7;
    EditText edtSub1, edtSub2, edtSub3, edtSub4, edtSub5, edtSub6, edtSub7;
    Double fnlGrade1, fnlGrade2, fnlGrade3, fnlGrade4, fnlGrade5, fnlGrade6, fnlGrade7;
    Double total1, total2, totalGPA;
    Integer gpaNo;
    TextView scoreGPA, edtNo;
    WebServiceCall wsc = new WebServiceCall();
    JSONObject jsnObj = new JSONObject();
    dbGPA dbMyGPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exam_result);

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
        edtNo = (TextView) findViewById(R.id.edtNo);
        scoreGPA = (TextView) findViewById(R.id.scoreGPA);

        dbMyGPA = new dbGPA(getApplication());

        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                Cursor c = dbMyGPA.getReadableDatabase().rawQuery("SELECT gpa_no FROM dbmygpa;", null);

                if (c.moveToFirst())
                {
                    do
                    {
                        gpaNo = c.getInt(c.getColumnIndex("gpa_no"));
                    }while(c.moveToNext());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(gpaNo == null){
                            edtNo.setText("1");
                        } else {
                            gpaNo += 1;
                            edtNo.setText(String.valueOf(gpaNo));
                        }
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
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
                    String strMsg = "No internet connection, please turn on your Mobile Data/WiFi.";
                    fnDisplayToastMsg(strMsg);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String strMsg = "Grade credit value successfully retrieved and calculated!";
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

    public void fnDisplayToastMsg (String strText)
    {
        Toast tst = Toast.makeText(getApplicationContext(), strText, Toast.LENGTH_SHORT);
        tst.show();
    }

    public void fnSave(View vw){
        if(totalGPA == null){
            String str = "Please click calculate button first!";
            fnDisplayToastMsg(str);
        }else{
            saveGPAdata();
            finish();
        }
    }

    public void saveGPAdata(){
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                String strNo = edtNo.getText().toString();
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

                String strQryGpa = "INSERT INTO dbmygpa (gpa_no, gpa_total) VALUES('"+strNo+"','"+dblGPA+"');";
                dbMyGPA.fnExecuteSql(strQryGpa, getApplicationContext());

                String strQrySubj1 = "INSERT INTO dbmysubj (subj_name, subj_gred, subj_credit, subjgpa_no) " +
                        "VALUES('"+subj1+"','"+strGrd1+"','"+strCrd1+"','"+strNo+"');";
                dbMyGPA.fnExecuteSql(strQrySubj1, getApplicationContext());

                String strQrySubj2 = "INSERT INTO dbmysubj (subj_name, subj_gred, subj_credit, subjgpa_no) " +
                        "VALUES('"+subj2+"','"+strGrd2+"','"+strCrd2+"','"+strNo+"');";
                dbMyGPA.fnExecuteSql(strQrySubj2, getApplicationContext());

                String strQrySubj3 = "INSERT INTO dbmysubj (subj_name, subj_gred, subj_credit, subjgpa_no) " +
                        "VALUES('"+subj3+"','"+strGrd3+"','"+strCrd3+"','"+strNo+"');";
                dbMyGPA.fnExecuteSql(strQrySubj3, getApplicationContext());

                String strQrySubj4 = "INSERT INTO dbmysubj (subj_name, subj_gred, subj_credit, subjgpa_no) " +
                        "VALUES('"+subj4+"','"+strGrd4+"','"+strCrd4+"','"+strNo+"');";
                dbMyGPA.fnExecuteSql(strQrySubj4, getApplicationContext());

                String strQrySubj5 = "INSERT INTO dbmysubj (subj_name, subj_gred, subj_credit, subjgpa_no) " +
                        "VALUES('"+subj5+"','"+strGrd5+"','"+strCrd5+"','"+strNo+"');";
                dbMyGPA.fnExecuteSql(strQrySubj5, getApplicationContext());

                String strQrySubj6 = "INSERT INTO dbmysubj (subj_name, subj_gred, subj_credit, subjgpa_no) " +
                        "VALUES('"+subj6+"','"+strGrd6+"','"+strCrd6+"','"+strNo+"');";
                dbMyGPA.fnExecuteSql(strQrySubj6, getApplicationContext());

                String strQrySubj7 = "INSERT INTO dbmysubj (subj_name, subj_gred, subj_credit, subjgpa_no) " +
                        "VALUES('"+subj7+"','"+strGrd7+"','"+strCrd7+"','"+strNo+"');";
                dbMyGPA.fnExecuteSql(strQrySubj7, getApplicationContext());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String strMsg = "Information successfully saved!";
                        fnDisplayToastMsg(strMsg);
                    }
                });
            }
        };
        Thread thrSave = new Thread(run);
        thrSave.start();
    }

    public void fnReset(View vw)
    {
        edtSub1.setText("");
        edtSub2.setText("");
        edtSub3.setText("");
        edtSub4.setText("");
        edtSub5.setText("");
        edtSub6.setText("");
        edtSub7.setText("");

        spnGrade1.setSelection(0);
        spnGrade2.setSelection(0);
        spnGrade3.setSelection(0);
        spnGrade4.setSelection(0);
        spnGrade5.setSelection(0);
        spnGrade6.setSelection(0);
        spnGrade7.setSelection(0);

        spnCredit1.setSelection(0);
        spnCredit2.setSelection(0);
        spnCredit3.setSelection(0);
        spnCredit4.setSelection(0);
        spnCredit5.setSelection(0);
        spnCredit6.setSelection(0);
        spnCredit7.setSelection(0);

        scoreGPA.setText("Your GPA is: 0.00");
    }
}
