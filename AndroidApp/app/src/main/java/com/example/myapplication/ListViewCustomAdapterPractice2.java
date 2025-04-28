package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListViewCustomAdapterPractice2 extends ArrayAdapter<ListViewContentPractice2> {
    public ListViewCustomAdapterPractice2(Context context, List<ListViewContentPractice2> fruits) {
        super(context, 0, fruits);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewContentPractice2 fruit = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_2, parent, false);
        }

        ImageView imageFruit = convertView.findViewById(R.id.imageFruit);
        TextView textViewFruitName = convertView.findViewById(R.id.textViewFruitName);
        TextView textViewCalories = convertView.findViewById(R.id.textViewCalories);

        imageFruit.setImageResource(fruit.getImage());
        textViewFruitName.setText(fruit.getName());
        textViewCalories.setText(fruit.getCalories());

        return convertView;
    }
}
