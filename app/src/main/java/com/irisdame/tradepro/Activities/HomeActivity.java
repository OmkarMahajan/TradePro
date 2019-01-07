package com.irisdame.tradepro.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.irisdame.tradepro.R;
import com.irisdame.tradepro.Utility.Utils;

public class HomeActivity extends AppCompatActivity {

    CardView reports;
    CardView support;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        reports = findViewById(R.id.repGrid);
        support = findViewById(R.id.supGrid);

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_home);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Utils utils = new Utils(this);
        utils.navigationDrawerSetup(this, drawerLayout, toggle, navigationView);

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, ReportTypeActivity.class);
                startActivity(intent);


            }
        });


        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogSetup();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        Utils.context = this;
        Utils.clearSharedPreferences();
    }

    private void alertDialogSetup() {
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.contact_info);

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        TextView phoneNo = myDialog.findViewById(R.id.phone_number);
        TextView emailSupport = myDialog.findViewById(R.id.email_support);

        phoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:8888741716"));
                startActivity(call);
                myDialog.dismiss();
            }
        });

        emailSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:test@test.com"));
                startActivity(intent);
                myDialog.dismiss();
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


}
