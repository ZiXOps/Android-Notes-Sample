package com.trendtechnology.notes.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A {@link Note} with it's data.
 *
 * @author Alexey Nesterov
 * @version 1.00 7 Apr 2016
 */
public class Note extends BaseObservable {

    private String title;
    private String text;
    private Date creationDate;
    private Date changeDate;
    private Uri imageUri;

    public Note(){
    }

    public Note(String title, String text, Date creationDate, Date changeDate) {
        this.title = title;
        this.text = text;
        this.creationDate = creationDate;
        this.changeDate = changeDate;
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
    public Uri getImageUri() {
        return this.imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
        notifyPropertyChanged(com.trendtechnology.notes.BR.imageUri);
    }

    public boolean hasAttachments() {
        return true;
    }

    @Override
    public String toString() {
        return "Note:{" +
                "title:'" + this.title + "', " +
                "text:'" + this.text + "', " +
                "creationDate:'" + this.creationDate.toString() + "', " +
                "changeDate:'" + this.changeDate.toString() + "', " +
                "imageUri:'" + "  " + "'" +
                "}";
    }

}
