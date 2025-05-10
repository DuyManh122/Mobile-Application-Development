package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class ListViewCustomAdapter extends ArrayAdapter<ListViewContent> {
    public ListViewCustomAdapter(Context context, List<ListViewContent> versions) {
        super(context, 0, versions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewContent version = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageListAvatarSV);
        TextView nameView = convertView.findViewById(R.id.textListName);
        TextView versionView = convertView.findViewById(R.id.textListStudentID);

        imageView.setImageResource(version.getImage());
        nameView.setText(version.getName());
        versionView.setText(version.getVersion());

        return convertView;
    }
}