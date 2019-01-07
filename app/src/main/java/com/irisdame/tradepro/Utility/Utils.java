package com.irisdame.tradepro.Utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.Toast;

import com.irisdame.tradepro.Activities.HomeActivity;
import com.irisdame.tradepro.R;

public class Utils {

    public static Context context;

    public static int noIntentFlag = 0;

    public Utils(Context context) {
        this.context = context;
    }

    public static String proName = "", proCode = "", custName = "", supName = "", fromDate = "", toDate = "";

    public static String BASE_URL = "http://traderepotest.azurewebsites.net/api/repo/Tradepro_test/query/";

    public static void clearSharedPreferences() {
        SharedPreferences sharedpreferences = context.getSharedPreferences("JSONDATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();

        SharedPreferences namesPreference = context.getSharedPreferences("SECONDTIME", Context.MODE_PRIVATE);
        SharedPreferences.Editor namesEditor = namesPreference.edit();
        namesEditor.clear();
        namesEditor.commit();
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String[][] seperateData(String data, int type) {
        String[][] fullData;;
        String[] eachWord = data.split("\n");
        int numberOfLines = eachWord.length;
        int numberOfIterations = numberOfLines/TypeCode.returnSizeOfType(type);

        fullData = new String[numberOfIterations][TypeCode.returnSizeOfType(type)];
        TypeCode.copyData = new String[numberOfIterations][TypeCode.returnSizeOfType(type)];

        int k=0;
        for(int i=0; i<numberOfIterations; i++) {

            int j=0;
            while(j<TypeCode.returnSizeOfType(type)) {
                fullData[i][j] = eachWord[k];
                TypeCode.copyData[i][j] = eachWord[k];
                j++;
                k++;
            }

        }

        return fullData;

    }


    public void navigationDrawerSetup(final Context c, DrawerLayout drawerLayout, ActionBarDrawerToggle toggle, NavigationView navigationView) {

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        Toast.makeText(c, "Home", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        c.startActivity(intent);
                        //context.
                        break;
                    case R.id.nav_about:
                        Toast.makeText(c, "About", Toast.LENGTH_SHORT).show();
                        String url = "http://www.irisdame.com/";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        c.startActivity(i);
                        break;
                    case R.id.nav_contact:
                        Toast.makeText(c, "Contact", Toast.LENGTH_SHORT).show();
                        Intent call = new Intent(Intent.ACTION_DIAL);
                        call.setData(Uri.parse("tel:8888741716"));
                        c.startActivity(call);
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    public static String getQueryUrl(int position) {
        String urlValue = "";
        switch (position) {
            case 0:
                if(Utils.proName.equals("") && Utils.proCode.equals("")) {
                    urlValue = BASE_URL + position;
                }
                if(!Utils.proName.equals("")) {
                    urlValue = BASE_URL + position + "/0/" + "Product_Name" + "/" + Utils.proName;
                }
                if(!Utils.proCode.equals("")) {
                    urlValue = BASE_URL + position + "/0/" + "Product_Code" + "/" + Utils.proCode;
                }
                if(!Utils.proName.equals("") && !Utils.proCode.equals("")) {
                    urlValue = BASE_URL + Utils.proName + "/" + Utils.proCode;
                }
                break;

            case 1:
                if(Utils.proName.equals("")) {
                    urlValue = BASE_URL + position;
                } else {
                    urlValue = BASE_URL + position + "/0/" + "Product_Name" + "/" + Utils.proName;
                }
                break;

            case 2:
                if(Utils.custName.equals("")) {
                    urlValue = BASE_URL + position;
                } else {
                    urlValue = BASE_URL + "0/1/" + "Customer_Name" + "/" + Utils.custName;
                }
                break;

            case 3:
                if(Utils.supName.equals("")) {
                    urlValue = BASE_URL + position;
                } else {
                    urlValue = BASE_URL + "0/2/" + "Supplier_Name" + "/" + Utils.supName;
                }
                break;

            case 4:
                if(!Utils.fromDate.equals("") && !Utils.toDate.equals("")) {
                    urlValue = BASE_URL + "0/3/" + fromDate + "/" + toDate;
                } else {
                    urlValue = BASE_URL + position;
                }
                break;

            case 5:
                urlValue = BASE_URL + position;
                break;
        }

        return urlValue;
    }

    public static void resetFilter() {
        proName = "";
        proCode = "";
        custName = "";
        supName = "";
        fromDate = "";
        toDate = "";
    }
}
