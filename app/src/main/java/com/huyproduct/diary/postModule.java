package com.huyproduct.diary;

public class postModule
{
    String authorName;
    String urlImage;
    String time;
    String date;
    String title;
    String content;

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

    private postModule() {}

    public postModule(String authorName, String urlImage,String date, String time, String title, String content)
    {
        this.authorName = authorName;
        this.urlImage = urlImage;
        this.date = date;
        this.time = time;
        this.title = title;
        this.content = content;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

}
