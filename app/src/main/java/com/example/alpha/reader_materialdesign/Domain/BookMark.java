package com.example.alpha.reader_materialdesign.Domain;

import org.litepal.crud.DataSupport;

/**
 * Created by Alpha on 2017/12/24.
 */

public class BookMark extends DataSupport{
    private int id;
    private String markname;
    private String bookName;
    private Double location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLocation() {
        return location;
    }

    public void setLocation(Double location) {
        this.location = location;
    }

    public String getMarkname() {
        return markname;
    }

    public void setMarkname(String markname) {
        this.markname = markname;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
