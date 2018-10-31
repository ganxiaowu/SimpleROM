package com.bjsx.orm.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 封装jdbc查询常用操作
 */
public class JDBUtils {

    /**
     * 给sql设参
     * @param ps    预编译sql对象
     * @param params    参数
     */
    public static void handleParams(PreparedStatement ps,Object[] params){

        if(params != null ){
            for(int i=0;i<params.length;i++){
                try {
                    ps.setObject(1+i,params[i]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
