package com.example.entity;

/**
 * @Auther: youMeng
 * @Date: 2025/4/18 - 04 - 18 - 21:45
 * @Description: com.example.entity
 * @version: 1.0
 * 图书信息
 */
public class Book {
    private Integer id;
    private String img;
    private String name;
    private String price;
    private String author;
    private Integer num;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
