package com.bjsx.orm.core;

/**
 * Created by Administrator on 2018/10/8.
 */
public interface TypeConvertor {

    /**
     * 数据类型转为java类型
     * @param columnType
     * @return
     */
    public String datebaseType2JavaType(String columnType);

    /**
     * java类型数据转为数据库类型
     * @param javaType
     * @return
     */
    public String javaType2databaseType(String javaType);
}
