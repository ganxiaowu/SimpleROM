package com.bjsx.orm.core;

/**
 * Created by Administrator on 2018/10/12.
 */
public class MySqlConvertor implements TypeConvertor {
    @Override
    public String datebaseType2JavaType(String columnType) {
        //更加 columnType类型转换
        if("varchar".equalsIgnoreCase(columnType)||"char".equalsIgnoreCase(columnType)){
            return "String";
        }else if("int".equalsIgnoreCase(columnType)
                ||"tinyint".equalsIgnoreCase(columnType)
                ||"smallint".equalsIgnoreCase(columnType)
                ||"mediumint".equalsIgnoreCase(columnType)
                ||"integar".equalsIgnoreCase(columnType)
                ){
            return "Integer";
        }else if("bigint".equalsIgnoreCase(columnType)){
            return "Long";
        }else if("double".equalsIgnoreCase(columnType)||"float".equalsIgnoreCase(columnType)){
            return "Double";
        }else if("clob".equalsIgnoreCase(columnType)){
            return "java.sql.CLob";
        }else if("blob".equalsIgnoreCase(columnType)){
            return "java.sql.BLob";
        }else if("date".equalsIgnoreCase(columnType)){
            return "java.sql.Date";
        }else if("timetamp".equalsIgnoreCase(columnType)){
            return "java.sql.Timestamp";
        }

        return null;
    }

    @Override
    public String javaType2databaseType(String javaType) {
        return null;
    }
}
