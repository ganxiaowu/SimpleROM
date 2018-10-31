package com.bjsx.orm.core;

import com.bjsx.orm.bean.ColumnInfo;
import com.bjsx.orm.bean.TableInfo;
import com.bjsx.orm.utils.JavaFielUtiles;
import com.bjsx.orm.utils.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 负责管理数据库所有表结构和类结构关系，并可以根据表结构生成类机构
 */
public class TableContext {
    private TableContext() {}
    /**
     * 表名为key,表信息对象
     */
    public static Map<String, TableInfo> tables = new HashMap<>();

    /**
     * 将po的class对象和表信息关联起来，便于重用
     */
    public static Map<Class, TableInfo> poClassTableMap = new HashMap<>();

    static{
        try {
            //初始化获取表信息
            Connection con = DBManage.getConn();
            DatabaseMetaData dbmb = con.getMetaData();

            ResultSet tableRet = dbmb.getTables(null, "%", "%", new String[]{"Table"});
            while(tableRet.next()){
                String tableName = (String)tableRet.getObject("TABLE_NAME");
                TableInfo ti = new TableInfo(tableName, new HashMap<String, ColumnInfo>(), new ArrayList<ColumnInfo>());
                tables.put(tableName, ti);
                //查找表中所有字段
                ResultSet set = dbmb.getColumns(null, "%", tableName, "%");
                while(set.next()){
                    ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"),set.getString("TYPE_NAME"),0);
                    ti.getColumns().put(set.getString("COLUMN_NAME"), ci);
                }

                ResultSet set2 = dbmb.getColumns(null, "%", tableName, "%");
                //获取主键
                while(set2.next()){
                    ColumnInfo ci2 = ti.getColumns().get(set2.getObject("COLUMN_NAME"));
                    ci2.setKeyType(1);
                    ti.getPrikey().add(ci2);
                }

                if(ti.getPrikey().size()>0){
                    ti.setOnlyPrikey(ti.getPrikey().get(0));
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        //更新po类结构
        updateJavaPOFile();
        loadPOTalbe();

    }

    /**
     * 根据表结构，更新配置po包下java类
     * 实现表结构到java类转换
     */
    public static void updateJavaPOFile(){
        Map<String,TableInfo> talbles  = TableContext.tables;
        for (TableInfo t : talbles.values()) {
            JavaFielUtiles.createJavaPOFile(t, new MySqlConvertor());
        }


    }

    /**
     * 加载po包下的类
     */
    public static void loadPOTalbe(){

        for(TableInfo tableInfo:tables.values()){
            Class c = null;
            try {
                c = Class.forName(DBManage.getConf().getPoPackage() + "." + StringUtils.fistChar2Upper(tableInfo.getTname()));
                poClassTableMap.put(c, tableInfo);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }


    }

    public static void main(String[] args) {
        TableContext c = new TableContext();
    }

}
