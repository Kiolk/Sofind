package com.github.kiolk.sofind.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.adapters.SoundRecylerAdapter;
import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.ui.fragments.base.BaseFragment;
import com.github.kiolk.sofind.ui.fragments.yoursounds.IYouSoundPresenter;
import com.github.kiolk.sofind.ui.fragments.yoursounds.IYouSoundView;
import com.github.kiolk.sofind.ui.fragments.yoursounds.YouSoundPresenter;

import java.util.ArrayList;
import java.util.List;


public class WorldSoundsFragment extends BaseFragment implements IYouSoundView {

    private SoundRecylerAdapter mAdapter;
    private List<FullSofindModel> mListSofinds;
    private IYouSoundPresenter mPresenter;
    private int updatePoint = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListSofinds = new ArrayList<>();
        mPresenter = new YouSoundPresenter(this);
        mAdapter = new SoundRecylerAdapter(getContext(), mListSofinds, mPresenter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribeOnSounds();
    }

    @Override
    public void addLateSound(FullSofindModel userSofind) {
            if(updatePoint == 0){
                updatePoint = mListSofinds.size();
            }
        mListSofinds.add(updatePoint, userSofind);
        mAdapter.notifyDataSetChanged();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_with_recycler_view, null);
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
        super.titleResource = R.string.WORLD_SOUNDS;
    }

    @Override
    public void showUserSounds() {
        RecyclerView recycler = getView().findViewById(R.id.general_fragment_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(mAdapter);
        mPresenter.subscribeOnSounds();
    }

    @Override
    public void setUserSound(FullSofindModel userSofind) {
        mListSofinds.add(0, userSofind);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserFilter() {

    }

    @Override
    public void shouUpdateProgressBar(boolean isShow) {
        ProgressBar progressBar = getView().findViewById(R.id.update_progress_bar);
        if(isShow){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void resetAddPoint() {
        updatePoint = 0;
    }
}
