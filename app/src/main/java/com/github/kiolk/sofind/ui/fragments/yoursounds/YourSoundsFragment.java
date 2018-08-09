package com.github.kiolk.sofind.ui.fragments.yoursounds;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.adapters.SoundRecyclerAdapter;
import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.providers.UserInfoProvider;
import com.github.kiolk.sofind.ui.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class YourSoundsFragment extends BaseFragment implements IYouSoundView {

    private String mUserId;
    private SoundRecyclerAdapter mAdapter;
    private List<FullSofindModel> mListSofinds;
    private IYouSoundPresenter mPresenter;
    int updatePoint;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mListSofinds = new ArrayList<>();
        mPresenter = new YouSoundPresenter(this);
        mAdapter = new SoundRecyclerAdapter(getContext(), mListSofinds, mPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_with_recycler_view, null);
        changeTitle();
        changeMenu();
        setupToolBar();
        return view;
    }

    //TODO work with inheritance fields
    private void changeMenu() {
        super.menuId = R.id.search_menu_item;
    }

    private void changeTitle() {
        super.titleResource = R.string.YOUR_SOUNDS;
    }

    @Override
    public void showUserSounds() {
        final RecyclerView recycler = getView().findViewById(R.id.general_fragment_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(mAdapter);
        mPresenter.subscribeOnSounds();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribeOnSounds();
    }

    @Override
    public void setUserSound(final FullSofindModel userSofind) {
        if (mUserId != null) {
            if (mUserId.equals(userSofind.getUserId())) {
                mListSofinds.add(0, userSofind);
                mAdapter.notifyDataSetChanged();
            }
        } else {
            mListSofinds.add(0, userSofind);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void addLateSound(final FullSofindModel userSofind) {
        if (updatePoint == 0) {
            updatePoint = mListSofinds.size();
        }
        if (mUserId != null) {
            if (mUserId.equals(userSofind.getUserId())) {
                mListSofinds.add(updatePoint, userSofind);
                mAdapter.notifyDataSetChanged();
            }
        } else {
            mListSofinds.add(updatePoint, userSofind);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showUpdateProgressBar(final boolean isShow) {
        final ProgressBar progressBar = getView().findViewById(R.id.update_progress_bar);
        if (isShow) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void resetAddPoint() {
        updatePoint = 0;
    }

    @Override
    public void setUserFilter() {
        mUserId = UserInfoProvider.getUserId(getContext());
    }
}
