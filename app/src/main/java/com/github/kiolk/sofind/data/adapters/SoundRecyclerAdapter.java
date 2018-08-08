package com.github.kiolk.sofind.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.ui.fragments.yoursounds.IYouSoundPresenter;
import com.github.kiolk.sofind.util.ConverterUtil;

import java.util.List;

/**
 * Recycler view adapter for representation of user sounds or world sounds
 */
public class SoundRecyclerAdapter extends RecyclerView.Adapter<SoundRecyclerAdapter.SoundViewHolder> {

    private final List<FullSofindModel> mSofindList;
    private final Context mContext;
    private final IYouSoundPresenter mPresenter;

    public SoundRecyclerAdapter(final Context context, final List<FullSofindModel> sofindList, final IYouSoundPresenter presenter) {
        mSofindList = sofindList;
        mContext = context;
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public SoundRecyclerAdapter.SoundViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.card_single_sound, parent, false);
        return new SoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SoundRecyclerAdapter.SoundViewHolder holder, final int position) {
        final FullSofindModel singlSofind = mSofindList.get(position);
        holder.mUserName.setText(singlSofind.getUserFullName());
        holder.mSofindBody.setText(singlSofind.getMindMessage());
        //TODO Refactor correct display time
        holder.mDate.setText(ConverterUtil.convertEpochTime(mContext, singlSofind.getCreateTime(), ConverterUtil.DAY_PATTERN));
        holder.mLikes.setText(String.valueOf(singlSofind.getLikes()));
        holder.mHeart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                singlSofind.setLikes(singlSofind.getLikes() + 1);
                mPresenter.saveUpdatedSofind(singlSofind);
                notifyDataSetChanged();
            }
        });
        if (position == mSofindList.size() - 1) {
            mPresenter.loadMoreData(mSofindList.size());
            Toast.makeText(mContext, "Attach last item", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return mSofindList.size();
    }

    final class SoundViewHolder extends RecyclerView.ViewHolder {

        private final TextView mUserName;
        private final TextView mSofindBody;
        private final TextView mDate;
        private final TextView mLikes;
        private final ImageView mHeart;

        private SoundViewHolder(final View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.user_name_text_view);
            mSofindBody = itemView.findViewById(R.id.sound_body_text_view);
            mDate = itemView.findViewById(R.id.date_create_text_view);
            mLikes = itemView.findViewById(R.id.number_of_likes_text_view);
            mHeart = itemView.findViewById(R.id.like_image_image_view);
        }
    }
}
