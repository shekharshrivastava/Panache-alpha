

package com.app.ssoft.panache.View;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ssoft.panache.Controller.UserRegistrationController;
import com.app.ssoft.panache.Model.UserRegistrationModel;
import com.app.ssoft.panache.R;
import com.app.ssoft.panache.Utils.DBHelper;
import com.app.ssoft.panache.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView signup_toolbar;
    private CountryCodePicker ccp;
    private EditText email_id_et;
    private EditText password_et;
    private EditText full_name_et;
    private EditText mob_num_et;
    private RadioRealButtonGroup genderRadioGroup;
    private RadioRealButton genderMaleRB;
    private RadioRealButton genderFemaleRB;
    private Button sign_up_btn;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ImageView backButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        backButton = findViewById(R.id.back_button);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        signup_toolbar = findViewById(R.id.signup_toolbar);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference(UserRegistrationModel.registration);
        Utils.setTextFont(this, "Roboto-Bold.ttf", signup_toolbar);

        email_id_et = findViewById(R.id.email_id_et);
        password_et = findViewById(R.id.password_et);
        full_name_et = findViewById(R.id.full_name_et);
        mob_num_et = findViewById(R.id.mob_num_et);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        genderMaleRB = findViewById(R.id.genderMaleRB);
        genderFemaleRB = findViewById(R.id.genderFemaleRB);
        sign_up_btn = findViewById(R.id.sign_up_btn);
        sign_up_btn.setOnClickListener(this);
        backButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                super.onBackPressed();
                break;
            case R.id.sign_up_btn:
                final String userName = full_name_et.getText().toString();
                final String emailId = email_id_et.getText().toString().trim();
                final String mobileNumber = mob_num_et.getText().toString().trim();
                final String password = password_et.getText().toString();

                if (TextUtils.isEmpty(emailId)) {
                    Toast.makeText(this, "Please enter email id", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(this, "Please enter full name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(mobileNumber)) {
                    Toast.makeText(this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }else {

                    Intent intent = new Intent(this, OTPActivity.class);
                    intent.putExtra("userName", userName);
                    intent.putExtra("emailId", emailId);
                    intent.putExtra("mobileNumber", mobileNumber);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }


       /*         PhoneAuthProvider.getInstance().verifyPhoneNumber(
                       "+91" + mobileNumber,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        this,               // Activity (for callback binding)
                        mCallbacks);*/

              /*  mAuth.createUserWithEmailAndPassword(emailId, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    UserRegistrationController.addUserData(mDatabase, userName, emailId, mobileNumber, "Male");
                                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
*/

        }
       /* mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }
        };
*/
    }

}
