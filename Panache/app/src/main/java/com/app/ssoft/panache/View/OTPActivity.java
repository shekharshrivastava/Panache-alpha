package com.app.ssoft.panache.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ssoft.panache.Controller.UserRegistrationController;
import com.app.ssoft.panache.Model.UserRegistrationModel;
import com.app.ssoft.panache.R;
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

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mobile_tv, otp_et_title, otp_error_tv, resend_code_tv, timer_tv;
    private Button submit_btn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mDatabase;
    private String emailID;
    private String userName;
    private String mobileNumber;
    private String password;
    private TextView otp_toolbar;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String TAG = "OTPActivity";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        progressBar = findViewById(R.id.progressBar);
        TextView OTPTitleTv = findViewById(R.id.OTPTitleTv);
        mobile_tv = findViewById(R.id.mobile_tv);
        otp_et_title = findViewById(R.id.otp_et_title);
        otp_error_tv = findViewById(R.id.otp_error_tv);
        resend_code_tv = findViewById(R.id.resend_code_tv);
        timer_tv = findViewById(R.id.timer_tv);
        submit_btn = findViewById(R.id.submit_btn);
        otp_toolbar = findViewById(R.id.otp_toolbar);
        submit_btn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference(UserRegistrationModel.registration);

        Utils.setTextFont(this,"Roboto-Bold.ttf",otp_toolbar);
        Utils.setTextFont(this, "Roboto-Regular.ttf", OTPTitleTv);
        Utils.setTextFont(this, "Roboto-Medium.ttf", mobile_tv);
        Utils.setTextFont(this, "Roboto-Light.ttf", otp_et_title);
        Utils.setTextFont(this, "Roboto-Regular.ttf", timer_tv);
        Utils.setTextFont(this, "Roboto-Regular.ttf", otp_error_tv);
        Utils.setTextFont(this, "Roboto-Bold.ttf", resend_code_tv);

        Intent intent = getIntent();
        emailID = intent.getStringExtra("emailId");
        userName = intent.getStringExtra("userName");
        mobileNumber = intent.getStringExtra("mobileNumber");
        password = intent.getStringExtra("password");

        mobile_tv.setText("+91 - "+mobileNumber);


        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished / 1000>=10) {
                    timer_tv.setText("00:" + millisUntilFinished / 1000);
                }else{
                    timer_tv.setText("00:0" + millisUntilFinished / 1000);
                }
                resend_code_tv.setTextColor(Color.GRAY);
                resend_code_tv.setEnabled(false);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                timer_tv.setText("00:00");
                resend_code_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                resend_code_tv.setEnabled(false);
            }

        }.start();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
//                Log.d(TAG, "onVerificationCompleted:" + credential);

//                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // ...
            }
        };
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+mobileNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                progressBar.setVisibility(View.VISIBLE);
                submit_btn.setVisibility(View.GONE);
                mAuth.createUserWithEmailAndPassword(emailID, password)
                        .addOnCompleteListener(OTPActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(OTPActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    submit_btn.setVisibility(View.VISIBLE);
                                    Toast.makeText(OTPActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    UserRegistrationController.addUserData(mDatabase, userName, emailID, mobileNumber, "Male");
                                    Intent intent = new Intent(OTPActivity.this,MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    progressBar.setVisibility(View.GONE);
                                    submit_btn.setVisibility(View.VISIBLE);
                                }
                            }
                        });
        }
    }
}
