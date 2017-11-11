package org.badhan.r64.core;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.badhan.r64.activity.auth.LoginActivity;
import org.badhan.r64.entity.User;

public class Auth {
    private static final String AUTH_PREFERENCES = "AUTH_PREFERENCES";
    private static final String AUTH_PREFERENCES_TOKEN = "AUTH_PREFERENCES_TOKEN";

    private final Context context;
    private final SharedPreferences preferences;

    private User user;
    private String authToken;

    public Auth(Context context) {
        this.context = context;
        user = new User();
        user.setDisplayName("Borhan Chowdhury");

        preferences = context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        authToken = preferences.getString(AUTH_PREFERENCES_TOKEN, null);
    }


    public User getUser() {
        return user;
    }

    public String getAuthToken(){
        return authToken;
    }

    public boolean hasAuthToken(){
        return authToken != null && !authToken.isEmpty();
    }

    public void setAuthToken(String authToken){
        this.authToken = authToken;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AUTH_PREFERENCES_TOKEN, authToken);
        editor.commit();
    }

    public void logout(){
        setAuthToken(null);

        //go to login activity
        Intent loginIntent = new Intent(context, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(loginIntent);
    }
}
