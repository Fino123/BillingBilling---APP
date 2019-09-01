package com.example.billingbilling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.billingbilling.db.Bill;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.List;

public class CountAverage extends AppCompatActivity implements View.OnClickListener {
    private double costSum = 0;
    private double costAverage;
    private int sumDays = 0;
    private int startYear,startMonth,startDay;
    private int endYear,endMonth,endDay;
    private TextView output_sum_day;
    private TextView output_sum_cost;
    private TextView output_average;
    private EditText start_year;
    private EditText start_month;
    private EditText start_day;
    private EditText end_year;
    private EditText end_month;
    private EditText end_day;
    List<Bill> mBills ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_average);
        TextView title = (TextView)findViewById(R.id.title_text);
        title.setText("计算日均消费");
        start_year = (EditText)findViewById(R.id.average_input_start_year);
        start_month = (EditText)findViewById(R.id.average_input_start_month);
        start_day = (EditText)findViewById(R.id.average_input_start_day);
        end_year = (EditText)findViewById(R.id.average_input_end_year);
        end_month = (EditText)findViewById(R.id.average_input_end_month);
        end_day = (EditText)findViewById(R.id.average_input_end_day);
        output_average = (TextView)findViewById(R.id.average_output_average);
        output_sum_cost = (TextView)findViewById(R.id.average_output_sum_cost);
        output_sum_day = (TextView)findViewById(R.id.average_output_sum_day);
        Button certain = (Button)findViewById(R.id.average_certain);
        certain.setOnClickListener(this);
        mBills = LitePal.findAll(Bill.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.average_certain:
                startYear = Integer.parseInt(start_year.getText().toString());
                startMonth = Integer.parseInt(start_month.getText().toString());
                startDay = Integer.parseInt(start_day.getText().toString());
                endYear = Integer.parseInt(end_year.getText().toString());
                endMonth = Integer.parseInt(end_month.getText().toString());
                endDay = Integer.parseInt(end_day.getText().toString());
                final Bill startBill = new Bill();
                startBill.setDate(startYear,startMonth,startDay);
                final Bill endBill = new Bill();
                endBill.setDate(endYear,endMonth,endDay);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (Bill bill:mBills){
                            if (bill.IntervalDays()<=endBill.IntervalDays()&&bill.IntervalDays()>=startBill.IntervalDays()){
                                costSum += bill.getCost();
                            }
                        }
                        sumDays = endBill.IntervalDays() - startBill.IntervalDays() + 1;
                        costAverage = costSum/sumDays;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                output_sum_day.setText(new Integer(sumDays).toString() + "  天" );
                                output_sum_cost.setText(String.format("%.4f",costSum) + "  元");
                                output_average.setText(String.format("%.4f",costAverage));
                                costSum = 0;
                            }
                        });

                    }
                }).start();
                break;

            default:
        }
    }
}
