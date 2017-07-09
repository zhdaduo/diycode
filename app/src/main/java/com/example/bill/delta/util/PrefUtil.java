package com.example.bill.delta.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.example.bill.delta.bean.user.Token;
import com.example.bill.delta.bean.user.UserDetailInfo;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * util to storage preference.
 */
public class PrefUtil {
    private static volatile PrefUtil prefUtil;
    private SharedPreferences mSharedPreferences;
    private Editor mEditor;

    private PrefUtil(Context context, String name) {
        mSharedPreferences =
            context.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static PrefUtil getInstance(Context context, String name) {
        if (prefUtil == null) {
            synchronized (PrefUtil.class) {
                if (prefUtil == null) {
                    prefUtil = new PrefUtil(context, name);
                }
            }
        }
        return prefUtil;
    }

    /**
     * storage login Token
     */
    public static void saveToken(Context context, Token token) {
        Constant.VALUE_TOKEN = token.getAccessToken();
        try {
            token.setAccessToken(
                KeyStoreHelper.encrypt(Constant.KEYSTORE_KEY_ALIAS, token.getAccessToken()));
            token.setTokenType(
                KeyStoreHelper.encrypt(Constant.KEYSTORE_KEY_ALIAS, token.getTokenType()));
            token.setRefreshToken(
                KeyStoreHelper.encrypt(Constant.KEYSTORE_KEY_ALIAS, token.getRefreshToken()));
        } catch (InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | UnrecoverableEntryException | KeyStoreException | IOException | NoSuchPaddingException | CertificateException e) {
            e.printStackTrace();
            Constant.VALUE_TOKEN = "";
            return;
        }
        PrefUtil prefUtil = PrefUtil.getInstance(context, Constant.Token.SHARED_PREFERENCES_NAME);
        prefUtil.putString(Constant.Token.ACCESS_TOKEN, token.getAccessToken());
        prefUtil.putString(Constant.Token.TOKEN_TYPE, token.getTokenType());
        prefUtil.putInt(Constant.Token.EXPIRES_IN, token.getExpiresIn());
        prefUtil.putString(Constant.Token.REFRESH_TOKEN, token.getRefreshToken());
        prefUtil.putInt(Constant.Token.CREATED_AT, token.getCreatedAt());
    }

    /**
     * get login Token
     */
    public static Token getToken(Context context) {
        PrefUtil prefUtil = PrefUtil.getInstance(context, Constant.Token.SHARED_PREFERENCES_NAME);
        Token token = new Token();
        token.setAccessToken(prefUtil.getString(Constant.Token.ACCESS_TOKEN, ""));
        token.setTokenType(prefUtil.getString(Constant.Token.TOKEN_TYPE, ""));
        token.setExpiresIn(prefUtil.getInt(Constant.Token.EXPIRES_IN, 0));
        token.setRefreshToken(prefUtil.getString(Constant.Token.REFRESH_TOKEN, ""));
        token.setCreatedAt(prefUtil.getInt(Constant.Token.CREATED_AT, 0));
        try {
            token.setAccessToken(
                KeyStoreHelper.decrypt(Constant.KEYSTORE_KEY_ALIAS, token.getAccessToken()));
            token.setTokenType(
                KeyStoreHelper.decrypt(Constant.KEYSTORE_KEY_ALIAS, token.getTokenType()));
            token.setRefreshToken(
                KeyStoreHelper.decrypt(Constant.KEYSTORE_KEY_ALIAS, token.getRefreshToken()));
        } catch (InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | UnrecoverableEntryException | KeyStoreException | IOException | NoSuchPaddingException | CertificateException e) {
            e.printStackTrace();
        }
        Constant.VALUE_TOKEN = token.getAccessToken();
        return token;
    }

    /**
     * storage login info
     */
    public static void saveMe(Context context, UserDetailInfo userDetailInfo) {
        PrefUtil prefUtil = PrefUtil.getInstance(context, Constant.Token.SHARED_PREFERENCES_NAME);
        prefUtil.putString(Constant.User.LOGIN, userDetailInfo.getLogin());
        prefUtil.putString(Constant.User.AVATAR_URL, userDetailInfo.getAvatarUrl());
        prefUtil.putString(Constant.User.EMAIL, userDetailInfo.getEmail());
    }

    /**
     * get login info
     */
    public static UserDetailInfo getMe(Context context) {
        PrefUtil prefUtil = PrefUtil.getInstance(context, Constant.Token.SHARED_PREFERENCES_NAME);
        UserDetailInfo userDetailInfo = new UserDetailInfo();
        userDetailInfo.setLogin(prefUtil.getString(Constant.User.LOGIN, ""));
        userDetailInfo.setAvatarUrl(prefUtil.getString(Constant.User.AVATAR_URL, ""));
        userDetailInfo.setEmail(prefUtil.getString(Constant.User.EMAIL, ""));
        return userDetailInfo;
    }

    /**
     * clear login info
     */
    public static void clearMe(Context context) {
        PrefUtil prefUtil = PrefUtil.getInstance(context, Constant.Token.SHARED_PREFERENCES_NAME);
        // User
        prefUtil.putString(Constant.User.LOGIN, "");
        prefUtil.putString(Constant.User.AVATAR_URL, "");
        prefUtil.putString(Constant.User.EMAIL, "");

        // Token
        prefUtil.putString(Constant.Token.ACCESS_TOKEN, "");
        prefUtil.putString(Constant.Token.TOKEN_TYPE, "");
        prefUtil.putInt(Constant.Token.EXPIRES_IN, -1);
        prefUtil.putString(Constant.Token.REFRESH_TOKEN, "");
        prefUtil.putInt(Constant.Token.CREATED_AT, -1);
        Constant.VALUE_TOKEN = "";
    }

    public SharedPreferences getSP() {
        return mSharedPreferences;
    }

    public Editor getEditor() {
        return mEditor;
    }

    /**
     * put(Long)
     */
    public void putLong(String key, long value) {
        mEditor.putLong(key, value).apply();
    }

    /**
     * put(Int)
     */
    public void putInt(String key, int value) {
        mEditor.putInt(key, value).apply();
    }

    /**
     * put(String)
     */
    public void putString(String key, String value) {
        mEditor.putString(key, value).apply();
    }

    /**
     * put(boolean)
     */
    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value).apply();
    }

    /**
     * get(Long)
     */
    public long getLong(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    /**
     * get(int)
     */
    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    /**
     * get(boolean)
     */
    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    /**
     * get(String)
     */
    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    /**
     * clear all
     */
    public void clear() {
        mEditor.clear().apply();
    }

    /**
     * clear the point one
     */
    public void remove(String key) {
        mEditor.remove(key).apply();
    }
}