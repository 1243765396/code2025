package com.example.entity;

/**
 * @Auther: youMeng
 * @Date: 2025/3/7 - 03 - 07 - 16:45
 * @Description: com.example.entity
 * @version: 1.0
 * 旅游攻略信息
 */
public class Introduction extends Account{
    private Integer id;
    private String img;
    private String title;
    private String content;
    private String time;
    private Integer categoryId;
    //不是数据库里的字段,是数据库里category_id关联category表,查询出的title字段
    private String categoryTitle;
    private Integer userId;
    //不是数据库里的字段,是数据库里user_id关联user表,查询出的name字段
    private String userName;
    private String userAvatar;
    private String description;
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
