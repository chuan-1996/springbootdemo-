package com.example.bootdemo.vo;

public class UserVO {
    private Integer id;

    private String username;

    private String realname;

    private String role;

    private String img;

    public UserVO(Integer id, String username, String realname, String role, String img) {
        this.id = id;
        this.username = username;
        this.realname = realname;
        this.role = role;
        this.img = img;
    }

    public UserVO() {
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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
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
