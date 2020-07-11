package my.springbliss.grocery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import my.springbliss.grocery.R;

import java.util.List;

public class MyorderAdapter extends RecyclerView.Adapter<MyorderAdapter.ViewHolder> {

    private Context context;
    private List<Myorder> personUtils;

    public MyorderAdapter(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorder_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Myorder pu = personUtils.get(position);

        holder.order_date.setText(pu.getDate());
        holder.order_time.setText(pu.getTime());
        holder.order_id.setText(pu.order_id);
        holder.order_status.setText(pu.status);
        holder.total_item.setText(pu.getTotal_item());
        holder.amount.setText(pu.getAmount());

        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                Intent intent=new Intent(context,MyorderItemList.class);
                intent.putExtra("orderId",pu.getOrder_id());
                intent.putExtra("orderStatus",pu.getStatus());
                intent.putExtra("ordertotal",pu.getAmount());
                intent.putExtra("shipping_address",pu.getAddress());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return personUtils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemClickListner itemClickListner;
        TextView order_date,order_time,order_id,order_status,total_item,amount;

        public ViewHolder(View itemView) {
            super(itemView);
            order_date=itemView.findViewById(R.id.order_date);
            order_time =  itemView.findViewById(R.id.order_time);
            order_id=itemView.findViewById(R.id.orderId);
            order_status=itemView.findViewById(R.id.status);
            total_item=itemView.findViewById(R.id.total_savings);
            amount=itemView.findViewById(R.id.payableAmount);
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
