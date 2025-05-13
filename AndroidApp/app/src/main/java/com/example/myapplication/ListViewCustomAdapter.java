package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ListViewCustomAdapter extends ArrayAdapter<ListViewContent> {
    public ListViewCustomAdapter(Context context, List<ListViewContent> versions) {
        super(context, 0, versions);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ListViewContent version = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageListAvatarSV);
        TextView nameView = convertView.findViewById(R.id.textListName);
        TextView mssvView = convertView.findViewById(R.id.textListStudentID);

        if (version != null) {
            imageView.setImageBitmap(version.getImage());
            nameView.setText(version.getName());
            mssvView.setText(version.getMssv());
        }


        return convertView;
    }
}