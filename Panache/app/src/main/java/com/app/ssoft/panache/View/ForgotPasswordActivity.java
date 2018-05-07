package com.app.ssoft.panache.View;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ssoft.panache.R;
import com.app.ssoft.panache.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_emailId;
    private Button send_btn;
    private FirebaseAuth auth;
    private TextView resetPwd_toolbar;
    private ImageView backButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        resetPwd_toolbar = findViewById(R.id.resetPwd_toolbar);
        et_emailId = findViewById(R.id.et_emailId);
        send_btn = findViewById(R.id.send_btn);
        backButton = findViewById(R.id.back_button);
        send_btn.setOnClickListener(this);
        backButton.setOnClickListener(this);
        Utils.setTextFont(this,"Roboto-Bold.ttf",resetPwd_toolbar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                super.onBackPressed();
                break;
            case R.id.send_btn:
                String email = et_emailId.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                send_btn.setVisibility(View.GONE);

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    send_btn.setVisibility(View.VISIBLE);
                                    finish();
                                } else {
                                    Toast.makeText(ForgotPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    send_btn.setVisibility(View.VISIBLE);
                                }

//                                progressBar.setVisibility(View.GONE);
                            }
                        });

        }


    }

}

