package com.huyproduct.diary;

public class constraint
{
    String authorName;
    String urlImage;
    String time;
    String date;
    String title;
    String content;
    String key;

    public constraint(String authorName, String urlImage, String time, String date, String title, String content, String key)
    {
        this.authorName = authorName;
        this.urlImage = urlImage;
        this.time = time;
        this.date = date;
        this.title = title;
        this.content = content;
        this.key = key;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



}
