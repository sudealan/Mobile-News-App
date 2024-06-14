package com.example.newsappsudealan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsDetails extends AppCompatActivity {

    ImageView imgDetails;
    TextView txtNameDetail;
    TextView txtHistoryDetail;
    TextView txtDate;
    int news_id;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            NewsModel newsModel = (NewsModel) msg.obj;

            txtNameDetail.setText(newsModel.getTitle());
            txtHistoryDetail.setText(newsModel.getText());
            setTitle(newsModel.getCategoryName());

            String inputDateString = newsModel.getDate();
            SimpleDateFormat inputFormat = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            }
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

            try {
                Date date = inputFormat.parse(inputDateString);
                String outputDateString = outputFormat.format(date);
                txtDate.setText(outputDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            NewsRepository repo = new NewsRepository();
            repo.downloadImage(((NewsApp)getApplication()).srv,imgHandler,newsModel.getImageField());

            return true;
        }
    });

    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            Bitmap img = (Bitmap) msg.obj;
            imgDetails.setImageBitmap(img);

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.news_details);

        news_id = getIntent().getIntExtra("id",1);

        imgDetails =findViewById(R.id.imgDetails);
        txtNameDetail = findViewById(R.id.txtNameDetail);
        txtHistoryDetail = findViewById(R.id.textHist);
        txtDate = findViewById(R.id.txtDate);

        NewsRepository repo = new NewsRepository();
        repo.getNewsById(((NewsApp)getApplication()).srv,dataHandler,news_id);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_open_activity){
            Intent i = new Intent(this,CommentsActivity.class);
            i.putExtra("id",news_id);
            this.startActivity(i);
        }
        else if(item.getItemId() == android.R.id.home){
            finish();
        }

        return true;
    }

}
