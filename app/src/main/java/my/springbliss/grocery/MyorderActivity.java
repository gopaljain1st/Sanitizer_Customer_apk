package my.springbliss.grocery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import my.springbliss.grocery.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyorderActivity extends AppCompatActivity {
    private static final String apiurl="https://digitalcafe.us/springbliss/history.php";
    RecyclerView recyclerView;
    List<Myorder> myorders;
    MyorderAdapter myorderAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Your Order");
        myorders=new ArrayList<>();
        recyclerView=findViewById(R.id.rerere);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyorderActivity.this));

        myorderAdapter=new MyorderAdapter(this,myorders);
        recyclerView.setAdapter(myorderAdapter);
        final SharedPreferences shared = getSharedPreferences("User_Info", MODE_PRIVATE);
        final String personelid=shared.getString("id","").trim();
       //Toast.makeText(this, ""+personelid, Toast.LENGTH_SHORT).show();
        progressDialog=new ProgressDialog(MyorderActivity.this,R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Springbliss");
        progressDialog.setMessage("Please Wait......");
        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //Toast.makeText(AttaAndOtherActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject(response);
                    String sucess = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (sucess.equals("1")) {
                        progressDialog.dismiss();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String orderid=object.getString("order_id");
                            String user_id=object.getString("user_id");
                            String name=object.getString("name");
                            Log.d("Name",name);
                            String address=object.getString("address");
                            String mobile=object.getString("mobile");
                            String deliver_time=object.getString("deliver_time");
                            String item_count=object.getString("item_count");
                            String total_amount=object.getString("total_amount");
                            String order_status=object.getString("order_status");
                            String timmeOfOrder=object.getString("timmeOfOrder");
                            String dateOfOrder=object.getString("dateOfOrder");
                            int amt= Integer.parseInt(total_amount);
                            if(user_id.equals(personelid))
                            {
                                myorders.add(new Myorder(dateOfOrder,timmeOfOrder,orderid,order_status,item_count,""+amt,address));
                                myorderAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyorderActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                 error.printStackTrace();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

        //myorders.add(new Myorder("31/05/2020","15:25","1597534682","Shipped","5","370"));

    }
}
