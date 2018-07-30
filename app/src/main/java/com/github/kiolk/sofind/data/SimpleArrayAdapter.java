package com.github.kiolk.sofind.data;

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

import java.util.ArrayList;


public class SimpleArrayAdapter extends ArrayAdapter<SettingItemModel> {

    private  Context inputContext;
    private  int resource;
    private  ArrayList<SettingItemModel> objects;

    public SimpleArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SettingItemModel> objects) {
        super(context, resource, objects);
        this.inputContext = context;
        this.objects =  objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) inputContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource, parent, false);
        TextView title = view.findViewById(R.id.setting_title_text_view);
        title.setText(objects.get(position).getTitle());
        ImageView icon = view.findViewById(R.id.setting_item_image_view);
        icon.setImageDrawable(objects.get(position).getResourse());
        return view;
    }
}
