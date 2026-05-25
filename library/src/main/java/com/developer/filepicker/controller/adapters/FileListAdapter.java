package com.developer.filepicker.controller.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.filepicker.R;
import com.developer.filepicker.controller.NotifyItemChecked;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.model.FileListItem;
import com.developer.filepicker.model.MarkedItemList;
import com.developer.filepicker.widget.MaterialCheckbox;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Adapter that renders files/folders and keeps checkbox state stable during row recycling.
 */
public class FileListAdapter extends BaseAdapter {

    private final ArrayList<FileListItem> listItem;
    private final Context context;
    private final DialogProperties properties;
    private NotifyItemChecked notifyItemChecked;

    public FileListAdapter(ArrayList<FileListItem> listItem, Context context, DialogProperties properties) {
        this.listItem = listItem == null ? new ArrayList<>() : listItem;
        this.context = context;
        this.properties = properties == null ? new DialogProperties() : properties;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public FileListItem getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dialog_file_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final FileListItem item = listItem.get(position);
        final boolean isParentRow = isParentRow(position, item);
        final boolean isSelectable = isSelectable(item, isParentRow);

        applySelectionAnimation(convertView, item);
        bindIcon(holder, item);
        bindTexts(holder, item, isParentRow);
        holder.checkbox.setCheckboxColors(
                properties.checkbox_checked_color,
                properties.checkbox_unchecked_color,
                properties.checkbox_checkmark_color,
                properties.checkbox_unchecked_inner_color
        );

        holder.checkbox.setOnCheckedChangedListener(null);
        holder.checkbox.setVisibility(isSelectable ? View.VISIBLE : View.INVISIBLE);
        holder.checkbox.setChecked(MarkedItemList.hasItem(item.getLocation()));
        holder.checkbox.setEnabled(isSelectable);

        if (isSelectable) {
            holder.checkbox.setOnCheckedChangedListener((checkbox, isChecked) -> {
                item.setMarked(isChecked);
                if (isChecked) {
                    if (properties.selection_mode == DialogConfigs.MULTI_MODE) {
                        MarkedItemList.addSelectedItem(item);
                    } else {
                        MarkedItemList.addSingleFile(item);
                    }
                } else {
                    MarkedItemList.removeSelectedItem(item.getLocation());
                }
                if (notifyItemChecked != null) {
                    notifyItemChecked.notifyCheckBoxIsClicked();
                }
            });
        }

        return convertView;
    }

    private void applySelectionAnimation(View view, FileListItem item) {
        int animationRes = MarkedItemList.hasItem(item.getLocation())
                ? R.anim.marked_item_animation
                : R.anim.unmarked_item_animation;
        Animation animation = AnimationUtils.loadAnimation(context, animationRes);
        view.startAnimation(animation);
    }

    private void bindIcon(ViewHolder holder, FileListItem item) {
        if (item.isDirectory()) {
            holder.typeIcon.setImageResource(R.mipmap.ic_type_folder);
            holder.typeIcon.setColorFilter(getColorCompat(R.color.colorPrimary));
        } else {
            holder.typeIcon.setImageResource(R.mipmap.ic_type_file);
            holder.typeIcon.setColorFilter(getColorCompat(R.color.colorAccent));
        }
        holder.typeIcon.setContentDescription(item.getFilename());
    }

    private void bindTexts(ViewHolder holder, FileListItem item, boolean isParentRow) {
        holder.name.setText(item.getFilename());
        if (isParentRow) {
            holder.type.setText(R.string.label_parent_directory);
        } else {
            Date date = new Date(item.getTime());
            DateFormat dateFormatter = android.text.format.DateFormat.getMediumDateFormat(context);
            DateFormat timeFormatter = android.text.format.DateFormat.getTimeFormat(context);
            holder.type.setText(String.format(context.getString(R.string.last_edit),
                    dateFormatter.format(date), timeFormatter.format(date)));
        }
    }

    private boolean isSelectable(FileListItem item, boolean isParentRow) {
        if (item == null || isParentRow) {
            return false;
        }
        if (item.isDirectory()) {
            return properties.selection_type != DialogConfigs.FILE_SELECT;
        }
        return properties.selection_type != DialogConfigs.DIR_SELECT;
    }

    private boolean isParentRow(int position, FileListItem item) {
        return position == 0 && item != null
                && item.getFilename().startsWith(context.getString(R.string.label_parent_dir));
    }

    private int getColorCompat(int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(colorRes, context.getTheme());
        }
        return context.getResources().getColor(colorRes);
    }

    public void setNotifyItemCheckedListener(NotifyItemChecked notifyItemChecked) {
        this.notifyItemChecked = notifyItemChecked;
    }

    private static class ViewHolder {
        final ImageView typeIcon;
        final TextView name;
        final TextView type;
        final MaterialCheckbox checkbox;

        ViewHolder(View itemView) {
            name = itemView.findViewById(R.id.fname);
            type = itemView.findViewById(R.id.ftype);
            typeIcon = itemView.findViewById(R.id.image_type);
            checkbox = itemView.findViewById(R.id.file_mark);
        }
    }
}
