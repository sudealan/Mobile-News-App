package com.example.newsappsudealan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ProgressBar prg;
    private TabLayout tabLayout;
    private List<CategoryModel> categories = new ArrayList<>();
    private List<CategoryFragment> fragments = new ArrayList<>();

    @Override
    public void userFooded(String result{
        Toas.
    })

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_newspaper_24);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("CS310 News");

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        prg = findViewById(R.id.activity_main_progressBarList);
        prg.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        viewPager.setVisibility(View.INVISIBLE);

        viewPager.setAdapter(new CategoryFragmentStateAdapter(this,fragments));
        ExecutorService srv = ((NewsApp)getApplication()).srv;
        CategoryRepository repo = new CategoryRepository();
        repo.getAllNewsCategories(srv,handler);


        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("Tab " + (position + 1))
        ).attach();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {

            // Update the list with the new data
            List<CategoryModel> newData = (List<CategoryModel>) msg.obj;
            updateList(newData);

            prg.setVisibility(View.INVISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void updateList(List<CategoryModel> newData) {
        categories.clear();
        categories.addAll(newData);
        for(int i =0; i< newData.size(); i++){
            CategoryFragment fragment1 = new CategoryFragment(newData.get(i).getId());
            fragments.add(fragment1);
        }
        // Set the FragmentStateAdapter as the adapter for the ViewPager2
        viewPager.setAdapter(new CategoryFragmentStateAdapter(this,fragments));
        // Connect the TabLayout and ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(categories.get(position).getName())
        ).attach();

    }





}