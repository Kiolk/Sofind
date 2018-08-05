package com.github.kiolk.sofind.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.models.SofindModel;

import java.util.List;
import java.util.zip.Inflater;

public class SoundRecylerAdapter extends RecyclerView.Adapter<SoundRecylerAdapter.SoundViewHolder> {

    private List<SofindModel> mSofindList;
    private Context mContext;

    public SoundRecylerAdapter(Context context, List<SofindModel> sofindList){
        mSofindList = sofindList;
        mContext = context;
    }



    @NonNull
    @Override
    public SoundRecylerAdapter.SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_single_sound, parent, false);
        return new SoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundRecylerAdapter.SoundViewHolder holder, int position) {
        SofindModel singlSofind = mSofindList.get(position);
        holder.mUserName.setText(singlSofind.getUserid());
        holder.mSofindBody.setText(singlSofind.getMindMessage());
        //TODO Refactor corect display time
        holder.mDate.setText(singlSofind.getCreateTime() + "");
        holder.mLikes.setText(singlSofind.getNumberOfLikes() + "");
    }

    @Override
    public int getItemCount() {
        return mSofindList.size();
    }

    class SoundViewHolder extends RecyclerView.ViewHolder{
        private TextView mUserName;
        private TextView mSofindBody;
        private TextView mDate;
        private TextView mLikes;
        private ImageView mHeart;

        private SoundViewHolder(View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.user_name_text_view);
            mSofindBody = itemView.findViewById(R.id.sound_body_text_view);
            mDate = itemView.findViewById(R.id.date_create_text_view);
            mLikes = itemView.findViewById(R.id.number_of_likes_text_view);
            mHeart = itemView.findViewById(R.id.like_image_image_view);
        }
    }
}
