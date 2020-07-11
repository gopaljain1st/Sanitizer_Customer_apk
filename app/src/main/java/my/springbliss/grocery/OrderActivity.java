package my.springbliss.grocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import my.springbliss.grocery.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Order> orders,order_for_data;
    String orderId,time;
    String useraddress;
    int count=0,totalPrice=0;
    Button placeorder,btnok;
    TextView subtotal,totals,or,select_address,cash_on,self,homed,address_shipping,shippingaddress,shipping_charge;
    RadioGroup radioGroup;
    RadioButton radioButton,ddtt;
    EditText selected_address;
    RelativeLayout addres,deliveryttimees;
    boolean flag=true;
    String new_Address;
    Myhelper myhelper;
    //String notification="https://digitalcafe.us/springbliss/sendMail.php";
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
        address_shipping=findViewById(R.id.address_shiping);
        shipping_charge=findViewById(R.id.shippingcharge);
        shippingaddress=findViewById(R.id.shippingaddress);
        self=findViewById(R.id.self);
        homed=findViewById(R.id.homed);
        placeorder=findViewById(R.id.placeorder);
        radioGroup=findViewById(R.id.radioGroup);
        myhelper=new Myhelper(this);
        //deliverytype=findViewById(R.id.selectt);
        or=findViewById(R.id.or);
        btnok=findViewById(R.id.btnok);
        selected_address=findViewById(R.id.selected_address);
        select_address=findViewById(R.id.select_your_address);
        cash_on=findViewById(R.id.cashon);
        addres=findViewById(R.id.addresss);
        deliveryttimees=findViewById(R.id.delivery_time);
        orders=new ArrayList<>();
        order_for_data=new ArrayList<>();
        orderId=""+System.currentTimeMillis();
        time="home";
        subtotal=findViewById(R.id.subtotals);
        totals=findViewById(R.id.total);

        Date cc = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat tf=new SimpleDateFormat("HH:mm:ss");
        final String formattedDate = df.format(cc);
        final String formattime=tf.format(cc);
        // Toast.makeText(this, ""+formattime, Toast.LENGTH_SHORT).show();

        recyclerView=findViewById(R.id.rerere);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderActivity.this));

        final SharedPreferences shared = getSharedPreferences("User_Info", MODE_PRIVATE);
        final String address=shared.getString("address","");
        useraddress=address;
        SQLiteDatabase database = myhelper.getReadableDatabase();
        String sql = "select * from SPRINGBLISS";
        Cursor c = database.rawQuery(sql,null);
        while(c.moveToNext()){
            String name =c.getString(1);
            String product_price =c.getString(2);
            String selling_price =c.getString(3);
            int qty = c.getInt(4);
            String seller_id=c.getString(5);
            String item_image=c.getString(6);
            int id = Integer.parseInt(c.getString(7));
            String weight=c.getString(8);
            if(!selling_price.equals(""))
                totalPrice+=(Integer.parseInt(selling_price) * qty);
            Log.d("selling price",selling_price);
            Order item = new Order(item_image,name,"","\u20B9"+product_price,"\u20B9"+selling_price,qty);
            String username=shared.getString("name","");
            String usermobile=shared.getString("mobile","");
            String z=item_image.replace("https://digitalcafe.us/springbliss/images/","");
            Order itemss=new Order(id,Integer.parseInt(seller_id),name,selling_price,qty,username,usermobile,useraddress,z,"Recieved",weight);
            order_for_data.add(itemss);
            orders.add(item);
            count++;
        }
        recyclerView.setAdapter(new OrderAdapter(OrderActivity.this,orders));
        subtotal.setText("\u20B9"+totalPrice);
        totals.setText("\u20B9"+(totalPrice+30));

        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totals.setText("\u20B9"+(totalPrice));
                useraddress="Self Pickup";
                flag=false;
                shipping_charge.setText("Rs 0");
                btnok.setVisibility(View.GONE);
                select_address.setVisibility(View.GONE);
                placeorder.setVisibility(View.VISIBLE);
                or.setVisibility(View.GONE);
                select_address.setVisibility(View.GONE);
                cash_on.setVisibility(View.GONE);
                self.setBackgroundColor(Color.parseColor("#009471"));
                self.setTextColor(Color.parseColor("#ffffff"));
                addres.setVisibility(View.GONE);
                deliveryttimees.setVisibility(View.GONE);
                homed.setBackgroundColor(Color.parseColor("#ffffff"));
                homed.setTextColor(Color.parseColor("#000000"));
            }
        });
        homed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=true;
                totals.setText("\u20B9"+(totalPrice+30));
                placeorder.setVisibility(View.VISIBLE);
                or.setVisibility(View.VISIBLE);
                shipping_charge.setText("Rs 30");
                select_address.setVisibility(View.VISIBLE);
                shippingaddress.setVisibility(View.VISIBLE);
                useraddress=address;
                shippingaddress.setText(useraddress);
                select_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnok.setVisibility(View.VISIBLE);
                        selected_address.setVisibility(View.VISIBLE);
                        //Toast.makeText(OrderActivity.this, ""+useraddress, Toast.LENGTH_SHORT).show();
                    }
                });
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        useraddress=selected_address.getText().toString();
                        shippingaddress.setText(useraddress);
                        selected_address.setVisibility(View.GONE);
                        btnok.setVisibility(View.GONE);
                    }
                });
                cash_on.setVisibility(View.VISIBLE);
                homed.setBackgroundColor(Color.parseColor("#009471"));
                homed.setTextColor(Color.parseColor("#ffffff"));
                self.setBackgroundColor(Color.parseColor("#ffffff"));
                self.setTextColor(Color.parseColor("#000000"));
                addres.setVisibility(View.VISIBLE);
                deliveryttimees.setVisibility(View.VISIBLE);
                int radioId=radioGroup.getCheckedRadioButtonId();
                radioButton=findViewById(radioId);
                time= radioButton.getText().toString();
            }
        });
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(OrderActivity.this);
                pd.setTitle("Uploading Item....");
                pd.show();
                HashMap<String,ArrayList<Order>> hm = new HashMap<>();
                hm.put("order",order_for_data);
                Gson gson = new Gson();
                final String jsonData = gson.toJson(hm);

                // Log.v("JsonData", "onClick: "+jsonData );
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderActivity.this);
                alertDialogBuilder.setMessage("Are you sure,You wanted to Place Order");

                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Toast.makeText(OrderActivity.this,"You clicked yesbutton",Toast.LENGTH_LONG).show();
                        String url = "https://digitalcafe.us/springbliss/proceed_order.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            {
                                // Log.d("responseorder",response);
                                //Log.d("jsondata",jsonData);
                                // pd.dismiss();
                                Toast.makeText(OrderActivity.this, "Order Succesfully Placed"+response, Toast.LENGTH_SHORT).show();
                                //Log.d("response", "response" + response);
                                pd.dismiss();
                                SQLiteDatabase database=myhelper.getWritableDatabase();
                                database.execSQL("delete from  SPRINGBLISS");
                                database.close();
                                Intent intent=new Intent(OrderActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }   , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                                Toast.makeText(OrderActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("allItems",jsonData);
                                map.put("orderId",orderId);
                                map.put("userId",shared.getString("id",""));
                                map.put("name",shared.getString("name",""));
                                map.put("mobile",shared.getString("mobile",""));
                                map.put("address",useraddress);
                                map.put("deliveryTime",time);
                                map.put("itemCount",""+count);
                                if (flag)
                                    map.put("totalPrice",""+(totalPrice+30));
                                else
                                    map.put("totalPrice",""+(totalPrice));
                                map.put("orderStatus","Received");
                                map.put("timeOfOrder",""+formattime);
                                map.put("dateOfOrder",""+formattedDate);
                                map.put("sellerId","1");
                                return map;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(OrderActivity.this);
                        requestQueue.add(stringRequest);
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
    }
    public void checkButton(){
        int radioId=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);

    }
}
