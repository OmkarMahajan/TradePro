package com.irisdame.tradepro.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.irisdame.tradepro.R;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmailET;
    private EditText inputPasswordET;
    private Button loginBT;
    private TextView newpassTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmailET = findViewById(R.id.inputEmailET);
        inputPasswordET = findViewById(R.id.inputPasswordET);
        loginBT = findViewById(R.id.loginBT);
        newpassTV = findViewById(R.id.newpassTV);

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                //checkCredentials();
            }
        });

        newpassTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,NewPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkCredentials() {
        String username = inputEmailET.getText().toString();
        String password = inputPasswordET.getText().toString();

        if(username.equals("test") &&
                password.equals("test")) {
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }


}
