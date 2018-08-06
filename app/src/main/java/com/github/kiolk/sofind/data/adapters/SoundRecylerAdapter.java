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
import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.data.models.SofindModel;
import com.github.kiolk.sofind.ui.fragments.yoursounds.IYouSoundPresenter;
import com.github.kiolk.sofind.util.ConverterUtil;

import java.util.List;

public class SoundRecylerAdapter extends RecyclerView.Adapter<SoundRecylerAdapter.SoundViewHolder> {

    private List<FullSofindModel> mSofindList;
    private Context mContext;
    private IYouSoundPresenter mPresenter;

    public SoundRecylerAdapter(Context context, List<FullSofindModel> sofindList, IYouSoundPresenter presenter){
        mSofindList = sofindList;
        mContext = context;
        mPresenter = presenter;
    }



    @NonNull
    @Override
    public SoundRecylerAdapter.SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_single_sound, parent, false);
        return new SoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundRecylerAdapter.SoundViewHolder holder, int position) {
        final FullSofindModel singlSofind = mSofindList.get(position);
        holder.mUserName.setText(singlSofind.getUserFullName());
        holder.mSofindBody.setText(singlSofind.getMindMessage());
        //TODO Refactor correct display time
        holder.mDate.setText(ConverterUtil.convertEpochTime(mContext, singlSofind.getCreateTime(), ConverterUtil.DAY_PATTERN));
        holder.mLikes.setText(singlSofind.getLikes() + "");
        holder.mHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singlSofind.setLikes(singlSofind.getLikes()+1);
                mPresenter.saveUpdatedSofind(singlSofind);
                notifyDataSetChanged();
            }
        });
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
