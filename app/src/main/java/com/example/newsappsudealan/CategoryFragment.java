package com.example.newsappsudealan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class CategoryFragment extends Fragment {

    int categoryId;
    List<NewsModel> data = new ArrayList<>();
    ProgressBar prg;
    RecyclerView recView;
    NewsViewAdapter adp;

    private final Handler handler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(@NonNull Message msg) {
            // Update the list with the new data
            List<NewsModel> newData = (List<NewsModel>) msg.obj;
            updateList(newData);

            prg.setVisibility(View.INVISIBLE);
            recView.setVisibility(View.VISIBLE);
        }
    };

    private void updateList(List<NewsModel> newData) {
        data.clear();
        data.addAll(newData);
        adp = new NewsViewAdapter(requireActivity(),data);
        recView.setAdapter(adp);
        adp.notifyDataSetChanged();
    }

    public CategoryFragment(int categoryId) {
        super();
        this.categoryId = categoryId;

    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_news_view, container, false);
        prg = view.findViewById(R.id.list_news_view_progressBarList);
        recView = view.findViewById(R.id.recView_list_news);
        prg.setVisibility(View.VISIBLE);
        recView.setVisibility(View.INVISIBLE);


        recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        NewsApp app = (NewsApp) ((AppCompatActivity) getActivity()).getApplication();
        ExecutorService srv = ((NewsApp)getActivity().getApplication()).srv;

        NewsRepository repo = new NewsRepository();
        adp = new NewsViewAdapter(getActivity(), data);
        recView.setAdapter(adp);
        repo.getNewsByCatId(app.srv,handler,categoryId);

        return view;

    }
}
