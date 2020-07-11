package my.springbliss.grocery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import my.springbliss.grocery.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
   EditText name,email,number,city,address,password,cnfpass;
    Button singnUp;
    TextView alreadyUser;
    ProgressDialog progressDialog;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        number=findViewById(R.id.mobile);
        city=findViewById(R.id.city);
        address=findViewById(R.id.address);
        password=findViewById(R.id.password);
        cnfpass=findViewById(R.id.cpassword);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Register");
        openHelper=new Myhelper(this);


        singnUp=findViewById(R.id.register);
        alreadyUser=findViewById(R.id.loginnext);

        singnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                progressDialog=new ProgressDialog(RegisterActivity.this,R.style.MyAlertDialogStyle);
                progressDialog.setTitle("Springbliss");
                progressDialog.setMessage("Please Wait......");

                progressDialog.show();
if(name.getText().toString().isEmpty() ||email.getText().toString().isEmpty() ||number.getText().toString().isEmpty() ||
        city.getText().toString().isEmpty() ||address.getText().toString().isEmpty() ||password.getText().toString().isEmpty() ||
        cnfpass.getText().toString().isEmpty())
{
    progressDialog.dismiss();
    Toast.makeText(RegisterActivity.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
}
else
{
                String url="https://digitalcafe.us/springbliss/userregistration.php";
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("register done")){
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                            name.setText("");
                            email.setText("");
                            number.setText("");
                            city.setText("");
                            address.setText("");
                            password.setText("");
                            cnfpass.setText("");
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String ,String > map=new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("number",number.getText().toString());
                        map.put("email",email.getText().toString());
                        map.put("city",city.getText().toString());
                        map.put("address",address.getText().toString());
                        map.put("password",password.getText().toString());
                        map.put("token", FirebaseInstanceId.getInstance().getToken());
                        return map;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(RegisterActivity.this);
                requestQueue.add(stringRequest);
            }
              }
        });
        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });



    }
}
