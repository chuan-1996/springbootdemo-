package com.example.bootdemo.vo;

public class LogVO {

    private String time;

    private String description;

    public LogVO(String time, String description) {
        this.time = time;
        this.description = description;
    }

    public LogVO() {
        super();
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}
