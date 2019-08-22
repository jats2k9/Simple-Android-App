package com.users.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.users.R;
import com.users.model.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private List<User> usersData;
    private Context context;

    public UsersAdapter(List<User> usersData, Context context) {
        this.usersData = usersData;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_cardview, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bindData(usersData.get(position));
    }

    @Override
    public int getItemCount() {
        return usersData.size();
    }


    static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        ImageView user_image;

        public UserViewHolder(View viewItem) {
            super(viewItem);
            userName = viewItem.findViewById(R.id.user_name);
            user_image = viewItem.findViewById(R.id.user_image);
        }

        public void bindData(User user) {
            userName.setText(user.getUserName());
            user_image.setImageBitmap(user.getUserImg());
        }
    }


}
