package com.example.billingbilling;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.billingbilling.db.Bill;
import com.example.billingbilling.util.MyApplication;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.List;

public class AddBill extends AppCompatActivity implements View.OnClickListener{
    private String type;
    private String way;
    private double cost;
    private int year,month,day;
    private Bill mbill = new Bill();
    private TextView inputType;
    private TextView inputWay;
    private EditText editCost;
    private EditText editYear;
    private EditText editMonth;
    private EditText editDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        /*控件：选择消费类型*/
        TextView title = (TextView)findViewById(R.id.title_text);
        title.setText("添加账单");
        Button typeMedical = (Button)findViewById(R.id.type_medical);
        Button typeSurvival = (Button)findViewById(R.id.type_survival);
        Button typeElectric = (Button)findViewById(R.id.type_water_electric);
        Button typeEntertainment = (Button)findViewById(R.id.type_entertainment);
        inputType = (TextView)findViewById(R.id.text_input_type);
        /*控件：选择消费方式*/
        Button wayCash = (Button)findViewById(R.id.way_cash);
        Button wayCard = (Button)findViewById(R.id.way_card);
        Button wayInternet = (Button)findViewById(R.id.way_internet);
        inputWay = (TextView)findViewById(R.id.text_input_way);
        /*控件：输入消费金额*/
        editCost = (EditText)findViewById(R.id.edit_cost);
        /*控件：输入消费时间*/
        editYear = (EditText)findViewById(R.id.edit_year);
        editMonth = (EditText)findViewById(R.id.edit_month);
        editDay = (EditText)findViewById(R.id.edit_day);
        /*控件：创建账单*/
        Button createBill = (Button)findViewById(R.id.button_create_bill);

        /*注册监听事件*/
        typeMedical.setOnClickListener(this);
        typeSurvival.setOnClickListener(this);
        typeElectric.setOnClickListener(this);
        typeEntertainment.setOnClickListener(this);
        wayCard.setOnClickListener(this);
        wayCash.setOnClickListener(this);
        wayInternet.setOnClickListener(this);
        createBill.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.type_medical:
                type = new String("医疗消费");
                inputType.setText(type);
                break;
            case R.id.type_entertainment:
                type = new String("娱乐消费");
                inputType.setText(type);
                break;
            case R.id.type_survival:
                type = new String("生存消费");
                inputType.setText(type);
                break;
            case R.id.type_water_electric:
                type = new String("水电消费");
                inputType.setText(type);
                break;
            case R.id.way_card:
                way = new String("银行卡支付");
                inputWay.setText(way);
                break;
            case R.id.way_cash:
                way = new String("现金支付");
                inputWay.setText(way);
                break;
            case R.id.way_internet:
                way = new String("网络支付");
                inputWay.setText(way);
                break;
            case R.id.button_create_bill:
                year = Integer.parseInt(editYear.getText().toString());
                month = Integer.parseInt(editMonth.getText().toString());
                day = Integer.parseInt(editDay.getText().toString());
                cost = Double.parseDouble(editCost.getText().toString());

                mbill.setCost(cost);
                mbill.setDate(year,month,day);
                mbill.setWay(way);
                mbill.setType(type);
                mbill.save();
                CheckMaximum(mbill);
                Toast.makeText(this, "创建成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddBill.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void CheckMaximum(final Bill bill){
        new Thread(new Runnable() {
            private List<Bill> bills;
            private float max_of_month;
            private float max_of_day;
            private float cost_of_day = 0;
            private float cost_of_month = 0;
            @Override
            public void run() {
                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                SharedPreferences preferences = getSharedPreferences("maximum",MODE_PRIVATE);
                if (preferences==null){
                    return;
                }
                max_of_day = preferences.getFloat("MaximumOfDay",0);
                max_of_month = preferences.getFloat("MaximumOfMonth",0);
                bills = LitePal.where("year like ? and month like ?",String.valueOf(bill.getYear()),
                        String.valueOf(bill.getMonth())).find(Bill.class);
                for (Bill bill1 : bills){
                    cost_of_month += (float)bill1.getCost();
                }
                if (cost_of_month > max_of_month){
                    Notification notification = new NotificationCompat.Builder(MyApplication.getContext())
                            .setContentTitle("账单金额超过限度")
                            .setContentText(mbill.getYear()+"年"+mbill.getMonth()+"月限度 "+max_of_month+"元 已超过，请合理消费")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.warning_small)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.warning))
                            .build();
                    manager.notify(1,notification);
                }

                bills = LitePal.where("year = ? and month like ? and day like ?",
                        String.valueOf(bill.getYear()),String.valueOf(bill.getMonth()),String.valueOf(bill.getDay())).find(Bill.class);
                for (Bill bill1 : bills){
                    cost_of_day += (float)bill1.getCost();
                }
                if (cost_of_day > max_of_day){
                    Notification notification = new NotificationCompat.Builder(MyApplication.getContext())
                            .setContentTitle("账单金额超过限度")
                            .setContentText(mbill.getYear()+"年"+mbill.getMonth()+"月"+mbill.getDay()+"日限度 "+max_of_day+"元 已超过，请合理消费")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.warning_small)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.warning))
                            .build();
                    manager.notify(2,notification);
                }
            }
        }).start();
    }
}
