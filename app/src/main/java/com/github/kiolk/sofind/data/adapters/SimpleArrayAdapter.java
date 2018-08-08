package com.github.kiolk.sofind.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.models.SettingItemModel;

import java.util.List;

/**
 * Class implement ArrayAdapter for representing settings items in ListView
 */
public class SimpleArrayAdapter extends ArrayAdapter<SettingItemModel> {

    private final Context inputContext;
    private final int resource;
    private final List<SettingItemModel> objects;

    public SimpleArrayAdapter(@NonNull final Context context, final int resource, @NonNull final List<SettingItemModel> objects) {
        super(context, resource, objects);
        this.inputContext = context;
        this.objects = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) inputContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view;

        if (inflater != null) {
            view = inflater.inflate(resource, parent, false);
            final TextView title = view.findViewById(R.id.setting_title_text_view);
            title.setText(objects.get(position).getTitle());
            final ImageView icon = view.findViewById(R.id.setting_item_image_view);
            icon.setImageDrawable(objects.get(position).getResource());
            return view;
        }

        return super.getView(position, convertView, parent);
    }
}
