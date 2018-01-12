package com.example.alpha.reader_materialdesign.Domain;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * Created by Alpha on 2018/1/6.
 */

public class SerializableMap implements Serializable {
    private TreeMap<Double, String> map;

    public TreeMap<Double, String> getMap() {
        return map;
    }

    public void setMap(TreeMap<Double, String> map) {
        this.map = map;
    }
}
