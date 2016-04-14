package com.trendtechnology.notes.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.Date;

/**
 * A {@link Note} with it's data.
 *
 * @author Alexey Nesterov
 * @version 1.00 7 Apr 2016
 */
public class Note extends BaseObservable {

    private int id;
    private String title;
    private String text;
    private Date creationDate;
    private Date changeDate;
    private String imageName;

    public Note() {
    }

    @Bindable
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(com.trendtechnology.notes.BR.id);
    }

    @Bindable
    public String getTitle() {
        return this.title;
    }

    public void setTitile(String title) {
        this.title = title;
        notifyPropertyChanged(com.trendtechnology.notes.BR.title);
    }

    @Bindable
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(com.trendtechnology.notes.BR.text);
    }

    @Bindable
    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        notifyPropertyChanged(com.trendtechnology.notes.BR.creationDate);
    }

    @Bindable
    public Date getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
        notifyPropertyChanged(com.trendtechnology.notes.BR.changeDate);
    }

    @Bindable
    public String getImageName() {
        return this.imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
        notifyPropertyChanged(com.trendtechnology.notes.BR.imageName);
    }

    @Override
    public String toString() {
        return "Note:{" +
                "id:'" + this.id + "', " +
                "title:'" + this.title + "', " +
                "text:'" + this.text + "', " +
                "creationDate:'" + this.creationDate.toString() + "', " +
                "changeDate:'" + this.changeDate.toString() + "', " +
                "imageName:'" + this.imageName + "'" +
                "}";
    }

}
