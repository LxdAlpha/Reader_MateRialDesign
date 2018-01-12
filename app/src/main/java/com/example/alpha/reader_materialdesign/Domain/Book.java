package com.example.alpha.reader_materialdesign.Domain;

import org.litepal.crud.DataSupport;

/**
 * Created by Alpha on 2017/12/8.
 */

public class Book extends DataSupport{
    private int id; //不需自己设置自增长，litepal默认id是自增的
    private String name;
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
