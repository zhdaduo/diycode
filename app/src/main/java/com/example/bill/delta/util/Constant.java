package com.example.bill.delta.util;

/**
 * some constant of api.
 */

public class Constant {

  public static final String KEYSTORE_KEY_ALIAS = "DiyCode";
  public static final String VALUE_CLIENT_ID = " ";
  public static final String VALUE_CLIENT_SECRET =
      "05fa1031291be95cf40163b60c0cdd4c70c9b62c4b5852b003f0608757db4dbc";
  public static final String VALUE_GRANT_TYPE_PASSWORD = "password";
  public static final String VALUE_GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
  public static final String KEY_TOKEN = "Authorization";
  public static final String VALUE_TOKEN_PREFIX = "Bearer ";

  public static String VALUE_TOKEN = "";

  public static class Token {
    public static final String SHARED_PREFERENCES_NAME = "sign";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String TOKEN_TYPE = "token_type";
    public static final String EXPIRES_IN = "expires_in";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String CREATED_AT = "created_at";
  }

  public static class User {
    public static final String LOGIN = "login";
    public static final String AVATAR_URL = "avatar_url";
    public static final String EMAIL = "email";
  }
}
