package com.irisdame.tradepro.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class JSONHelper {

    int type;
    Context context;

    public JSONHelper(int type, Context context) {
        this.type = type;
        this.context = context;
    }

    public void getT(String url) throws ExecutionException, InterruptedException {
        Log.v("Test", "1");
        DownloadTaskJSON taskJSON = new DownloadTaskJSON();

        taskJSON.execute(url);
        Log.v("Test", "2");
        //Log.v("Test", sss);
    }


    public class DownloadTaskJSON extends AsyncTask<String, Void, String> {

        String data ="";
        String dataParsed = "";
        String singleParsed ="";

        @Override
        protected String doInBackground(String... urls) {
            try {
                Log.v("Test", "3");
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                SharedPreferences namesPreference = context.getSharedPreferences("SECONDTIME", Context.MODE_PRIVATE);

                SharedPreferences.Editor namesEditor = namesPreference.edit();

                namesEditor.clear();
                namesEditor.commit();

                while(line != null){
                    line = bufferedReader.readLine();
                    data = data + line;
                }

                Log.v("Test", "4");
                Log.v("Test", data);

                JSONArray JA = new JSONArray(data);


                String[] fields = TypeCode.getFieldsList(type);
                List<String> names = new ArrayList<>();
                int flag=0;
                int secondFlag = 0;
                for(int i =0 ;i <JA.length(); i++){
                    JSONObject JO = (JSONObject) JA.get(i);

                    if(!names.contains(JO.get(fields[0]).toString())) {
                        secondFlag=0;
                        names.add(JO.get(fields[0]).toString());
                    }

                   // map.put(, 1);

                    for(int j = 0; j<=fields.length-1; j++) {
                        if(JO.has("Error")) {
                            Log.d("ErrorKey", JO.get("Error").toString());
                            flag=1;
                            break;
                        }

                        singleParsed = singleParsed + JO.get(fields[j]) + "\n";

                    }

                    if(names.contains(JO.get(fields[0]).toString()) && secondFlag==1) {

                        String temp = namesPreference.getString(JO.get(fields[0]).toString(), "");

                        temp = temp + singleParsed;
                        namesEditor.putString(JO.get(fields[0]).toString(), temp);
                        namesEditor.commit();

                        singleParsed = "";
                    }

                    if(flag==1) {
                        break;
                    }

                    dataParsed = dataParsed + singleParsed;
                    singleParsed = "";

                    secondFlag = 1;
                }

                Log.v("Test", "5");

                SharedPreferences sharedpreferences = context.getSharedPreferences("JSONDATA", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.commit();

                editor.putString("JSON_STRING", dataParsed);
                editor.commit();

                //Log.v("Test", dataParsed);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return dataParsed;
        }
    }
}
