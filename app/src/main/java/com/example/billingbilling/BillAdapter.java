package com.example.billingbilling;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.billingbilling.db.Bill;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder>{
    private List<Bill> mBills;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView typeImage ;
        TextView billTime;
        View billView;

        public ViewHolder(View view){
            super(view);
            billView = view;
            typeImage = (ImageView)view.findViewById(R.id.type_image);
            billTime = (TextView)view.findViewById(R.id.bill_time_text);
        }
    }

    public BillAdapter(List<Bill> billList){
        mBills = billList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bill_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.typeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Bill bill = mBills.get(position);
                Toast.makeText(view.getContext(), bill.getType(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.billView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Bill bill = mBills.get(position);
                Intent intent = new Intent(viewGroup.getContext(),ShowBill.class);
                intent.putExtra("Id",bill.getId());
                viewGroup.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Bill bill = mBills.get(position);

        if ("医疗消费".equals(bill.getType())){
            viewHolder.typeImage.setImageResource(R.mipmap.medical_image);
        }else if ("生存消费".equals(bill.getType())){
            viewHolder.typeImage.setImageResource(R.mipmap.survial_image);
        }else if ("水电消费".equals(bill.getType())){
            viewHolder.typeImage.setImageResource(R.mipmap.electric_water_image);
        }else if ("娱乐消费".equals(bill.getType())){
            viewHolder.typeImage.setImageResource(R.mipmap.entertainment_image);
        }

        viewHolder.billTime.setText("消费日期："+bill.getYear()+"年"+bill.getMonth()+"月"+bill.getDay()+"日");
    }

    @Override
    public int getItemCount() {
        return mBills.size();
    }

}
