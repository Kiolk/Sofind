package com.github.kiolk.sofind.ui.fragments.createsound;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.models.SofindModel;
import com.github.kiolk.sofind.ui.fragments.base.BaseFragment;


public class CreateSoundFragment extends BaseFragment implements ICreateSoundView {

    private static final int MAX_NUMBER_OF_CHARACTERS = 100;

    private ICreateSoundPresenter mPresenter;
    private TextView mUserName;
    private TextView mSoundBody;
    private TextView mDate;
    private EditText mUserInput;
    private TextWatcher mTextChangeListener;
    private boolean isValidSoFindBody = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CreateSoundPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_sound, null);
        mUserName = view.findViewById(R.id.user_name_text_view);
        mSoundBody = view.findViewById(R.id.sound_body_text_view);
        mDate = view.findViewById(R.id.date_create_text_view);
        mUserInput = view.findViewById(R.id.new_sound_body_edit_text);

        changeTitle();
        changeMenu();
        setupToolBar();
        return view;
    }

    //TODO work with inheritance fields
    private void changeMenu() {
        super.menuId = R.id.save_menu_item;
    }

    private void changeTitle() {
        super.titleResource = R.string.CREATE_SOUND;
    }

    @Override
    public void prepareForm() {
        mPresenter.updateExampleForm();
        if(mTextChangeListener == null) {
            mTextChangeListener = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length() >= MAX_NUMBER_OF_CHARACTERS - 1){
                        mUserInput.setError(getResources().getString(R.string.MORE_SYMBOLS) + " " + MAX_NUMBER_OF_CHARACTERS);
                        isValidSoFindBody = false;
                    }
                    mPresenter.updateExampleForm();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
        }
        mUserInput.addTextChangedListener(mTextChangeListener);

    }

    @Override
    public void updateExample(SofindModel sofind, String userName) {
        mUserName.setText(userName);
        mDate.setText(sofind.getCreateTime() + " ");
        mSoundBody.setText(sofind.getMindMessage());
    }

    @Override
    public void saveNewSofind() {
//        mUserInput.removeTextChangedListener(mTextChangeListener);
        if(isValidSoFindBody){
            mPresenter.saveNewSofind(getSofindBody());
        }else{
            Toast.makeText(getContext(), getResources().getString(R.string.MORE_SYMBOLS) + " " + MAX_NUMBER_OF_CHARACTERS, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void clearForm() {
        mUserInput.setText("");
        mSoundBody.setText("");
    }

    @Override
    public void succesSave() {
        clearForm();
        Toast.makeText(getContext(), "Success save new sound", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getSofindBody() {
        return mUserInput.getText().toString();
    }

    @Override
    public void error() {
        Toast.makeText(getContext(), "Not save new sound", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUserInput.removeTextChangedListener(mTextChangeListener);
    }
}
