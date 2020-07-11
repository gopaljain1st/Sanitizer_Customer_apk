package my.springbliss.grocery;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import my.springbliss.grocery.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyorderItemList_Adapter extends RecyclerView.Adapter<MyorderItemList_Adapter.ViewHolder> {

    private Context context;
    private List<Order> personUtils;

    public MyorderItemList_Adapter(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Order pu = personUtils.get(position);
       /* final Myhelper myhelper=new Myhelper(context);
        final SQLiteDatabase database=myhelper.getWritableDatabase();*/
        //holder.itemimage.setImageResource(pu.getImageid());
        Picasso.with(context).load(pu.getImageid()).resize(400,400).centerCrop().into(holder.itemimage);
        holder.product_name.setText(pu.getProductname());
        //holder.brand_name.setText(pu.getQty());
        holder.sprice.setText("\u20B9"+pu.getSellingPrice());
        holder.item_qty.setText(""+pu.getQty());
    }

    @Override
    public int getItemCount() {
        return personUtils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemimage;
        TextView product_name,brand_name,pprice,sprice,item_qty;

        public ViewHolder(View itemView) {
            super(itemView);
            itemimage=itemView.findViewById(R.id.product_img);
            product_name =  itemView.findViewById(R.id.product_name);
            sprice=itemView.findViewById(R.id.selling_price);
            item_qty=itemView.findViewById(R.id.product_qty);
        }
    }
}
