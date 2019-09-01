package com.example.billingbilling;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CountSumList extends AppCompatActivity implements View.OnClickListener{
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_sum_list);
        TextView title = (TextView)findViewById(R.id.title_text);
        title.setText("请选择统计方式");
        Button countByYear = (Button)findViewById(R.id.count_by_year);
        Button countByMonth = (Button) findViewById(R.id.count_by_month);
        Button countByDay = (Button)findViewById(R.id.count_by_day);
        countByDay.setOnClickListener(this);
        countByMonth.setOnClickListener(this);
        countByYear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.count_by_year:
                intent = new Intent(CountSumList.this,CountByYear.class);
                break;
            case R.id.count_by_month:
                intent = new Intent(this,CountByMonth.class);
                break;
            case R.id.count_by_day:
                intent = new Intent(this,CountByDay.class);
                break;
        }
        startActivity(intent);
    }
}
