package com.app.ssoft.panache.View;

import android.content.Intent;
import android.net.Uri;
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

import com.app.ssoft.panache.R;
import com.app.ssoft.panache.Utils.Utils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int RC_SIGN_IN = 1;
    private TextView signin_subheading_tv;
    private TextView login_option_tv;
    private TextView email_option_tv;
    private TextView facebook_btn;
    private TextView google_btn;
    private TextView new_user_tv;
    private TextView signin_toolbar;
    private Button sign_in_btn;
    private EditText email_id_et;
    private EditText password_et;
    private FirebaseAuth auth;
    private ImageView backButton;
    private String TAG = "SignIn Activity";
    private GoogleApiClient mGoogleApiClient;
    private String idToken;
    private String name;
    private String email;
    private Uri photoUri;
    private String photo;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private LoginButton loginButton;
    private TextView forgot_pwd_tv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        backButton = findViewById(R.id.back_button);
        signin_subheading_tv = findViewById(R.id.signin_subheading_tv);
        login_option_tv = findViewById(R.id.login_option_tv);
        email_option_tv = findViewById(R.id.email_option_tv);
        facebook_btn = findViewById(R.id.facebook_btn);
        google_btn = findViewById(R.id.google_btn);
        new_user_tv = findViewById(R.id.new_user_tv);
        signin_toolbar= findViewById(R.id.signin_toolbar);
        sign_in_btn = findViewById(R.id.sign_in_btn);
        email_id_et = findViewById(R.id.email_id_et);
        password_et = findViewById(R.id.password_et);
        forgot_pwd_tv = findViewById(R.id.forgot_pwd_tv);
        progressBar = findViewById(R.id.progressBar);
        new_user_tv.setOnClickListener(this);
        sign_in_btn.setOnClickListener(this);
        google_btn.setOnClickListener(this);
        backButton.setOnClickListener(this);
        facebook_btn.setOnClickListener(this);
        forgot_pwd_tv.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });
        Utils.setTextFont(this,"Roboto-Bold.ttf",signin_toolbar);
        Utils.setTextFont(this,"Roboto-Bold.ttf",facebook_btn);
        Utils.setTextFont(this,"Roboto-Bold.ttf",google_btn);
        Utils.setTextFont(this,"Roboto-Regular.ttf",signin_subheading_tv);
        Utils.setTextFont(this,"Roboto-Light.ttf",login_option_tv);
        Utils.setTextFont(this,"Roboto-Light.ttf",email_option_tv);
        Utils.setTextFont(this,"Roboto-Regular.ttf",new_user_tv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_button:
                super.onBackPressed();
                break;
            case R.id.new_user_tv:
                Intent intent = new Intent(this,SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.sign_in_btn:
                String email = email_id_et.getText().toString();
                final String password = password_et.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                sign_in_btn.setVisibility(View.GONE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
//                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    sign_in_btn.setVisibility(View.VISIBLE);
                                    // there was an error
                                    Toast.makeText(SignInActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                                } else {
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    progressBar.setVisibility(View.GONE);
                                    sign_in_btn.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                break;
            case R.id.google_btn:
                // Configure sign-in to request the user's ID, email address, and basic
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
                signInWithGoogle();
                break;
            case R.id.facebook_btn:
                loginButton.performClick();
                break;
            case R.id.forgot_pwd_tv:
                Intent forgotPwdIntent = new Intent(this,ForgotPasswordActivity.class);
                startActivity(forgotPwdIntent);
                break;

        }
    }


    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
// Google Sign In was successful, save Token and a state then authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                idToken = account.getIdToken();
                name = account.getDisplayName();
                email = account.getEmail();
                photoUri = account.getPhotoUrl();
                photo = photoUri.toString();
//sharedPrefManager.saveIsLoggedIn(mContext, true);
                AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
                firebaseAuthWithGoogle(account);

            } else {
// Google Sign In failed
                Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT)
                        .show();
            }
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }


    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }
                });
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
