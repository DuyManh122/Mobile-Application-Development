package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.ViewHolder> {

    private List<UserInfo> userList;

    public UserInfoAdapter(List<UserInfo> userList) {
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_user_information_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserInfo userInfo = userList.get(position);

        holder.keyTextView.setText(userInfo.getKey());
        holder.valueTextView.setText(userInfo.getValue());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView keyTextView;
        TextView valueTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            keyTextView = itemView.findViewById(R.id.User_infor_list1);
            valueTextView = itemView.findViewById(R.id.User_infor_list2);
        }
    }

    public void updateUserInfo(int position, UserInfo updatedUserInfo) {
        userList.set(position, updatedUserInfo);
        notifyItemChanged(position);
    }
}

