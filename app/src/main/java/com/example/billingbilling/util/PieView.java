package com.example.billingbilling.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.billingbilling.R;
import com.example.billingbilling.db.Bill;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class PieView extends View {
    private int numberOfMedical;
    private int numberOfEntertainment;
    private int numberOfsurvival;
    private int numberOfelec;
    private int sum;
    private double costOfMedical;
    private double costOfEntertainment;
    private double costOfSurvival;
    private double costOfElec;
    private double costSum;

    private Paint paint = new Paint();
    private List<Bill> mBill;
    private Path path = new Path();

    public PieView(Context context){
        super(context);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mBill = LitePal.where("type like ?","医疗消费").find(Bill.class);
        numberOfMedical = mBill.size();
        for (Bill bill:mBill){
            costOfMedical += bill.getCost();
        }
        mBill = LitePal.where("type like ?","娱乐消费").find(Bill.class);
        numberOfEntertainment = mBill.size();
        for (Bill bill:mBill){
            costOfEntertainment += bill.getCost();
        }
        mBill = LitePal.where("type like ?","生存消费").find(Bill.class);
        numberOfsurvival = mBill.size();
        for (Bill bill:mBill){
            costOfSurvival += bill.getCost();
        }
        mBill = LitePal.where("type like ?","水电消费").find(Bill.class);
        numberOfelec = mBill.size();
        for (Bill bill:mBill){
            costOfElec += bill.getCost();
        }

        costSum = costOfElec + costOfSurvival + costOfEntertainment + costOfMedical;
        sum = numberOfelec + numberOfsurvival + numberOfEntertainment + numberOfMedical;

        double anc_medical = 360.0 * numberOfMedical/sum;
        double anc_survival = 360.0 * numberOfsurvival/sum;
        double anc_ele = 360.0 * numberOfelec/sum;
        double anc_entertainment = 360.0 * numberOfEntertainment/sum;

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawArc(200,100,800,500,0,(float)anc_medical,true,paint);
        paint.setColor(Color.BLUE);
        canvas.drawArc(200,100,800,500,(float)anc_medical,(float)anc_ele,true,paint);
        paint.setColor(Color.GREEN);
        canvas.drawArc(200,100,800,500,(float)(anc_medical+anc_ele),
                (float)anc_entertainment,true,paint);
        paint.setColor(Color.YELLOW);
        canvas.drawArc(200,100,800,500,(float)(anc_ele+anc_entertainment+anc_medical),
                (float)anc_survival,true,paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        canvas.drawText("账单数目",100,100,paint);
        canvas.drawText("账单金额",100,550,paint);
        path.moveTo(200,600);
        path.lineTo(200,1000);
        path.rLineTo(800,0);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path,paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawRect(300,(float)(1000-400.0*costOfMedical/costSum),400,1000,paint);
        paint.setColor(Color.BLUE);
        canvas.drawRect(450,(float)(1000-400.0*costOfElec/costSum),550,1000,paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(600,(float)(1000-400.0*costOfEntertainment/costSum),700,1000,paint);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(750,(float)(1000-400.0*costOfSurvival/costSum),850,1000,paint);

        paint.setColor(Color.RED);
        canvas.drawRect(200,1100,250,1150,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("医疗消费",300,1140,paint);
        paint.setColor(Color.BLUE);
        canvas.drawRect(200,1200,250,1250,paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(200,1300,250,1350,paint);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(200,1400,250,1450,paint);

        paint.setColor(Color.BLACK);
        canvas.drawText("水电消费",300,1240,paint);
        canvas.drawText("娱乐消费",300,1340,paint);
        canvas.drawText("生存消费",300,1440,paint);
        canvas.drawText("共"+numberOfMedical +"笔  "+String.format("%.2f",costOfMedical)+"元",550,1140,paint);
        canvas.drawText("共"+numberOfelec +"笔  "+String.format("%.2f",costOfElec)+"元",550,1240,paint);
        canvas.drawText("共"+numberOfEntertainment +"笔  "+String.format("%.2f",costOfEntertainment)+"元",550,1340,paint);
        canvas.drawText("共"+numberOfsurvival +"笔  "+String.format("%.2f",costOfSurvival)+"元",550,1440,paint);
    }

    public double getMedicalPercent(){
        return (double)numberOfMedical/sum;
    }

    public double getSurvivalPercent(){
        return (double)numberOfsurvival/sum;
    }

    public double getEntertainmentPencent(){
        return (double)numberOfEntertainment/sum;
    }

    public double getElectricPercent(){
        return (double)numberOfelec/sum;
    }
}
