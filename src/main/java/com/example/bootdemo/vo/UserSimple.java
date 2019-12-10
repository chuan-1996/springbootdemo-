package com.example.bootdemo.vo;

public class UserSimple {
    private String username;

    private String img;

    public UserSimple(String username, String img) {
        this.username = username;
        this.img = img;
    }

    public UserSimple() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

}
