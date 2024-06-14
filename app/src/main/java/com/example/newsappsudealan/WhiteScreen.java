package com.example.newsappsudealan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WhiteScreen extends AppCompatActivity {

    View root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        root.setBackgroundColor(
                getResources().getColor(
                        android.R.color.white)
        );

        setContentView(root);
    }

}
