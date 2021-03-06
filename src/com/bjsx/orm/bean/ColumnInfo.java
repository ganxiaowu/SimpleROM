package com.bjsx.orm.bean;

import com.bjsx.orm.core.TypeConvertor;

/**
 * 封装表中一个字段
 * Created by Administrator on 2018/10/9.
 */

public class ColumnInfo {

    TypeConvertor typeConvertor;


    public ColumnInfo(String name, String dataType, int keyType) {
        this.name = name;
        this.dataType = dataType;
        this.keyType = keyType;
    }

    public ColumnInfo() {
    }

    private String name;
    private String dataType;
    private int keyType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }
}
