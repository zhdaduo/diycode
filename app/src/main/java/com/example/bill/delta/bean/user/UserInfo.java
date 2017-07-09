package com.example.bill.delta.bean.user;

import com.google.gson.annotations.SerializedName;

public class UserInfo {

  @SerializedName("id") private int id;
  @SerializedName("login") private String login;
  @SerializedName("name") private String name;
  @SerializedName("avatar_url") private String avatarUrl;
  private boolean followed;

  public boolean isFollowed() {
    return followed;
  }

  public void setFollowed(boolean followed) {
    this.followed = followed;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvatarUrl() {
    return avatarUrl.replace("large_avatar", "avatar");
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  @Override
  public String toString() {
    return "UserInfo{"
        + "id="
        + id
        + ", login='"
        + login
        + '\''
        + ", name='"
        + name
        + '\''
        + ", avatarUrl='"
        + avatarUrl
        + '\''
        + '}';
  }
}
