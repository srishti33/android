package com.example.nic.davpublicschool;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.jar.Attributes;

public class registration extends Activity implements
        AdapterView.OnItemSelectedListener{
    String[] class1= { "I","II","III","IV","V","VI","VII","VIII","IX","X","XI","XII"};
    String[] sp= { "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    String[] sp1= { "JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEPT","OCT","NOV","DEC"};
    String[] sp3={"1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017"};
    String[] place ={"Andhra Pradesh","Assam","Arunachal Pradesh","Bangalore","Chennai","Chandigarh","Chattisgarh","Jammu & Kashmir","Himachal Pradesh","Haryana","Gujrat","Rajasthan","Madhya Pradesh","Ranchi","Maharashtra","Karnataka","Kerela","Sikkim","Tamil Nadu","Orrisa","West Bengal","Punjab"};
    EditText editText;
    Button button;
    RadioButton genderradioButton;
    RadioGroup radioGroup;
    DatabaseHelper myDb;
    Button add,view;
    AutoCompleteTextView actv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
         myDb = new DatabaseHelper(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,place);

         actv= (AutoCompleteTextView)findViewById(R.id.act_native);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.BLUE);


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        editText =(EditText) findViewById(R.id.editText);
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        Spinner spin1 = (Spinner) findViewById(R.id.spinner);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        Spinner spin3 = (Spinner) findViewById(R.id.spinner3);
        add =(Button)findViewById(R.id.add);
        view =(Button)findViewById(R.id.btn_view);

        spin.setOnItemSelectedListener(this);



        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editText.getText().toString(),actv.getText().toString());
                        if(isInserted == true)
                            Toast.makeText(registration.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(registration.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );

       viewAll();

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);

        //Creating the ArrayAdapter instance having the list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,class1);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        ArrayAdapter aa1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,sp);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(aa1);

        ArrayAdapter aa2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,sp1);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(aa2);

        ArrayAdapter aa3 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,sp3);
        aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(aa3);


        //Setting the ArrayAdapter data on the Spinner


    }


    public void viewAll() {
        view.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Cursor res = myDb.getAllData();
                            if(res.getCount() == 0) {
                                // show message
                                showMessage("Error","Nothing found");
                                return;
                            }

                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()) {
                                buffer.append("Id :"+ res.getString(0)+"\n");
                                buffer.append("Name :"+ res.getString(1)+"\n");
                                buffer.append("Naitive :"+ res.getString(2)+"\n");
                            }

                            // Show all data
                            showMessage("Data",buffer.toString());
                        }
                    }
            );
        }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        String value1=editText.getText().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
    public void onclickbuttonMethod(){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) findViewById(selectedId);
        if(selectedId==-1){
            Toast.makeText(registration.this,"Nothing selected", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(registration.this, genderradioButton.getText(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), ActivityTwo.class);
            startActivity(i);
        }}
    }

