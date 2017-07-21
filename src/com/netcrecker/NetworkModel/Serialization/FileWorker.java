package com.netcrecker.NetworkModel.Serialization;

import java.io.File;


public class FileWorker {

    public void createDir(String dirName, String path){
        new File(path + dirName).mkdir();
    }

    public void deleteDir(String dirName, String path){
        new File(path + dirName).delete();
    }

    public boolean checkAvailability(String dirName, String path){
        return new File(path + dirName).exists();
    }

}
