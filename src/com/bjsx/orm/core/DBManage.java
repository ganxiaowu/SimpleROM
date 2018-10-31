package com.bjsx.orm.core;

import com.bjsx.orm.bean.Configuration;
import com.bjsx.orm.bean.TableInfo;
import com.bjsx.orm.pool.DBConnPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

/**
 * 根据配置信息，维持连接对象管理
 *
 */
public class DBManage {
    /**
     * 配置文件信息
     */
    private static Configuration conf;
    /**
     * 连接池对象
     */
    private static DBConnPool pool;

    /**
     * 静态代码块，启动执行一次
     */
    static {
        Properties pros = new Properties();
        conf = new Configuration();
        //加载配置文件
        try {
            pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置配置信息
        conf.setDriver(pros.getProperty("driver"));
        conf.setPoPackage(pros.getProperty("poPackage"));
        conf.setPwd(pros.getProperty("pwd"));
        conf.setSrcPath(pros.getProperty("srcPath"));
        conf.setUser(pros.getProperty("user"));
        conf.setUrl(pros.getProperty("url"));
        conf.setUsingDB(pros.getProperty("usingDB"));
        conf.setQueryClass(pros.getProperty("queryClass"));
        conf.setPOOL_MAX_SIZE(Integer.parseInt(pros.getProperty("POOL_MAX_SIZE")));
        conf.setPOOL_MIN_SIZE(Integer.parseInt(pros.getProperty("POOL_MIN_SIZE")));
        // 加载TableContext
        System.out.println(TableContext.class);
    }
    /**
     * 维持连接对象
     */
    public static Connection getConn(){
        if(pool==null){
            pool = new DBConnPool();
        }
    //直接建立连接，后期增加连接池提高效率
        return DBConnPool.getConnection();
        /*try {
            Class.forName(conf.getDriver());
            return DriverManager.getConnection((conf.getUrl()), conf.getUser(), conf.getPwd());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/
    }

    /**
     * 返回configuration对象
     */
    public static Configuration getConf(){
        return conf;
    }

    public static void close(Statement ps,Connection conn){
        try {
            if(ps!=null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn!=null){
                DBConnPool.closePool(conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 维持连接对象
     */
    public static Connection creatConn(){
        try {
            Class.forName(conf.getDriver());
            return DriverManager.getConnection((conf.getUrl()), conf.getUser(), conf.getPwd());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        Map<String, TableInfo> tables = TableContext.tables;
    }


}
