package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.FlowerViewHolder> {

    private List<ProductClass> flowerList;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(ProductClass product);
    }

    public ProductAdapter(List<ProductClass> flowerList, OnItemClickListener listener) {
        this.flowerList = flowerList;
        this.onItemClickListener = listener;
    }

    public void updateData(List<ProductClass> newList) {
        flowerList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FlowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        return new FlowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerViewHolder holder, int position) {
        ProductClass flower = flowerList.get(position);
        holder.name.setText(flower.getProductName());
        holder.description.setText(flower.getproductDescription());
        holder.price.setText("₫" + flower.getproductPrice());
        holder.quantity.setText("remaining quantity: " + flower.getproductQuantity());
        holder.category.setText(flower.getcategoryName());

        int resId = holder.itemView.getContext().getResources()
                .getIdentifier(flower.getProductResourceId(), "drawable", holder.itemView.getContext().getPackageName());
        holder.image.setImageResource(resId);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(flower);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flowerList.size();
    }

    public static class FlowerViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, price, category, quantity;
        ImageView image;

        public FlowerViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.flower_category);
            quantity = itemView.findViewById(R.id.flower_quantity);
            name = itemView.findViewById(R.id.flower_name);
            description = itemView.findViewById(R.id.flower_description);
            price = itemView.findViewById(R.id.flower_price);
            image = itemView.findViewById(R.id.flower_image);
        }
    }
}
