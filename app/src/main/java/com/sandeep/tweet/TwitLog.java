package com.sandeep.tweet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;
import com.google.gson.JsonObject;
import com.sandeep.tweet.signin.Home;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.HashMap;
import java.util.Objects;


public class TwitLog extends AppCompatActivity {



    FirebaseAuth firebaseAuth;
    String TAG = "twit";
    OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");
    String TWITTER_KEY = "ifjZZ3sHvIdk5wWIHdtaBvVfy";
    String TWITTER_SECRET = "ZI2LlIjbkLIliGUTTgENGPmXXFOxe9JIYyV0E2kkKBh63uoelI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);Twitter.initialize(this);
      //  Twitter.initialize(this);
        setContentView(R.layout.activity_twit_log);
        getSupportActionBar().hide();
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET))
                .debug(true)
                .build();
        Twitter.initialize(config);


                loginTwit();


//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {


    //   });


//        TwitterConfig config = new TwitterConfig.Builder(this)
//                .logger(new DefaultLogger(Log.DEBUG))
//                .twitterAuthConfig(new TwitterAuthConfig((R.string.CONSUMER_KEY), R.string.CONSUMER_SECRET))
//                .debug(false)
//                .build();
//        Twitter.initialize(config);


//        loginButton.setCallback(new Callback<TwitterSession>()
//
//    {
//
//
//        @Override
//        public void success (Result < TwitterSession > result) {
//        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
//        TwitterAuthToken authToken = session.getAuthToken();
//
//        String token = authToken.token;
//        String secret = authToken.secret;
//
//        loginMethod(session);
//    }
//
//        @Override
//        public void failure (TwitterException exception){
//        Toast.makeText(getApplicationContext(), "Login fail", Toast.LENGTH_LONG).show();
//    }
//    });
}
    public void loginMethod(TwitterSession twittersession){
            String userName = twittersession.getUserName();
        Intent intent = new Intent(TwitLog.this , Home.class);
        Log.d("twit", "user:" + userName);
    }


  public void loginTwit(){
      firebaseAuth = FirebaseAuth.getInstance();
      try {
          firebaseAuth
                  .startActivityForSignInWithProvider(this, provider.build())
                  .addOnSuccessListener(
                          new OnSuccessListener<AuthResult>() {
                              @Override
                              public void onSuccess(AuthResult authResult) {

                                  // User is signed in.
                                  // IdP data available in
                                  // authResult.getAdditionalUserInfo().getProfile().
                                  // The OAuth access token can also be retrieved:
                                  // authResult.getCredential().getAccessToken().
                                  // The OAuth secret can be retrieved by calling:
                                  // authResult.getCredential().getSecret().
                                  Log.d("twit", "suthREs:" + authResult.getAdditionalUserInfo().getProfile() + " " + authResult.getUser() + " " + authResult.getCredential());
                                  Intent intent = new Intent(TwitLog.this, Home.class);
                                  HashMap json = new HashMap(authResult.getAdditionalUserInfo().getProfile());
                                  Log.d("twit", "email: " + json.get("email"));
                                  intent.putExtra("email", Objects.requireNonNull(json.get("email")).toString());
                                  intent.putExtra("retweet", Objects.requireNonNull(json.get("followers_count")).toString());
                                  intent.putExtra("username", Objects.requireNonNull(json.get("screen_name")).toString());
                                  intent.putExtra("favouriteCount", Objects.requireNonNull(json.get("favourites_count")).toString());
                                  intent.putExtra("friend", Objects.requireNonNull(json.get("friends_count")).toString());
                                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                  startActivity(intent);

                              }
                          })
                  .addOnFailureListener(
                          new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                  // Handle failure.
                                  Log.d("twit", "suthREs:" + e.getMessage());
                              }
                          });
      }catch (NullPointerException ex){
          Toast.makeText(this, "Null Value from User's Profile", Toast.LENGTH_SHORT).show();
      }
  }


}
