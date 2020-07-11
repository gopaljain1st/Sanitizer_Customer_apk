package my.springbliss.grocery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import my.springbliss.grocery.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
LinearLayout linearLayout;
    RecyclerView recyclerView;
   Button checkout;
   TextView subt,totall,emptycart;
   CustomRecyclerAdapter2 customRecyclerAdapter2;
    List<Item> orders;
int subtotal,count1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Cart");

        checkout=findViewById(R.id.checkout);
        orders=new ArrayList<>();
        linearLayout=findViewById(R.id.main_layout);
        emptycart=findViewById(R.id.emptycart);
        subt=findViewById(R.id.subtotal);
        totall=findViewById(R.id.total);
        recyclerView=findViewById(R.id.rerere);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        Myhelper myhelper=new Myhelper(this);
        SQLiteDatabase database = myhelper.getReadableDatabase();
        String sql = "select * from  SPRINGBLISS";
        Cursor c = database.rawQuery(sql,null);
        Cursor d=database.rawQuery(sql,null);
        orders = new ArrayList<>();
        if(d.moveToFirst()) {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String name = c.getString(1);
                String product_price = c.getString(2);
                String selling_price = c.getString(3);
                int qty = c.getInt(4);
                String seller_id=c.getString(5);
                String item_image=c.getString(6);
                String item_id=c.getString(7);
                String item_weight=c.getString(8);
                Log.d("itemid","item "+item_id);
                Log.d("weight","item_weight "+item_weight);
                subtotal+=(Integer.parseInt(selling_price)*qty);
                Item item = new Item(item_image,id, name, "","\u20B9"+product_price, selling_price, qty);
                orders.add(item);
            }

            final SharedPreferences prefs = this.getSharedPreferences("User_Info", Context.MODE_PRIVATE);
            customRecyclerAdapter2=new CustomRecyclerAdapter2(CartActivity.this, orders);
            //recyclerView.setAdapter(new CustomRecyclerAdapter2(CartActivity.this, orders));
            recyclerView.setAdapter(customRecyclerAdapter2);

            customRecyclerAdapter2.notifyDataSetChanged();
            //subt.setText("\u20B9"+""+ss);
            subt.setText("\u20B9"+""+subtotal);
            totall.setText("\u20B9"+""+(subtotal+30));



            Myhelper myhelper1=new Myhelper(this);
            SQLiteDatabase database1 = myhelper1.getReadableDatabase();
            String sql1 = "select * from SPRINGBLISS";
            Cursor c1 = database1.rawQuery(sql1,null);
            while(c1.moveToNext()){
                count1++;
            }


            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String loginID = prefs.getString("email", "");
                    String loginPWD = prefs.getString("password", "");

                    if (loginID.length() > 0 && loginPWD.length() > 0) {
                        Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                        startActivity(intent);
                    } else {
                        //SHOW PROMPT FOR LOGIN DETAILS
                       // Toast.makeText(CartActivity.this, "Please Login To Continue", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CartActivity.this);
                        alertDialogBuilder.setMessage("Please Login To Continue");
                                alertDialogBuilder.setPositiveButton("Login",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                           Intent intent=new Intent(CartActivity.this,LoginActivity.class);
                                           startActivity(intent);
                                            }
                                        });

                        alertDialogBuilder.setNegativeButton("Sign-Up",new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Intent intent=new Intent(CartActivity.this,RegisterActivity.class);
                                startActivity(intent);
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                }
            });
        }
        else
        {
            emptycart.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
            checkout.setVisibility(View.GONE);
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_cart,menu);
        MenuItem menuItem=menu.findItem(R.id.cart1);
        menuItem.setIcon(Converter.convertLayoutToImage(CartActivity.this,count1,R.drawable.ic_shopping_cart));

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(CartActivity.this,MainActivity.class));
        finish();
    }
    public void resetGraph(Context context)
    {

      //  mBarChart.invalidate();
finish();
startActivity(getIntent());
    }
}
