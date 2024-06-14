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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommentViewAdapter extends RecyclerView.Adapter<CommentViewAdapter.ListHolder>{

    Context ctx;
    List<CommentModel> data;

    public CommentViewAdapter(Context ctx, List<CommentModel> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public CommentViewAdapter.ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root= LayoutInflater.from(ctx).inflate(R.layout.comment_row_layout,parent,false);

        CommentViewAdapter.ListHolder holder = new CommentViewAdapter.ListHolder(root);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewAdapter.ListHolder holder, int position) {

        holder.txtCommentName.setText(data.get(holder.getAdapterPosition()).getName());
        holder.txtCommentText.setText(data.get(position).getCommentText());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ListHolder extends RecyclerView.ViewHolder{

        TextView txtCommentName;
        TextView txtCommentText;
        ConstraintLayout row;


        public ListHolder(@NonNull View itemView) {
            super(itemView);

            txtCommentName = itemView.findViewById(R.id.txtCommentName);
            txtCommentText = itemView.findViewById(R.id.txtCommentText);
            row = itemView.findViewById(R.id.comment_row_list);

        }

    }

}
