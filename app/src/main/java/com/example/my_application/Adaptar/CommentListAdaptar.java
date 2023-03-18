package com.example.my_application.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_application.Data_Model.CommentList.Comment;
import com.example.my_application.R;

import java.util.ArrayList;
import java.util.List;

public class CommentListAdaptar extends RecyclerView.Adapter<CommentListAdaptar.ViewHolders> {

    List<Comment> commentList = new ArrayList<>();
    Context context;

    public CommentListAdaptar(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_model, parent, false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        holder.User_Name.setText(String.valueOf(commentList.get(position).getName()));
        holder.User_Comment.setText(String.valueOf(commentList.get(position).getText()));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView User_Name, User_Comment;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            User_Name = itemView.findViewById(R.id.comment_user_name);
            User_Comment = itemView.findViewById(R.id.comments_des);
        }
    }
}
