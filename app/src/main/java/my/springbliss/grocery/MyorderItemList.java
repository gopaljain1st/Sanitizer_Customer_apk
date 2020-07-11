package my.springbliss.grocery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
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

public class MyorderItemList extends AppCompatActivity {
    private static final String apiurl="https://digitalcafe.us/springbliss/historyorderitem.php";
    RecyclerView recyclerView;
    ArrayList<Order> orders;
    TextView subtotal,total,statusOrder,shipping_charge,Shipping_address;
    MyorderItemList_Adapter orderAdapter;
    ProgressDialog progressDialog;
    int subt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder_item_list);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("My Order Item List");

        orders=new ArrayList<>();



        final String orderidd = getIntent().getExtras().getString("orderId").trim();
        final String orderstatus = getIntent().getExtras().getString("orderStatus").trim();
        final int ordertotal = Integer.parseInt(getIntent().getExtras().getString("ordertotal").trim());
        final String address = getIntent().getExtras().getString("shipping_address").trim();

        recyclerView=findViewById(R.id.rerere);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyorderItemList.this));
        statusOrder=findViewById(R.id.statusorder);
        Shipping_address=findViewById(R.id.shippingaddresssss);
        subtotal=findViewById(R.id.subtotal);
        total=findViewById(R.id.total);
        shipping_charge=findViewById(R.id.shipping_charge);

        Shipping_address.setText(address);
        statusOrder.setText(orderstatus);

         orderAdapter=new MyorderItemList_Adapter(this,orders);
         recyclerView.setAdapter(orderAdapter);
        progressDialog=new ProgressDialog(MyorderItemList.this,R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Springbliss");
        progressDialog.setMessage("Please Wait......");
        progressDialog.show();

        StringRequest request=new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                  //  Toast.makeText(MyorderItemList.this, "" + response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject(response);
                    String sucess = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (sucess.equals("1")) {
                        progressDialog.dismiss();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            //Toast.makeText(MyorderItemList.this, ""+object, Toast.LENGTH_SHORT).show();
                            String item_id=object.getString("item_id");
                            String orderid=object.getString("order_id");
                            if(orderid.equals(orderidd))
                            {
                            String names=object.getString("item_name");
                            String item_price=object.getString("item_price");
                            String item_qty=object.getString("item_qty");
                            String seller_id=object.getString("seller_id");
                            String item_image = object.getString("itemimage");
                            String u = "https://digitalcafe.us/springbliss/images/" + item_image;
                            int price= Integer.parseInt(item_price);
                            int qqty=Integer.parseInt(item_qty);
                            subt=subt+(price*qqty);
                                //subt=price;
                            //Toast.makeText(MyorderItemList.this, ""+names, Toast.LENGTH_SHORT).show();
                           // Log.e("Price "+i,""+subt);
                                orders.add(new Order(u,names,item_price,qqty,Integer.parseInt(item_id),Integer.parseInt(seller_id)));
                                orderAdapter.notifyDataSetChanged();
                                shipping_charge.setText(""+(ordertotal-subt));
                                subtotal.setText("\u20B9"+""+subt);
                                total.setText("\u20B9"+""+(ordertotal));
                            }

                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(MyorderItemList.this, ""+e, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyorderItemList.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
        //orders.add(new Order(R.drawable.masala2,"demo","450",2,3,5));
        //Toast.makeText(this, ""+subt, Toast.LENGTH_SHORT).show();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

}
