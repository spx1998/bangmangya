package com.xiaoyuanbang.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchUtil {
    public static List<String> processContent(String content) {
        List<String> stringList;
        int i=0;
        content=content.trim();
        String[] stringArray =content.split(" ");

        if(stringArray.length>=5){
            stringList = new ArrayList<>(Arrays.asList(stringArray).subList(0, 5));
        }else {
            stringList = new ArrayList<>(Arrays.asList(stringArray));
            while (stringList.size()<5){
                stringList.add(" ");
            }
        }
        return stringList;
    }
}
