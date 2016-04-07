package com.trendtechnology.notes.model;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * A {@link Note} with it's data.
 *
 * @author Alexey Nesterov
 * @version 1.00 7 Apr 2016
 */
public class Note {

    private String name;
    private String text;
    private List<File> attachmentList;
    private Date creationDate;
    private Date changeDate;

    public void setName(String noteName) {
        this.name = noteName;
    }

    public String getName() {
        return this.name;
    }

    public void setText(String noteText) {
        this.text = noteText;
    }

    public String getText() {
        return this.text;
    }

    public void setCreationDate(Date noteCreationDate) {
        this.creationDate = noteCreationDate;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setChangeDate(Date noteChangeDate) {
        this.changeDate = noteChangeDate;
    }

    public Date getChangeDate() {
        return this.changeDate;
    }

}
