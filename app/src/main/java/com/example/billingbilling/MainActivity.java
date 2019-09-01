package com.example.billingbilling;

import android.app.IntentService;
import android.content.Intent;
import android.drm.DrmStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.billingbilling.db.Bill;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    private List<Bill> mBills = new ArrayList<Bill>();
    private BillAdapter billAdapter;
    private DrawerLayout mDrawerLayout;
    private String typeString = new String("全部");
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.drawer_list3);
        }
        /*设置recyclerview*/
        recyclerView = (RecyclerView)findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mBills = LitePal.findAll(Bill.class);
        billAdapter = new BillAdapter(mBills);
        recyclerView.setAdapter(billAdapter);

        /*设置FloatingActionButton*/
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add_bill);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddBill.class);
                startActivity(intent);
            }
        });

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_count_cost:
                        Intent intent = new Intent(MainActivity.this,CountSumList.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_add_max:
                        Intent setMax = new Intent(MainActivity.this,SetMaximum.class);
                        startActivity(setMax);
                        break;
                    case R.id.nav_count_average:
                        Intent average_intent = new Intent(MainActivity.this,CountAverage.class);
                        startActivity(average_intent);
                        break;
                    case R.id.nav_count_type:
                        Intent show_type_percent = new Intent(MainActivity.this,ShowTypeByPie.class);
                        startActivity(show_type_percent);
                        break;
                    default:
                }
                menuItem.setChecked(false);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        billAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.all_small_button:
                typeString = new String("全部");
                changeBillList();
                break;
            case R.id.entertainment_small_button:
                typeString = new String("娱乐消费");
                changeBillList();
                break;
            case R.id.medical_small_button:
                typeString = new String("医疗消费");
                changeBillList();
                break;
            case R.id.survival_small_button:
                typeString = new String("生存消费");
                changeBillList();
                break;
            case R.id.water_ele_small_button:
                typeString = new String("水电消费");
                changeBillList();
                break;

            default:
        }
        return true;
    }

    private void changeBillList(){
        if ("全部".equals(typeString)){
            mBills = LitePal.findAll(Bill.class);
            billAdapter = new BillAdapter(mBills);
            recyclerView.setAdapter(billAdapter);
        }else{
            mBills = LitePal.where("type like ?",typeString).find(Bill.class);
            billAdapter = new BillAdapter(mBills);
            recyclerView.setAdapter(billAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_item,menu);
        return true;
    }
}
