package my.springbliss.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import my.springbliss.grocery.R;

public class StartActivity extends AppCompatActivity {

    Button login,register,guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        guest=findViewById(R.id.skip);



        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

        SharedPreferences prefs = this.getSharedPreferences("User_Info", Context.MODE_PRIVATE);

        String loginID = prefs.getString("email", "");
        String loginPWD = prefs.getString("password", "");

        if (loginID.length()>0 && loginPWD.length()>0) {
            Intent intent=new Intent(StartActivity.this,MainActivity.class);
            startActivity(intent);
        } else {
            //SHOW PROMPT FOR LOGIN DETAILS
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,RegisterActivity.class));
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
