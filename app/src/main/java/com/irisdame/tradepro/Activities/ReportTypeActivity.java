package com.irisdame.tradepro.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.irisdame.tradepro.R;
import com.irisdame.tradepro.Utility.TypeCode;
import com.irisdame.tradepro.Utility.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.widget.AdapterView.*;
import static com.irisdame.tradepro.Utility.Utils.BASE_URL;
import static com.irisdame.tradepro.Utility.Utils.clearSharedPreferences;

public class ReportTypeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    final Calendar myCalendar = Calendar.getInstance();

    EditText toDateET, fromDateET;
    ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_type);

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_report_type);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        navigationView = (NavigationView) findViewById(R.id.navigation_view_report_type);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Utils utils = new Utils(this);
        utils.navigationDrawerSetup(this, drawerLayout, toggle, navigationView);

        Utils.context = this;
        Utils.clearSharedPreferences();

        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        loadingProgressBar.setVisibility(GONE);

        final String[] mobileArray = TypeCode.getTypes();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mobileArray);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadingProgressBar.setVisibility(VISIBLE);

                Intent intent;
                if(TypeCode.noOfActivity(position) == 2) {
                    intent = new Intent(ReportTypeActivity.this, ListActivity.class);
                    intent.putExtra("noOfActivity", TypeCode.noOfActivity(position));
                } else {
                    intent = new Intent(ReportTypeActivity.this, FinalReportActivity.class);
                    intent.putExtra("noOfActivity", TypeCode.noOfActivity(position));
                }

                intent.putExtra("position", position);

                String urlValue = Utils.getQueryUrl(position);

                intent.putExtra("url", urlValue);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        Utils.noIntentFlag = 0;
    }



    @Override
    protected void onDestroy() {
        Utils.context = this;
        clearSharedPreferences();
        super.onDestroy();
    }

}
