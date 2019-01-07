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
import com.irisdame.tradepro.Utility.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.irisdame.tradepro.Utility.Utils.clearSharedPreferences;
import static com.irisdame.tradepro.Utility.Utils.isNetworkAvailable;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReportListAdapter adapter;
    int type;
    String url;
    String[][] fullData;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private int noOfActivity;

    final Calendar myCalendar = Calendar.getInstance();
    EditText toDateET, fromDateET;

    String ret;
    List<String> name = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_list);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        navigationView = (NavigationView) findViewById(R.id.navigation_view_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Utils utils = new Utils(this);
        utils.navigationDrawerSetup(this, drawerLayout, toggle, navigationView);

        recyclerView = findViewById(R.id.recyclerView);

        Bundle bundle = getIntent().getExtras();

        type = bundle.getInt("position");
        url = bundle.getString("url");
        noOfActivity = bundle.getInt("noOfActivity");

        fetchData(url);

        adapter = new ReportListAdapter(this, name, type, noOfActivity);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.noIntentFlag = 0;
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Utils.context = this;
        clearSharedPreferences();

        Utils.resetFilter();

        Intent intent = new Intent(this, ReportTypeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_button, menu);
        return true;
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
                new DatePickerDialog(ListActivity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fromDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ListActivity.this, date2, myCalendar
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

                Utils.context = ListActivity.this;
                Utils.clearSharedPreferences();
                String url = Utils.getQueryUrl(type);
                fetchData(url);

                ReportListAdapter adapter = new ReportListAdapter(ListActivity.this, name, type, noOfActivity);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                recyclerView.addItemDecoration(new DividerItemDecoration(ListActivity.this,
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


    @Override
    protected void onDestroy() {
        Utils.context = this;
        clearSharedPreferences();
        super.onDestroy();
    }

    private void fetchData(String newUrl) {
        JSONHelper helper = new JSONHelper( type, this);
        SharedPreferences sharedpreferences = getSharedPreferences("JSONDATA", Context.MODE_PRIVATE);
        ret = sharedpreferences.getString("JSON_STRING", "NO STRING");

        Utils.context = this;

        while(ret.equals("NO STRING")) {
            try {

                if(isNetworkAvailable() == false) {
                    Intent intent = new Intent(ListActivity.this, HomeActivity.class);
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

        fullData = Utils.seperateData(ret, type);

        name.clear();
        for(int i=0; i<fullData.length; i++) {
            name.add(fullData[i][0]);
        }
    }
}
