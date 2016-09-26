package com.metalight.xword.utils;

/**
 * Created by willy on 2016/5/16.
 */
public class Config {
    public static String getServerAddress(){
        return  "10.0.2.2:8088";
    }

    public static int getDocLastEditPageIndex(int docId){
        return 0;
    }

    public  static int getBatchFetchPageNumber(){
        return 1;
    }
}
