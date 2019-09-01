package com.example.billingbilling.db;

import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;

import java.util.Date;

public class Bill extends LitePalSupport {
   private int id;
   private int year;
   private int month;
   private int day;
   private double cost;
   private String type;
   private String way;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public void setDate(int year,int month,int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public boolean isLaterThan(int year,int month,int day){
        if (this.year>year)
            return true;
        else if (this.year<year)
            return false;
        else{
            if (this.month>month)
                return true;
            else if(this.month<month)
                return false;
            else{
                if (this.day>=day)
                    return true;
                else return false;
            }
        }
    }

    public boolean isEarlierThan(int year,int month,int day){
        if (this.isLaterThan(year,month,day))
            return false;
        else
            return true;
    }

    public int IntervalDays(){
        //返回距离1年1月1日的天数
        int max_days_of_month[]={0,31,28,31,30,31,30,31,31,30,31,30,31};
        int sum = 0;
        sum += day;
        for (int i=month-1;i>0;i--){
            sum += max_days_of_month[i];
            //如果本年的二月是闰年的二月，总天数加1
            if(i==2&&(year%4==0&&year%100!=0)||year%400==0){
                sum += 1;
            }
        }
        sum += 365*year + year/4 + year/400 - year/100;
        return sum;
    }
}
