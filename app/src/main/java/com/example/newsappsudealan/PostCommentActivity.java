package com.example.newsappsudealan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

public class PostCommentActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtComment;
    ProgressBar prgBar;
    TextView txtView;
    ProgressDialog progressDialog;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            int i = ((Number)msg.obj).intValue();

            //if the message SUCCESSFUL
            if(i == 1){

                Intent intent = new Intent();
                intent.putExtra("result", "some result");
                setResult(RESULT_OK, intent);
                progressDialog.dismiss();
                finish();
                Toast.makeText(PostCommentActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_comment_view);

        Button btnPostComment = findViewById(R.id.btnPostComment);

        txtName = findViewById(R.id.txtYourName);
        txtComment= findViewById(R.id.txtYourComment);
        txtView = findViewById(R.id.error_textView);
        txtView.setVisibility(View.INVISIBLE);


        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = txtName.getText().toString();
                String comment = txtComment.getText().toString();
                txtView.setVisibility(View.INVISIBLE);

                if(name.equals("") || comment.equals("")){
                    txtView.setVisibility(View.VISIBLE);
                    return;
                }
                ExecutorService srv = ((NewsApp)getApplication()).srv;
                CommentRepository repo = new CommentRepository();
                progressDialog = ProgressDialog.show(PostCommentActivity.this, "Please wait", "Downloading data...");

                int news_id = getIntent().getIntExtra("id",1);

                repo.postCommenterino(srv,handler,name,comment,news_id);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return true;
    }
}
