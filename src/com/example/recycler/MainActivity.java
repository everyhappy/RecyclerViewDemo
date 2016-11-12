package com.example.recycler;

import java.util.ArrayList;

import com.example.recycler.MyNormalRecyclerAdapter.OnItemClickLitener;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    RecyclerView recyclerView;
    ArrayList<Product> productList;
    public static final int HEADER_RECYCLER_VIEW_ITEM = 0;
    public static final int NORMAL_RECYCLER_VIEW_ITEM = 1;
    private StaggeredGridLayoutManager manager;
    MyNormalRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= (RecyclerView) findViewById(R.id.recycler);
        //layoutManager
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL));
        initData();
        //adapter
        adapter=new MyNormalRecyclerAdapter(productList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new OnItemClickLitener() {

            @Override
            public void onItemClick(View view, int position) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, position + " long click",
                        Toast.LENGTH_SHORT).show();
                        //mAdapter.removeData(position);
            }
            
        });
        setHeader(recyclerView);
        setFooter(recyclerView);
        SpacesItemDecoration spaces = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(spaces);
        
    }

    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.titlelayout, view, false);
        adapter.setHeaderView(header);
    }
    
    private void setFooter(RecyclerView view) {
        View footerView = LayoutInflater.from(this).inflate(R.layout.footerlayout, view, false);
        adapter.setFooterView(footerView);
    }
    private void initData() {
        productList = new ArrayList<Product>();
        Product product;
        for (int i = 0; i < 30; i++) {
            int asd = R.drawable.ic_launcher;
            product = new Product(asd, "" + i);
            productList.add(product);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        Log.d("zcc", "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }
}
