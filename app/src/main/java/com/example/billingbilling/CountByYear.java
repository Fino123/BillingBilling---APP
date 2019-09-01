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

public class CountByYear extends AppCompatActivity implements View.OnClickListener{
    private String year;
    private EditText input;
    private TextView output;
    private double sum = 0;
    private List<Bill> mBills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_by_year);
        TextView title = (TextView)findViewById(R.id.title_text);
        title.setText("按年份统计");
        Button sumerizeButton = (Button)findViewById(R.id.count_by_year_countbutton);
        input = (EditText)findViewById(R.id.count_by_year_input);
        Button certainButton = (Button)findViewById(R.id.count_by_year_certain);
        output = (TextView)findViewById(R.id.count_by_year_output);
        sumerizeButton.setOnClickListener(this);
        certainButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.count_by_year_countbutton:
                year = input.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mBills = LitePal.where("year like ?",year).find(Bill.class);
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
            case R.id.count_by_year_certain:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }
}
