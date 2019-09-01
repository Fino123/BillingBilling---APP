package com.example.billingbilling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.billingbilling.db.Bill;
import com.example.billingbilling.util.PieView;

import org.litepal.LitePal;

import java.util.List;

public class ShowTypeByPie extends AppCompatActivity {
    private List<Bill> mBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_type_by_pie);
        TextView title = (TextView)findViewById(R.id.title_text);
        title.setText("账单类型分布");
        PieView pieView = (PieView)findViewById(R.id.show_type_by_pie);

    }
}
