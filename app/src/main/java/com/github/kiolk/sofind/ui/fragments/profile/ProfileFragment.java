package com.github.kiolk.sofind.ui.fragments.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.models.UserModel;
import com.github.kiolk.sofind.ui.fragments.base.BaseFragment;
import com.github.kiolk.sofind.util.ConstantUtil;

public class ProfileFragment extends BaseFragment implements IProfileView {

    public static final int INCORRECT_PASSWORD = R.string.INCORRECT_PASSWORD;
    public static final int NOT_CORRECT_COMPLET_FORM = R.string.NOT_COMPLITE_FORM;

    private ProfilePresenter mPresenter = new ProfilePresenter(this);
    private EditText mUserName;
    private EditText mUserSurname;
    private EditText mUserAge;
    private EditText mFirstPassword;
    private EditText mSecondPAssword;
    private RadioButton mIsMale;
    private RadioButton mIsFemale;
    private Button mSaVeButton;
    private RadioGroup mUserSex;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        changeMenu();
        changeTitle();
        setupToolBar();
        View view = inflater.inflate(R.layout.fragment_profile, null);
        setupView(view);
        return view;
    }

    private void setupView(View view) {
        mUserName = view.findViewById(R.id.user_name_edit_profile);
        mUserSurname = view.findViewById(R.id.user_surname_edit_profile);
        mUserAge = view.findViewById(R.id.age_edit_profile);
        mUserSex = view.findViewById(R.id.sex_edit_profile_radio_group);
        mIsFemale = view.findViewById(R.id.female_edit_profile_radio_button);
        mIsMale = view.findViewById(R.id.male_edit_profile_radio_button);
        mFirstPassword = view.findViewById(R.id.first_password_edit_profile);
        mSecondPAssword = view.findViewById(R.id.second_password_edit_profile);
        mSaVeButton = view.findViewById(R.id.save_profile_button);
        mProgressBar = view.findViewById(R.id.profile_progress_bar);
    }

    private void changeTitle() {
        super.titleResource = R.string.EDIT_PROFILE;
    }

    private void changeMenu() {
        super.menuId = R.id.profile_menu_item;
    }

    @Override
    public void saveEditProfile() {
        mPresenter.saveUser(null);
    }

    public void prepareInformation() {
        mPresenter.getUserInformation();
    }

    @Override
    public void setInformation(UserModel user) {
        mUserName.setText(user.getUserName());
        mUserSurname.setText(user.getSurname());
        mUserAge.setText(user.getAge() + "");
        if (user.isMale()) {
            mIsMale.setChecked(true);
        } else {
            mIsFemale.setChecked(true);
        }
        mSaVeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCorrectInputInfo()){
                    setupConfirmPasswordDialog();
                }else {
                    showErrorMessage(NOT_CORRECT_COMPLET_FORM);
                }
            }
        });
    }

    private void setupConfirmPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View confirmView = inflater.inflate(R.layout.dialog_new_sofind, null);
        final EditText mUserConfirmPassword = confirmView.findViewById(R.id.edit_text_dialog);
        builder.setTitle(R.string.SAVE_WITH_CONFIRM).setView(confirmView)
                .setPositiveButton(R.string.SAVE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mPresenter.confirmUserPassword(mUserConfirmPassword.getText().toString())) {
                            mPresenter.saveUser(getUserInformation());
                        } else {
                            showErrorMessage(INCORRECT_PASSWORD);
                        }
                    }
                }).create().show();
    }

    private UserModel getUserInformation() {
        UserModel result = new UserModel();
        result.setMale(mIsMale.isChecked());
        result.setUserName(mUserName.getText().toString());
        result.setSurname(mUserSurname.getText().toString());
        result.setAge(Integer.parseInt(mUserAge.getText().toString()));
        result.setPassword(mFirstPassword.getText().toString());
        return result;
    }

    @Override
    public void successUpdate() {
        Toast.makeText(getContext(), "Success save information", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar(boolean isShow) {
        if (isShow) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showErrorMessage(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean checkCorrectInputInfo() {
        boolean isCorrect = true;
        if(mUserName.getText().toString().length() < ConstantUtil.MIN_NUMBER_IN_NAME){
            mUserName.setError(getResources().getString(R.string.INPUT_MIN_TWO_SYMBOL) + " " + ConstantUtil.MIN_NUMBER_IN_NAME);
            isCorrect = false;
        }
        if(mUserSurname.getText().toString().length() < ConstantUtil.MIN_NUMBER_IN_NAME){
            mUserSurname.setError(getResources().getString(R.string.INPUT_MIN_TWO_SYMBOL) + " " + ConstantUtil.MIN_NUMBER_IN_NAME);
            isCorrect = false;
        }
        int age = Integer.parseInt(mUserAge.getText().toString());
        if(age > ConstantUtil.MAX_OLD_VALUE || age < ConstantUtil.MIN_OLD_VALUE){
            mUserAge.setError(getResources().getString(R.string.INPUT_CORRECT_AGE));
            isCorrect = false;
        }

        if(!mSecondPAssword.getText().toString().equals(mFirstPassword.getText().toString())){
            mSecondPAssword.setError(getResources().getString(R.string.CONFIRM_PASSWORD_NOT_EQUALS));
            isCorrect = false;
        }
        if(!mFirstPassword.getText().toString().equals("") && mFirstPassword.getText().toString().length() < ConstantUtil.MIN_NUMBER_IN_PASSWORD){
            mFirstPassword.setError(getResources().getString(R.string.SHORT_PASSWOR) + " " + ConstantUtil.MIN_NUMBER_IN_PASSWORD);
            isCorrect = false;
        }
        return isCorrect;
    }


}
