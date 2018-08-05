package com.github.kiolk.sofind.ui.fragments.yoursounds;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.adapters.SoundRecylerAdapter;
import com.github.kiolk.sofind.data.models.SofindModel;
import com.github.kiolk.sofind.ui.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


public class YourSoundsFragment extends BaseFragment implements IYouSoundView {

//   super.titleResource = R.string.YOUR_SOUNDS;

    private SoundRecylerAdapter mAdapter;
    private List<SofindModel> mListSofinds;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListSofinds = new ArrayList<SofindModel>();
        SofindModel ex =new SofindModel();
        ex.setNumberOfLikes();
        ex.setUserid("23");
        ex.setCreateTime(232323L);
        ex.setMindMessage("Per asper sa ad aster");
        mListSofinds.add(ex);
        mAdapter = new SoundRecylerAdapter(getContext(), mListSofinds);
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
        super.titleResource = R.string.YOUR_SOUNDS;
    }

    @Override
    public void showUserSounds() {
        RecyclerView recycler = getView().findViewById(R.id.general_fragment_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(mAdapter);
        SofindModel ex =new SofindModel();
        ex.setNumberOfLikes();
        ex.setUserid("23");
        ex.setCreateTime(232323L);
        ex.setMindMessage("Not hurt neigbour");
        setUserSound(ex);
    }

    @Override
    public void setUserSound(SofindModel userSofind) {
        mListSofinds.add(userSofind);
        mAdapter.notifyDataSetChanged();
    }
}
