package com.example.entity;

/**
 * @Auther: youMeng
 * @Date: 2025/4/18 - 04 - 18 - 21:45
 * @Description: com.example.entity
 * @version: 1.0
 * 系统公告信息
 */
public class Notice {
    private Integer id;
    private String title;
    private String content;
    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
