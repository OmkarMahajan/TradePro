package com.irisdame.tradepro.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.irisdame.tradepro.Utility.JSONHelper;
import com.irisdame.tradepro.R;
import com.irisdame.tradepro.Adapter.ReportListAdapter;
import com.irisdame.tradepro.Utility.TypeCode;
import com.irisdame.tradepro.Utility.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.irisdame.tradepro.Utility.Utils.clearSharedPreferences;
import static com.irisdame.tradepro.Utility.Utils.isNetworkAvailable;

public class FinalReportActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private Context activityContext;

    private RecyclerView finalReportRV;
    private ReportListAdapter adapter;

    private int type;
    private String url;
    private String[][] fullData;

    List<String> dataList = new ArrayList<>();

    private int noOfActivity;

    final Calendar myCalendar = Calendar.getInstance();
    EditText toDateET, fromDateET;

    String ret;
    List<String> name = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_report);

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_final_report);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        navigationView = (NavigationView) findViewById(R.id.navigation_view_final_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Utils utils = new Utils(this);
        utils.navigationDrawerSetup(this, drawerLayout, toggle, navigationView);

        finalReportRV = findViewById(R.id.finalReportRV);

        Utils.noIntentFlag = 1;

        if(getIntent() != null) {
            Bundle bundle = getIntent().getExtras();

            type = bundle.getInt("position");
            url = bundle.getString("url");
            noOfActivity = bundle.getInt("noOfActivity");

            if(noOfActivity == 1) {
                fetchData(url);

            } else {
                String[] fields = TypeCode.getFieldsList(type);
                String[] values = new String[fields.length];

                for(int i=0; i<fields.length; i++) {
                    values[i] = bundle.getString(fields[i]);
                }


                String test = "\n";
                for(int i=0; i<fields.length; i++) {
                    test = test + fields[i] + "   " + values[i] + "\n";
                }

                dataList.add(test);

                addSharedPreferencesValuesToList(values[0]);
            }

            adapter = new ReportListAdapter(this, dataList, type, noOfActivity);

            finalReportRV.setAdapter(adapter);
            finalReportRV.setLayoutManager(new LinearLayoutManager(this));
            finalReportRV.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Utils.context = this;
        clearSharedPreferences();

        Utils.resetFilter();

        if(noOfActivity == 2) {
            finish();
        } else {
            Intent intent = new Intent(this, ReportTypeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(noOfActivity == 1) {
            getMenuInflater().inflate(R.menu.search_button, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(toggle.onOptionsItemSelected(item))
            return true;

        switch (id) {
            case R.id.search_button:
                alertDialogSetUp();
                break;
        }

        return true;
    }

    public void alertDialogSetUp() {

        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.search_parameter);

        TextView filterProductNameTV = myDialog.findViewById(R.id.filterProductNameTV);
        TextView filterProductCodeTV = myDialog.findViewById(R.id.filterProductCodeTV);
        TextView filterCustomerNameTV = myDialog.findViewById(R.id.filterCustomerNameTV);
        TextView filterSupplierNameTV = myDialog.findViewById(R.id.filterSupplierNameTV);
        TextView selectDateTV = myDialog.findViewById(R.id.selectDateTV);
        TextView fromDateTV = myDialog.findViewById(R.id.fromDateTV);
        TextView toDateTV = myDialog.findViewById(R.id.toDateTV);

        final EditText filterProductNameET = myDialog.findViewById(R.id.filterProductNameET);
        final EditText filterProductCodeET = myDialog.findViewById(R.id.filterProductCodeET);
        final EditText filterCustomerNameET = myDialog.findViewById(R.id.filterCustomerNameET);
        final EditText filterSupplierNameET = myDialog.findViewById(R.id.filterSupplierNameET);

        toDateET = myDialog.findViewById(R.id.toDateET);
        fromDateET = myDialog.findViewById(R.id.fromDateET);

        switch (type) {
            case 0:
                filterCustomerNameTV.setVisibility(View.GONE);
                filterCustomerNameET.setVisibility(View.GONE);
                filterSupplierNameTV.setVisibility(View.GONE);
                filterSupplierNameET.setVisibility(View.GONE);
                selectDateTV.setVisibility(View.GONE);
                fromDateTV.setVisibility(View.GONE);
                toDateTV.setVisibility(View.GONE);
                toDateET.setVisibility(View.GONE);
                fromDateET.setVisibility(View.GONE);
                break;

            case 1:
                filterProductCodeTV.setVisibility(View.GONE);
                filterProductCodeET.setVisibility(View.GONE);
                filterCustomerNameTV.setVisibility(View.GONE);
                filterCustomerNameET.setVisibility(View.GONE);
                filterSupplierNameTV.setVisibility(View.GONE);
                filterSupplierNameET.setVisibility(View.GONE);
                selectDateTV.setVisibility(View.GONE);
                fromDateTV.setVisibility(View.GONE);
                toDateTV.setVisibility(View.GONE);
                toDateET.setVisibility(View.GONE);
                fromDateET.setVisibility(View.GONE);
                break;

            case 2:
                filterProductNameTV.setVisibility(View.GONE);
                filterProductNameET.setVisibility(View.GONE);
                filterProductCodeTV.setVisibility(View.GONE);
                filterProductCodeET.setVisibility(View.GONE);
                filterSupplierNameTV.setVisibility(View.GONE);
                filterSupplierNameET.setVisibility(View.GONE);
                selectDateTV.setVisibility(View.GONE);
                fromDateTV.setVisibility(View.GONE);
                toDateTV.setVisibility(View.GONE);
                toDateET.setVisibility(View.GONE);
                fromDateET.setVisibility(View.GONE);
                break;

            case 3:
                filterProductNameTV.setVisibility(View.GONE);
                filterProductNameET.setVisibility(View.GONE);
                filterProductCodeTV.setVisibility(View.GONE);
                filterProductCodeET.setVisibility(View.GONE);
                filterCustomerNameTV.setVisibility(View.GONE);
                filterCustomerNameET.setVisibility(View.GONE);
                selectDateTV.setVisibility(View.GONE);
                fromDateTV.setVisibility(View.GONE);
                toDateTV.setVisibility(View.GONE);
                toDateET.setVisibility(View.GONE);
                fromDateET.setVisibility(View.GONE);
                break;

            case 4:
                filterProductNameTV.setVisibility(View.GONE);
                filterProductNameET.setVisibility(View.GONE);
                filterProductCodeTV.setVisibility(View.GONE);
                filterProductCodeET.setVisibility(View.GONE);
                filterCustomerNameTV.setVisibility(View.GONE);
                filterCustomerNameET.setVisibility(View.GONE);
                filterSupplierNameTV.setVisibility(View.GONE);
                filterSupplierNameET.setVisibility(View.GONE);
                break;
        }


        Button okBT = myDialog.findViewById(R.id.okBT);
        Button cancelBT = myDialog.findViewById(R.id.cancelBT);

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelToET();
            }

        };
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelFromET();
            }

        };

        toDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FinalReportActivity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fromDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FinalReportActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();


        okBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.proName = filterProductNameET.getText().toString();
                Utils.proCode = filterProductCodeET.getText().toString();
                Utils.custName = filterCustomerNameET.getText().toString();
                Utils.supName = filterSupplierNameET.getText().toString();
                Utils.toDate = toDateET.getText().toString();
                Utils.fromDate = fromDateET.getText().toString();
                myDialog.dismiss();

                Utils.context = FinalReportActivity.this;
                Utils.clearSharedPreferences();

                String url = Utils.getQueryUrl(type);
                fetchData(url);

                ReportListAdapter adapter = new ReportListAdapter(FinalReportActivity.this, dataList, type, noOfActivity);

                finalReportRV.setAdapter(adapter);
                finalReportRV.setLayoutManager(new LinearLayoutManager(FinalReportActivity.this));
                finalReportRV.addItemDecoration(new DividerItemDecoration(FinalReportActivity.this,
                        DividerItemDecoration.VERTICAL));

                myDialog.dismiss();
            }
        });

        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

    }

    private void updateLabelToET() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        toDateET.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelFromET() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fromDateET.setText(sdf.format(myCalendar.getTime()));
    }



    private void addSharedPreferencesValuesToList(String key) {
        SharedPreferences namesPreference = getSharedPreferences("SECONDTIME", Context.MODE_PRIVATE);

        String data = namesPreference.getString(key, "NO_DATA");

        String[] eachWord = data.split("\n");
        int numberOfLines = eachWord.length;
        int numberOfIterations = numberOfLines/TypeCode.returnSizeOfType(type);

        String[] fields = TypeCode.getFieldsList(type);

        int k=0;

        for(int i=0; i<numberOfIterations; i++) {

            int j=0;
            String test = "\n";
            while(j<TypeCode.returnSizeOfType(type)) {

                test = test + fields[j] + "   " + eachWord[k] + "\n";
                j++;
                k++;
            }

            dataList.add(test);
        }

    }

    private void fetchData(String newUrl) {
        JSONHelper helper = new JSONHelper( type, this);
        SharedPreferences sharedpreferences = getSharedPreferences("JSONDATA", Context.MODE_PRIVATE);
        String ret = sharedpreferences.getString("JSON_STRING", "NO STRING");

        Utils.context = this;

        while(ret.equals("NO STRING")) {
            try {

                if(isNetworkAvailable() == false) {
                    Intent intent = new Intent(FinalReportActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(this, "No Network Available", Toast.LENGTH_SHORT).show();
                    Utils.context = this;
                    clearSharedPreferences();
                    startActivity(intent);
                    finish();
                    break;
                }

                helper.getT(newUrl);
                ret = sharedpreferences.getString("JSON_STRING", "NO STRING");

                Thread.sleep(500);


            } catch (Exception e) {
                e.printStackTrace(); }
        }

        Log.v("STRING", ret);
        dataList.clear();

        fullData = Utils.seperateData(ret, type);

        String[] fields = TypeCode.getFieldsList(type);
        for(int i=0; i<fullData.length; i++) {

            String temp = "\n";
            for(int j=0; j<fullData[i].length; j++) {
                temp = temp + fields[j] + " " + fullData[i][j] + "\n";
            }

            dataList.add(temp);
        }

    }

}
