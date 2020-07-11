package my.springbliss.grocery;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Sanitizer_Adapter extends RecyclerView.Adapter<Sanitizer_Adapter.GridViewHolder> {


    Context context;
    List<SanitizerModel> attaAndOtherModelList;
    List<SanitizerModel> attaAndOtherModelListAll;

    public Sanitizer_Adapter(Context context, List<SanitizerModel> attaAndOtherModelList) {
        this.context = context;
        this.attaAndOtherModelList = attaAndOtherModelList;
       attaAndOtherModelListAll=new ArrayList<>();
       attaAndOtherModelListAll.addAll(attaAndOtherModelList);
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_card_for_atta_and_other,parent,false);
        GridViewHolder gridViewHolder=new GridViewHolder(view);
        return  gridViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GridViewHolder holder, int position) {
        try
        {
        final SanitizerModel attaAndOtherModel = attaAndOtherModelList.get(position);

        final Myhelper myhelper = new Myhelper(context);
        final SQLiteDatabase database = myhelper.getWritableDatabase();
        final SQLiteDatabase db=myhelper.getReadableDatabase();
        final int[] number = {1};
        holder.minus_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number[0] == 1) {
                    holder.item_count.setText("" + number[0]);
                }

                if (number[0] > 1) {

                    number[0] = number[0] - 1;
                    holder.item_count.setText("" + number[0]);
                }
            }
        });
        holder.add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // attaAndOtherModel.setQuantity(item+1);

                number[0] = number[0] + 1;
                holder.item_count.setText("" + number[0]);
            }
        });

            String divide=attaAndOtherModel.getItemLeftPrice();

            holder.item_quantity.setText(attaAndOtherModel.getItemQuantity());
        holder.item_price.setText("Out Price: \u20B9" + attaAndOtherModel.getItemPrice());
        holder.item_left_price.setText("MRP: \u20B9" + attaAndOtherModel.getItemLeftPrice());
        holder.item_left_price.setPaintFlags(holder.item_left_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.item_name.setText(attaAndOtherModel.getItemName());

            double mrp = Double.parseDouble(attaAndOtherModel.getItemLeftPrice());
            double out_price = Double.parseDouble(attaAndOtherModel.getItemPrice());
            float res= (float) (mrp-out_price);

            int str1 = Integer.parseInt(divide);
            long div=(long) ((res*100)/str1);
            holder.discount_rate.setText(div+"  %");

        Picasso.with(context).load(attaAndOtherModel.getItemImage()).resize(400, 400).centerCrop().into(holder.item_image);
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.add.getText() == "Added") {
                   Toast.makeText(context, "Item Already Added", Toast.LENGTH_SHORT).show();
                } else {
                    String sql1 = "select count(*) from SPRINGBLISS where ITEMID="+attaAndOtherModel.getItem_id();
                    Cursor cur=db.rawQuery(sql1,null);
                    if (cur != null) {
                        cur.moveToFirst();                       // Always one row returned.
                        if (cur.getInt (0) == 0) {               // Zero count means empty table.
                            Toast.makeText(context, ""+cur.getInt(0), Toast.LENGTH_SHORT).show();
                            ContentValues values = new ContentValues();
                            values.put("NAME", attaAndOtherModel.getItemName());
                            values.put("PRODUCT_PRICE", attaAndOtherModel.getItemLeftPrice());
                            values.put("SELLING_PRICE", attaAndOtherModel.getItemPrice());
                            values.put("QTY", number[0]);
                            values.put("SELEER_ID", attaAndOtherModel.getSeller_id().trim());
                            values.put("ITEM_IMAGE", attaAndOtherModel.getItemImage());
                            values.put("ITEMID",attaAndOtherModel.getItem_id());
                            values.put("WEIGHT",attaAndOtherModel.getItemQuantity());

                            database.insert("SPRINGBLISS", null, values);
                            database.close();
                            holder.add.setText("Added");
                        }
                        else
                            Toast.makeText(context, "Item Already Added", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                Intent intent = new Intent(context, DescriptionActivity.class);
                intent.putExtra("itemname", attaAndOtherModel.getItemName() + " " + attaAndOtherModel.getItemQuantity());
                intent.putExtra("item_title", attaAndOtherModel.getItemName());
                intent.putExtra("item_price", attaAndOtherModel.getItemPrice());
                intent.putExtra("item_left_price", attaAndOtherModel.getItemLeftPrice());
                intent.putExtra("item_desc", attaAndOtherModel.getItem_desc());
                intent.putExtra("sellerid", attaAndOtherModel.getSeller_id());
                intent.putExtra("image_id", attaAndOtherModel.getItemImage());
                intent.putExtra("item_id", attaAndOtherModel.getItem_id());
                intent.putExtra("weight", attaAndOtherModel.getItemQuantity());
                context.startActivity(intent);
            }
        });
    }
        catch (Exception e)
        {
            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return attaAndOtherModelList.size();
    }



    public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemClickListner itemClickListner;
        ImageView item_image,add_item,minus_item;
        TextView item_name,item_quantity,item_left_price,item_price,item_count,discount_rate;
        Button add;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image=itemView.findViewById(R.id.item_image);
            item_name=itemView.findViewById(R.id.item_name);
            item_left_price=itemView.findViewById(R.id.item_price_left);
            item_price=itemView.findViewById(R.id.item_price);
            item_quantity=itemView.findViewById(R.id.item_quantity);
            item_count=itemView.findViewById(R.id.item_count);
            add_item=itemView.findViewById(R.id.add_icon);
            add=itemView.findViewById(R.id.btn_add);
            minus_item=itemView.findViewById(R.id.substract_icon);
            discount_rate=itemView.findViewById(R.id.discount_rate);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {

            this.itemClickListner.onItemClickListner(view,getLayoutPosition());
        }

        public void setItemClickListner(ItemClickListner ic){
            this.itemClickListner=ic;
        }


    }

}
