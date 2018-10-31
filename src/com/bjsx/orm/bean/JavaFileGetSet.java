package com.bjsx.orm.bean;

/**
 * 封装java属性和 get set 方法
 */
public class JavaFileGetSet {
    private String fielInfo;
    private String getFieldInfo;
    private String setInfo;

    public JavaFileGetSet(String fielInfo, String getFieldInfo, String setInfo) {
        this.fielInfo = fielInfo;
        this.getFieldInfo = getFieldInfo;
        this.setInfo = setInfo;
    }

    public JavaFileGetSet() {
    }

    public String getFielInfo() {
        return fielInfo;
    }

    public void setFielInfo(String fielInfo) {
        this.fielInfo = fielInfo;
    }

    public String getGetFieldInfo() {
        return getFieldInfo;
    }

    public void setGetFieldInfo(String getFieldInfo) {
        this.getFieldInfo = getFieldInfo;
    }

    public String getSetInfo() {
        return setInfo;
    }

    public void setSetInfo(String setInfo) {
        this.setInfo = setInfo;
    }

    //复写toString
    @Override
    public String toString(){
        System.out.println(fielInfo);
        System.out.println(getFieldInfo);
        System.out.println(setInfo);
        return super.toString();
    }
}
