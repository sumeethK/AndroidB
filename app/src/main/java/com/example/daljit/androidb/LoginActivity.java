package com.example.daljit.androidb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidb.util.Encoder;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.net.URL;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 0;

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    private Profile pendingUpdateForUser;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView info;
    private ImageView dp;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        initializeFaceBookSDKElement();
        initializeUserProfile();
       // setUserData();
    }

    private void setUserData() {
        SharedPreferences myPrefs = this.getSharedPreferences("user_session", Context.MODE_PRIVATE);
        info.setText(myPrefs.getString("user_fullName", ""));
        dp.setImageBitmap(Encoder.decodeBase64(myPrefs.getString("user_dp", "")));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initializeFaceBookSDKElement() {

        callbackManager = CallbackManager.Factory.create();
        if (AccessToken.getCurrentAccessToken() == null) {
            waitForFacebookSdk();
        } else {
            init();
        }
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_birthday");
        info = (TextView) findViewById(R.id.login_result);
        dp = (ImageView) findViewById(R.id.dp);
        if(pendingUpdateForUser !=null){
            info.setText(pendingUpdateForUser.getName()
            + "BD:" +pendingUpdateForUser.describeContents()
            );
            dp.setImageBitmap(getUserPic(pendingUpdateForUser.getId()));
            Toast.makeText(this,pendingUpdateForUser.getName(),Toast.LENGTH_LONG).show();
        }
       // info.setMovementMethod(new ScrollingMovementMethod());
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                saveUserSession(pendingUpdateForUser,loginResult.getAccessToken().toString());
                Profile.fetchProfileForCurrentAccessToken();
                setProfile(Profile.getCurrentProfile());
                info.setText(pendingUpdateForUser.getFirstName() + " " + pendingUpdateForUser.getLastName()
                );
                Log.i("Facebook","ID "+pendingUpdateForUser.getId());
              dp.setImageBitmap(getUserPic(pendingUpdateForUser.getId()));

                // dp.setImageURI(pendingUpdateForUser.getProfilePictureUri(200,200));
               // dp.setImageDrawable();


            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });
    }

    private void initializeUserProfile(){

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
               if(currentProfile!=null)
                setProfile(currentProfile);
                else setProfile(oldProfile);
            }
        };
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                Profile.fetchProfileForCurrentAccessToken();
                setProfile(Profile.getCurrentProfile());
            }
        };

        Profile.fetchProfileForCurrentAccessToken();
    }

    private void setProfile(Profile profile) {
            pendingUpdateForUser = profile;
    }

    public Bitmap getUserPic(String userID) {
            String imageURL;
            Bitmap bitmap = null;
            imageURL = "https://graph.facebook.com/"+userID+"/picture?type=large";
            try {
                enableStrictMode();
                bitmap = BitmapFactory.decodeStream(new URL(imageURL).openConnection().getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
    }


    private void saveUserSession(Profile profile,String acessToken){
        SharedPreferences myPrefs = this.getSharedPreferences("user_session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        String imageURL = "http://graph.facebook.com/"+profile.getId()+"/picture?type=small";
        Bitmap dp =getUserPic(profile.getId());
        editor.putString("user_acessToken", acessToken);
        editor.putString("user_fullName", profile.getName());
        editor.putString("user_id", profile.getId());
        editor.putString("user_imageUri", imageURL);
        editor.putString("user_dp", Encoder.encodeTobase64(dp));
        editor.commit();
    }

    private void waitForFacebookSdk() {
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                int tries = 0;
                while (tries < 3) {
                    if (AccessToken.getCurrentAccessToken() == null) {
                        tries++;
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        return null;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object result) {
                init();
            }
        };
        asyncTask.execute();
    }

    private void init() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null || accessToken.isExpired()) {
            // do work
        } else {
            Profile.fetchProfileForCurrentAccessToken();
            setProfile(Profile.getCurrentProfile());
            // do some other work
        }
    }


    public void enableStrictMode()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    /*private void getKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.daljit.androidb", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }*/
}

