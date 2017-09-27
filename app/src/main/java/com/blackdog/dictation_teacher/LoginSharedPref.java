package com.blackdog.dictation_teacher;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DH on 2017-09-27.
 */

public class LoginSharedPref {

    public void saveLoginInfo(Context context, String login_id, String password) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                "loginInfo", Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("login_id", login_id);
        editor.putString("password", password);
        editor.commit();
    }

    public void deleteLoginInfo(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                "loginInfo", Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("login_id", "");
        editor.putString("password", "");
        editor.commit();
    }

    public String getLoginId(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                "loginInfo", Context.MODE_PRIVATE
        );

        return sharedPref.getString("login_id", "");
    }
    public String getPassword(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                "loginInfo", Context.MODE_PRIVATE
        );

        return sharedPref.getString("password", "");
    }



}
