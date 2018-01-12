package com.example.alpha.reader_materialdesign.Utils;

import android.os.Environment;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alpha on 2017/12/4.
 */

public class Util {
    public static List<File> getSuffixFile(ArrayList<File> files, String filePath, String suffere){
        File f = new File(filePath);
        if(!f.exists()){
            return null;
        }
        File[] subFiles = f.listFiles();
        if(subFiles == null)
            return null;
        for(File subFile:subFiles){
            if(subFile.isFile() && subFile.getName().endsWith(suffere)){
                files.add(subFile);
            }else if(subFile.isDirectory()){
                getSuffixFile(files, subFile.getAbsolutePath(), suffere);
            }else{

            }
        }
        return files;
    }
}
