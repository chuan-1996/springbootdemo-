package com.example.bootdemo.entity;

public class User{
    private Integer id;

    private String username;

    private String passwd;

    private String salt;

    private String role;

    private String img;

    public User(Integer id, String username, String passwd, String salt, String role, String img) {
        this.id = id;
        this.username = username;
        this.passwd = passwd;
        this.salt = salt;
        this.role = role;
        this.img = img;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }
}
