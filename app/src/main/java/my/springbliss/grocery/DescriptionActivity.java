package my.springbliss.grocery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import my.springbliss.grocery.R;
import com.squareup.picasso.Picasso;

public class DescriptionActivity extends AppCompatActivity {
TextView item_names,item_price,item_desc,item_title;
Button btn_add;
ImageView item_images;
int count1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Description");
      item_names=findViewById(R.id.item_names);
      item_price=findViewById(R.id.itemprice);
      item_desc=findViewById(R.id.desc);
      item_title=findViewById(R.id.item_title);
      item_images=findViewById(R.id.item_images);
      btn_add=findViewById(R.id.btnadd);
        final String itemname = getIntent().getExtras().getString("itemname");
        String itemtitle = getIntent().getExtras().getString("item_title");
        final String itemprice = getIntent().getExtras().getString("item_price");
        String itemdesc = getIntent().getExtras().getString("item_desc");
        final String item_left_price = getIntent().getExtras().getString("item_left_price");
        final String seller=getIntent().getExtras().getString("sellerid");
        final String image_id=getIntent().getExtras().getString("image_id");
        final String item_id=getIntent().getExtras().getString("item_id");
        final String weight=getIntent().getExtras().getString("weight");

        Picasso.with(this).load(image_id).resize(400,400).centerCrop().into(item_images);
        item_title.setText(itemtitle);
        item_price.setText("Price: "+itemprice);
        item_names.setText(itemname);
        item_desc.setText(itemdesc);
        final Myhelper myhelper=new Myhelper(DescriptionActivity.this);
        final SQLiteDatabase database=myhelper.getWritableDatabase();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_add.getText()=="Added")
                {
                    Toast.makeText(DescriptionActivity.this, "Item Already Added", Toast.LENGTH_SHORT).show();
                }
                else {
                    ContentValues values = new ContentValues();
                    values.put("NAME", itemname);
                    values.put("PRODUCT_PRICE",itemprice);
                    values.put("SELLING_PRICE", item_left_price);
                    values.put("QTY",1);
                    values.put("SELEER_ID",seller);
                    values.put("ITEM_IMAGE",image_id);
                    values.put("ITEMID",item_id);
                    values.put("WEIGHT",weight);
                    database.insert("SPRINGBLISS", null, values);
                    database.close();
                    //holder.add.setText("Remove");
                    btn_add.setText("Added");
                }
            }
        });


        Myhelper myhelper1=new Myhelper(this);
        SQLiteDatabase database1 = myhelper1.getReadableDatabase();
        String sql1 = "select * from SPRINGBLISS";
        Cursor c1 = database1.rawQuery(sql1,null);
        while(c1.moveToNext()){
            count1++;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_cart,menu);
        MenuItem menuItem=menu.findItem(R.id.cart1);
        menuItem.setIcon(Converter.convertLayoutToImage(DescriptionActivity.this,count1,R.drawable.ic_shopping_cart));

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(getApplicationContext(),CartActivity.class));
                return true;
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
        super.onBackPressed();
    }
}
