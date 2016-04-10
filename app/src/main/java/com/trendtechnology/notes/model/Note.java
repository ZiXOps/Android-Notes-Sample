package com.trendtechnology.notes.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A {@link Note} with it's data.
 *
 * @author Alexey Nesterov
 * @version 1.00 7 Apr 2016
 */
public class Note {

    private String title;
    private String text;
    private Date creationDate;
    private Date changeDate;
    private List<Attachment> attachmentList;

    public Note(String title, String text, Date creationDate, Date changeDate) {
        this.title = title;
        this.text = text;
        this.creationDate = creationDate;
        this.changeDate = changeDate;
        this.attachmentList = new ArrayList<>();
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public Date getChangeDate() {
        return this.changeDate;
    }

    public boolean hasAttachments() {
        return this.attachmentList.size() > 0;
    }

    @Override
    public String toString() {
        return "Note:{" +
                "title:'" + this.title + "', " +
                "text:'" + this.text + "', " +
                "creationDate:'" + this.creationDate.toString() + "', " +
                "changeDate:'" + this.changeDate.toString() + "'" +
                "}";
    }

}
