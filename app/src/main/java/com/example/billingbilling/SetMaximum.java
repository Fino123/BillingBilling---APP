package com.example.billingbilling;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetMaximum extends AppCompatActivity {
    private float month,day;
    private float showMaxOfMonth,showMaxOfDay;
    private EditText Inputday;
    private EditText Inputmonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_maximum);
        TextView title = (TextView)findViewById(R.id.title_text);
        title.setText("设置每日/月消费上限");
        Inputmonth = (EditText)findViewById(R.id.inputMaxMonth);
        Inputday = (EditText)findViewById(R.id.inputMaxDay);
        SharedPreferences pref = getSharedPreferences("maximum",MODE_PRIVATE);
        if (pref!=null){
            showMaxOfMonth = pref.getFloat("MaximumOfMonth",0);
            showMaxOfDay = pref.getFloat("MaximumOfDay",0);
            Inputday.setText(String.valueOf(showMaxOfDay));
            Inputmonth.setText(String.valueOf(showMaxOfMonth));
        }


        final SharedPreferences.Editor editor = getSharedPreferences("maximum",MODE_PRIVATE).edit();
        Button button = (Button)findViewById(R.id.setMaxCertain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month = Float.parseFloat(Inputmonth.getText().toString());
                day = Float.parseFloat(Inputday.getText().toString());
                editor.putFloat("MaximumOfMonth",month);
                editor.putFloat("MaximumOfDay",day);
                editor.apply();
                Toast.makeText(SetMaximum.this, "设置成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
