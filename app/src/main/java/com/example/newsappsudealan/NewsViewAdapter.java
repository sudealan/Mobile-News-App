package com.example.newsappsudealan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.ListHolder>{

    Context ctx;
    List<NewsModel> data;


    public NewsViewAdapter(Context ctx, List<NewsModel> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root= LayoutInflater.from(ctx).inflate(R.layout.row_layout,parent,false);

        ListHolder holder = new ListHolder(root);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {

        holder.txtTitle.setText(data.get(holder.getAdapterPosition()).getTitle());

        String inputDateString = data.get(position).getDate();

        SimpleDateFormat inputFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        }
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date = inputFormat.parse(inputDateString);
            String outputDateString = outputFormat.format(date);
            holder.txtDate.setText(outputDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //holder.txtDate.setText(data.get(position).getDate());
        NewsApp app = (NewsApp) ((AppCompatActivity)ctx).getApplication();

        holder.downloadImage(app.srv,data.get(holder.getAdapterPosition()).getImageField());

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ctx,NewsDetails.class);
                i.putExtra("id",data.get(holder.getAdapterPosition()).getId());
                ctx.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ListHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtDate;
        ImageView imgList;
        ConstraintLayout row;
        boolean imageDownloaded;

        Handler imgHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                Bitmap img = (Bitmap)msg.obj;
                imgList.setImageBitmap(img);
                imageDownloaded = true;
                return true;
            }
        });

        public ListHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
            imgList = itemView.findViewById(R.id.imgList);
            row = itemView.findViewById(R.id.row_list);

        }

        public void downloadImage(ExecutorService srv, String path){

            if (!imageDownloaded){

                NewsRepository repo = new NewsRepository();
                repo.downloadImage(srv,imgHandler,path);

            }
        }
    }

}
