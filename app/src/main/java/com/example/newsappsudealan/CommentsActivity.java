package com.example.newsappsudealan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommentsActivity extends AppCompatActivity {

    List<CommentModel> data;
    RecyclerView recView;
    int news_id;
    static int REQUEST_CODE = 1;
    ProgressBar prg;
    ProgressDialog progressDialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the result from the intent
            String result = data.getStringExtra("result");
            // Update the UI with the result

            ExecutorService srv = ((NewsApp)getApplication()).srv;
            news_id = getIntent().getIntExtra("id",1);

            CommentRepository repo = new CommentRepository();
            repo.getCommentsByNewsId(srv,handler, news_id);
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            data = (List<CommentModel>) msg.obj;
            CommentViewAdapter adp = new CommentViewAdapter(CommentsActivity.this,data);

            recView.setAdapter(adp);
            progressDialog.dismiss();
            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_comment_view);

        recView = findViewById(R.id.comment_recView);
        prg = findViewById(R.id.comment_progressBarList);


// Show the progress dialog when the download starts
        progressDialog = ProgressDialog.show(this, "Please wait", "Downloading data...");

        recView.setLayoutManager(new LinearLayoutManager(this));

        ExecutorService srv = ((NewsApp)getApplication()).srv;
        news_id = getIntent().getIntExtra("id",1);

        CommentRepository repo = new CommentRepository();
        repo.getCommentsByNewsId(srv,handler, news_id);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comment_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_post_comment_activity){
            Intent i = new Intent(this,PostCommentActivity.class);
            i.putExtra("id",news_id);
            startActivityForResult(i, REQUEST_CODE);
        }
        else if(item.getItemId() == android.R.id.home){
            finish();
        }

        return true;
    }
}
