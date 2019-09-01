package com.example.billingbilling;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.billingbilling.db.Bill;

import org.litepal.LitePal;

import java.util.List;

public class CountByMonth extends AppCompatActivity implements View.OnClickListener{
    private String year;
    private String month;
    private EditText input_year;
    private EditText input_month;
    private TextView output;
    private double sum = 0;
    private List<Bill> mBills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_by_month);
        TextView title = (TextView)findViewById(R.id.title_text);
        title.setText("按月份统计");
        Button sumerizeButton = (Button)findViewById(R.id.count_by_month_countbutton);
        input_year = (EditText)findViewById(R.id.count_by_month_input_year);
        Button certainButton = (Button)findViewById(R.id.count_by_month_certain);
        output = (TextView)findViewById(R.id.count_by_month_output);
        input_month = (EditText)findViewById(R.id.count_by_month_input_month);
        sumerizeButton.setOnClickListener(this);
        certainButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.count_by_month_countbutton:
                year = input_year.getText().toString();
                month = input_month.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mBills = LitePal.where("year like ? and month like ?",year,month).find(Bill.class);
                        for (Bill bill : mBills){
                            sum += bill.getCost();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                output.setText(String.format("%.3f 元",sum));
                            }
                        });
                    }
                }).start();
                break;
            case R.id.count_by_month_certain:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }
}

