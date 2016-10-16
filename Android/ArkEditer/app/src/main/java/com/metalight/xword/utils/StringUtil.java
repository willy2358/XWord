package com.metalight.xword.utils;

/**
 * Created by willy on 2016/10/16.
 */
public class StringUtil {
    public static String getUniqueSubString(String testString, String partSubStr, int startIdx){
        int end = startIdx + partSubStr.length() - 1;
        while(true){
            String testPart = testString.substring(startIdx, end);
            int idx1 = testString.indexOf(testPart);
            int idx2 = testString.lastIndexOf(testPart);
            if (idx1 == idx2){
                return testPart;
            }

            end++;
            if (end >= testString.length()){
                break;
            }

        }

        return null;
    }
}
