package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<OrderClass> orders;

    public OrderAdapter(List<OrderClass> orders) {
        this.orders = orders;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView userEmail, productName, price, quantity;

        public OrderViewHolder(View view) {
            super(view);
            userEmail = view.findViewById(R.id.userEmail);
            productName = view.findViewById(R.id.productName);
            price = view.findViewById(R.id.price);
            quantity = view.findViewById(R.id.quantity);
        }
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        OrderClass order = orders.get(position);
        holder.userEmail.setText("User: " + order.getUserEmail());
        holder.productName.setText(order.getProductName());
        holder.price.setText("Price: đ" + order.getPrice());
        holder.quantity.setText("Quantity: " + order.getQuantity());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
