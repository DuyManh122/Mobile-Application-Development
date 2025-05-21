package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartClass> cartClasses;

    public CartAdapter(List<CartClass> cartClasses) {
        this.cartClasses = cartClasses;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartClass item = cartClasses.get(position);
        holder.name.setText(item.getProduct().getProductName());
        holder.quantity.setText("Quantity: " + item.getQuantity());
        holder.price.setText("₫" + item.getProduct().getproductPrice() * item.getQuantity());
        int resId = holder.itemView.getContext().getResources()
                .getIdentifier(item.getProduct().getProductResourceId(), "drawable", holder.itemView.getContext().getPackageName());
        holder.image.setImageResource(resId);
    }

    @Override
    public int getItemCount() {
        return cartClasses.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, price;
        ImageView image;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_item_name);
            quantity = itemView.findViewById(R.id.cart_item_quantity);
            price = itemView.findViewById(R.id.cart_item_price);
            image = itemView.findViewById(R.id.cart_item_image);
        }
    }
}
