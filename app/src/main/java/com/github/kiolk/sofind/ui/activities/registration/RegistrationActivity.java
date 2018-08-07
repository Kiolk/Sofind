package com.github.kiolk.sofind.ui.activities.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.models.UserModel;
import com.github.kiolk.sofind.ui.activities.SplashActivity;
import com.github.kiolk.sofind.util.ConstantUtil;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView {

    private static final int MIN_NUMBER_OF_SYMBOLS = 5;

    private Presenter mPresenter;
    private Button mSignButton;
    private Button mRegistration;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mUserName;
    private EditText mUserSrname;
    private EditText mUserAge;
    private Boolean isUserMale;
    private RadioGroup mRadioGroup;
    private ProgressBar mProgresBar;
    private boolean isOpenRegistrationForm;
    private LinearLayout mRegistrationForm;
    private View.OnFocusChangeListener mFocusChangeListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mPresenter = new PresenterImpl(this);
        setupLoginButton();
    }

    @Override
    public void onBackPressed() {
        if (isOpenRegistrationForm) {
            mSignButton.setEnabled(true);
            mRegistration.setText(getBaseContext().getResources().getString(R.string.REGISTER));
            mRegistrationForm.setVisibility(View.GONE);
            isOpenRegistrationForm = false;
        } else {
            super.onBackPressed();
        }
    }

    private void setupLoginButton() {
        mSignButton = findViewById(R.id.log_in_user);
        mUserName = findViewById(R.id.user_name_edit_text);
        mUserSrname = findViewById(R.id.surname_edit_text);
        mUserAge = findViewById(R.id.age_edit_text);
        mEmail = findViewById(R.id.email_edit_text);
        mPassword = findViewById(R.id.password_edit_text);
        mRadioGroup = findViewById(R.id.sex_radio_group);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isUserMale = checkedId == R.id.male_radio_button;
                mRadioGroup.setBackgroundColor(getBaseContext().getResources().getColor(R.color.TRANSPARENT_COLOR));
            }
        });
        mFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                switch (v.getId()) {
                    case R.id.email_edit_text:
                        if (!isEmailValid(mEmail.getText().toString())) {
                            mEmail.setError(getBaseContext().getResources().getString(R.string.NOT_CORRECT_INPUR_EMAIL));
                            break;
                        }
                    case R.id.password_edit_text:
                        if (!isValidPassword(mPassword.getText().toString())) {
                            mPassword.setError(getBaseContext().getResources().getString(R.string.SHORT_PASSWOR) + " " + ConstantUtil.MIN_NUMBER_IN_PASSWORD);
                        }
                        break;
                    case R.id.user_name_edit_text:
                        if (!isValidTextInput(mUserName.getText().toString())) {
                            mUserName.setError(getBaseContext().getResources().getString(R.string.INPUT_MIN_TWO_SYMBOL) + " " + ConstantUtil.MIN_NUMBER_IN_NAME);
                        }
                        break;
                    case R.id.surname_edit_text:
                        if (!isValidTextInput(mUserSrname.getText().toString())) {
                            mUserSrname.setError(getBaseContext().getResources().getString(R.string.INPUT_MIN_TWO_SYMBOL) +  " " + ConstantUtil.MIN_NUMBER_IN_NAME);
                        }
                        break;
                    case R.id.age_edit_text:
                        if (!isValidAge(mUserAge.getText().toString())) {
                            mUserAge.setError(getBaseContext().getResources().getString(R.string.INPUT_CORRECT_AGE));
                        }
                }
            }
        };
        mEmail.setOnFocusChangeListener(mFocusChangeListener);
        mUserName.setOnFocusChangeListener(mFocusChangeListener);
        mUserSrname.setOnFocusChangeListener(mFocusChangeListener);
        mUserAge.setOnFocusChangeListener(mFocusChangeListener);
        mPassword.setOnFocusChangeListener(mFocusChangeListener);
        mProgresBar = findViewById(R.id.registration_progress_bar);
        mRegistration = findViewById(R.id.register_new_user);
        mRegistrationForm = findViewById(R.id.registration_form_linear_layout);
        mSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                singing();
                showProgressBar(true);
                mPresenter.singIn(mEmail.getText().toString(), mPassword.getText().toString());
            }
        });

        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpenRegistrationForm) {
                    isOpenRegistrationForm = true;
                    mSignButton.setEnabled(false);
                    mRegistration.setText(getBaseContext().getResources().getString(R.string.SAVE_NEW_USER));
                    mRegistrationForm.setVisibility(View.VISIBLE);
                } else {
                    showProgressBar(true);
//                registration();
                    mPresenter.registerNewUser(mEmail.getText().toString(), mPassword.getText().toString());
                }
            }
        });
    }

    @Override
    public void showProgressBar(boolean isShow) {
        if (isShow) {
            mProgresBar.setVisibility(View.VISIBLE);
        } else {
            mProgresBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void success() {
        Intent intent = new Intent(this, SplashActivity.class);
        setResult(RESULT_OK, intent);
        Toast.makeText(this, "Success login", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void error(int message) {
        String messageInfo;
        switch (message) {
            case INVALID_PASSWORD_OR_EMAIL:
                messageInfo = getBaseContext().getResources().getString(R.string.INVALID_PASSWORD_OR_EMAIL);
                break;
            default:
                messageInfo = getBaseContext().getResources().getString(R.string.MISTEKE);
        }
        Toast.makeText(this, messageInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isValidEmailAndPassword() {
        return isEmailValid(mEmail.getText().toString()) && isValidPassword(mPassword.getText().toString());
    }

    @Override
    public boolean isValidRegistrationForm() {
        return isValidEmailAndPassword() && isValidTextInput(mUserName.getText().toString())
                && isValidTextInput(mUserSrname.getText().toString())
                && isValidAge(mUserAge.getText().toString())
                && isUserMale != null;
    }

    @Override
    public void showMistake() {
        mUserAge.requestFocus();
        mPassword.requestFocus();
        mPassword.requestFocus();
        mUserName.requestFocus();
        mUserSrname.requestFocus();
        mEmail.requestFocus();
        if (isUserMale == null) {
            mRadioGroup.setBackgroundColor(getBaseContext().getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public UserModel getNewUserInformation() {
        UserModel newUser = new UserModel();
        newUser.setEmail(mEmail.getText().toString());
        newUser.setPassword(mPassword.getText().toString());
        newUser.setUserName(mUserName.getText().toString());
        newUser.setSurname(mUserSrname.getText().toString());
        newUser.setAge(Integer.parseInt(mUserAge.getText().toString()));
        newUser.setMale(isUserMale);
        return newUser;
    }

    private boolean isValidPassword(String password) {
        return password.length() > ConstantUtil.MIN_NUMBER_IN_PASSWORD;
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidAge(String age) {
        if (age.equals("")) {
            return false;
        }
        int userAge = Integer.parseInt(age);
        return userAge < ConstantUtil.MAX_OLD_VALUE && userAge > ConstantUtil.MIN_OLD_VALUE;
    }

    private boolean isValidTextInput(String text) {
        return text.length() > 1 && text.matches("[a-zA-Z ]+");
    }
}
