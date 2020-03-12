package com.developer.filepicker.file;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.FileListViewHolder> {
    private final ArrayList<ListItem> listItems;
    private final Context context;

    FileListAdapter(ArrayList<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public FileListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.file_list_item,
                parent, false);
        return new FileListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileListViewHolder holder, int position) {
        holder.name.setText(listItems.get(position).getName());
        holder.path.setText(listItems.get(position).getPath());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class FileListViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView path;

        FileListViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            path = itemView.findViewById(R.id.path);
        }
    }
}