package com.example.billingbilling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.billingbilling.db.Bill;

import org.litepal.LitePal;
import org.w3c.dom.Text;

public class ChangeBill extends AppCompatActivity implements View.OnClickListener{
    private String type;
    private String way;
    private double cost;
    private int year,month,day;
    private TextView inputType;
    private TextView inputWay;
    private EditText editCost;
    private EditText editYear;
    private EditText editMonth;
    private EditText editDay;
    private Bill mbill;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_bill);
        TextView title = (TextView)findViewById(R.id.title_text);
        title.setText("修改账单");
        /*控件：选择消费类型*/
        Button typeMedical = (Button)findViewById(R.id.change_type_medical);
        Button typeSurvival = (Button)findViewById(R.id.change_type_survival);
        Button typeElectric = (Button)findViewById(R.id.change_type_water_electric);
        Button typeEntertainment = (Button)findViewById(R.id.change_type_entertainment);
        inputType = (TextView)findViewById(R.id.change_text_type);
        /*控件：选择消费方式*/
        Button wayCash = (Button)findViewById(R.id.change_way_cash);
        Button wayCard = (Button)findViewById(R.id.change_way_card);
        Button wayInternet = (Button)findViewById(R.id.change_way_internet);
        inputWay = (TextView)findViewById(R.id.change_text_input_way);
        /*控件：输入消费金额*/
        editCost = (EditText)findViewById(R.id.change_edit_cost);
        /*控件：输入消费时间*/
        editYear = (EditText)findViewById(R.id.change_edit_year);
        editMonth = (EditText)findViewById(R.id.change_edit_month);
        editDay = (EditText)findViewById(R.id.change_edit_day);
        /*控件：创建账单*/
        Button createBill = (Button)findViewById(R.id.change_button_change_bill);

        /*注册监听事件*/
        typeMedical.setOnClickListener(this);
        typeSurvival.setOnClickListener(this);
        typeElectric.setOnClickListener(this);
        typeEntertainment.setOnClickListener(this);
        wayCard.setOnClickListener(this);
        wayCash.setOnClickListener(this);
        wayInternet.setOnClickListener(this);
        createBill.setOnClickListener(this);

        id = getIntent().getIntExtra("Id",0);
        mbill = LitePal.find(Bill.class,id);

        editCost.setText(new Double(mbill.getCost()).toString());
        editYear.setText(new Integer(mbill.getYear()).toString());
        editMonth.setText(new Integer(mbill.getMonth()).toString());
        editDay.setText(new Integer(mbill.getMonth()).toString());
        inputType.setText(mbill.getType());
        inputWay.setText(mbill.getWay());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_type_medical:
                type = new String("医疗消费");
                inputType.setText(type);
                break;
            case R.id.change_type_entertainment:
                type = new String("娱乐消费");
                inputType.setText(type);
                break;
            case R.id.change_type_survival:
                type = new String("生存消费");
                inputType.setText(type);
                break;
            case R.id.change_type_water_electric:
                type = new String("水电消费");
                inputType.setText(type);
                break;
            case R.id.change_way_card:
                way = new String("银行卡支付");
                inputWay.setText(way);
                break;
            case R.id.change_way_cash:
                way = new String("现金支付");
                inputWay.setText(way);
                break;
            case R.id.change_way_internet:
                way = new String("网络支付");
                inputWay.setText(way);
                break;
            case R.id.change_button_change_bill:
                year = Integer.parseInt(editYear.getText().toString());
                month = Integer.parseInt(editMonth.getText().toString());
                day = Integer.parseInt(editDay.getText().toString());
                cost = Double.parseDouble(editCost.getText().toString());

                mbill.setType(type);
                mbill.setWay(way);
                mbill.setCost(cost);
                mbill.setYear(year);
                mbill.setMonth(month);
                mbill.setDay(day);
                mbill.save();

                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChangeBill.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}

