package com.example.billingbilling;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.billingbilling.db.Bill;

import org.litepal.LitePal;
import org.w3c.dom.Text;

public class ShowBill extends AppCompatActivity {
    private Bill mbill;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill);
        TextView title = (TextView)findViewById(R.id.title_text);
        title.setText("账单信息如下");
        id = getIntent().getIntExtra("Id",0);
        mbill = LitePal.find(Bill.class,id);
        TextView showTime = (TextView)findViewById(R.id.show_bill_time);
        showTime.setText(mbill.getYear()+"年"+mbill.getMonth()+"月"+mbill.getDay()+"日");
        TextView showCost = (TextView)findViewById(R.id.show_bill_cost);
        showCost.setText(mbill.getCost()+"元");
        TextView showType = (TextView)findViewById(R.id.show_bill_type);
        showType.setText(mbill.getType());
        TextView showWay = (TextView)findViewById(R.id.show_bill_way);
        showWay.setText(mbill.getWay());

        Button deleteBill = (Button)findViewById(R.id.delete_bill_button);
        deleteBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"你确定要删除账单吗",Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LitePal.delete(Bill.class,mbill.getId());
                                Intent intent = new Intent(ShowBill.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }).show();
            }
        });

        Button changeBill = (Button)findViewById(R.id.change_bill_button);
        changeBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowBill.this,ChangeBill.class);
                intent.putExtra("Id",mbill.getId());
                startActivity(intent);
            }
        });
    }
}
