package com.bjsx.orm.utils;

/**
 * 封装字符串常用操作
 */
public class StringUtils {
    //将目标字符串首字母大写
    public static String fistChar2Upper(String str){
        //截取第一个字母大写，截取剩余字母拼接
        return str.substring(0, 1).toUpperCase() + str.substring(1);

    }


}
