package com.example.user.notepad.realm;

import io.realm.RealmObject;

/**
 * Created by user on 23.09.2017.
 */

public class GetterSetter extends RealmObject {
    private String title;
    private String text;
    private String dateTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
