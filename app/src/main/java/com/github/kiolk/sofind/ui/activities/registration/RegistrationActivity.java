package com.github.kiolk.sofind.ui.activities.registration;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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

/**
 * View component of registration process
 */
public class RegistrationActivity extends AppCompatActivity implements RegistrationView {

    private RegistrationPresenter mRegistrationPresenter;
    private Button mSignButton;
    private Button mRegistration;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mUserName;
    private EditText mUserSurname;
    private EditText mUserAge;
    private Boolean isUserMale;
    private RadioGroup mRadioGroup;
    private ProgressBar mProgressBar;
    private boolean isOpenRegistrationForm;
    private LinearLayout mRegistrationForm;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mRegistrationPresenter = new RegistrationPresenterImpl(this);
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
        mUserSurname = findViewById(R.id.surname_edit_text);
        mUserAge = findViewById(R.id.age_edit_text);
        mEmail = findViewById(R.id.email_edit_text);
        mPassword = findViewById(R.id.password_edit_text);
        mRadioGroup = findViewById(R.id.sex_radio_group);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(final RadioGroup group, final int checkedId) {
                isUserMale = checkedId == R.id.male_radio_button;
                mRadioGroup.setBackgroundColor(getBaseContext().getResources().getColor(R.color.TRANSPARENT_COLOR));
            }
        });
        final View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(final View v, final boolean hasFocus) {
                switch (v.getId()) {
                    case R.id.email_edit_text:
                        if (!isEmailValid(mEmail.getText().toString())) {
                            mEmail.setError(getBaseContext().getResources().getString(R.string.NOT_CORRECT_INPUT_EMAIL));
                        }
                        break;
                    case R.id.password_edit_text:
                        if (!isValidPassword(mPassword.getText().toString())) {
                            mPassword.setError(getBaseContext().getResources().getString(R.string.SHORT_PASSWORD) + " " + ConstantUtil.MIN_NUMBER_IN_PASSWORD);
                        }
                        break;
                    case R.id.user_name_edit_text:
                        if (!isValidTextInput(mUserName.getText().toString())) {
                            mUserName.setError(getBaseContext().getResources().getString(R.string.INPUT_MIN_TWO_SYMBOL) + " " + ConstantUtil.MIN_NUMBER_IN_NAME);
                        }
                        break;
                    case R.id.surname_edit_text:
                        if (!isValidTextInput(mUserSurname.getText().toString())) {
                            mUserSurname.setError(getBaseContext().getResources().getString(R.string.INPUT_MIN_TWO_SYMBOL) + " " + ConstantUtil.MIN_NUMBER_IN_NAME);
                        }
                        break;
                    case R.id.age_edit_text:
                        if (!isValidAge(mUserAge.getText().toString())) {
                            mUserAge.setError(getBaseContext().getResources().getString(R.string.INPUT_CORRECT_AGE));
                        }
                }
            }
        };
        mEmail.setOnFocusChangeListener(focusChangeListener);
        mUserName.setOnFocusChangeListener(focusChangeListener);
        mUserSurname.setOnFocusChangeListener(focusChangeListener);
        mUserAge.setOnFocusChangeListener(focusChangeListener);
        mPassword.setOnFocusChangeListener(focusChangeListener);
        mProgressBar = findViewById(R.id.registration_progress_bar);
        mRegistration = findViewById(R.id.register_new_user);
        mRegistrationForm = findViewById(R.id.registration_form_linear_layout);
        mSignButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
//                singing();
                showProgressBar(true);
                mRegistrationPresenter.singIn(mEmail.getText().toString(), mPassword.getText().toString());
            }
        });

        mRegistration.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (!isOpenRegistrationForm) {
                    isOpenRegistrationForm = true;
                    mSignButton.setEnabled(false);
                    mRegistration.setText(getBaseContext().getResources().getString(R.string.SAVE_NEW_USER));
                    mRegistrationForm.setVisibility(View.VISIBLE);
                } else {
                    showProgressBar(true);
//                registration();
                    mRegistrationPresenter.registerNewUser(mEmail.getText().toString(), mPassword.getText().toString());
                }
            }
        });
    }

    @Override
    public void showProgressBar(final boolean isShow) {
        if (isShow) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void success() {
        final Intent intent = new Intent(this, SplashActivity.class);
        setResult(RESULT_OK, intent);
        Toast.makeText(this, R.string.SUCCESS_LOGIN, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void error(final int message) {
        final String messageInfo;
        switch (message) {
            case INVALID_PASSWORD_OR_EMAIL:
                messageInfo = getBaseContext().getResources().getString(R.string.INVALID_PASSWORD_OR_EMAIL);
                break;
            default:
                messageInfo = getBaseContext().getResources().getString(R.string.MISTAKE);
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
                && isValidTextInput(mUserSurname.getText().toString())
                && isValidAge(mUserAge.getText().toString())
                && isUserMale != null;
    }

    @Override
    public void showMistake() {
        mUserAge.requestFocus();
        mPassword.requestFocus();
        mPassword.requestFocus();
        mUserName.requestFocus();
        mUserSurname.requestFocus();
        mEmail.requestFocus();
        if (isUserMale == null) {
            mRadioGroup.setBackgroundColor(getBaseContext().getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public UserModel getNewUserInformation() {
        final UserModel newUser = new UserModel();
        newUser.setEmail(mEmail.getText().toString());
        newUser.setPassword(mPassword.getText().toString());
        newUser.setUserName(mUserName.getText().toString());
        newUser.setSurname(mUserSurname.getText().toString());
        newUser.setAge(Integer.parseInt(mUserAge.getText().toString()));
        newUser.setMale(isUserMale);
        return newUser;
    }

    private boolean isValidPassword(final CharSequence password) {
        return password.length() > ConstantUtil.MIN_NUMBER_IN_PASSWORD;
    }

    private boolean isEmailValid(final CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidAge(final String age) {
        if ("".equals(age)) {
            return false;
        }
        final int userAge = Integer.parseInt(age);
        return userAge < ConstantUtil.MAX_OLD_VALUE && userAge > ConstantUtil.MIN_OLD_VALUE;
    }

    private boolean isValidTextInput(final String text) {
        return text.length() > 1 && text.matches("[a-zA-Z ]+");
    }
}
