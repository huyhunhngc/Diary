package com.huyproduct.diary;

public class postModule
{
    String time;
    String date;
    String title;
    String content;



    private postModule() {}

    public postModule(String date, String time, String title, String content)
    {
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
