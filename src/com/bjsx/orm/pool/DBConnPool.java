package com.bjsx.orm.pool;

import com.bjsx.orm.core.DBManage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/29.
 */
public class DBConnPool {
    private static List<Connection> pool;
    private static final int POOL_MAX_SIZE = DBManage.getConf().getPOOL_MAX_SIZE();
    private static final int POOL_MIN_SIZE = DBManage.getConf().getPOOL_MIN_SIZE();

    public void initPool(){
        if(pool==null){
            pool = new ArrayList();
        }
        System.out.println(POOL_MAX_SIZE);
        while(pool.size()<POOL_MIN_SIZE){
            pool.add(DBManage.creatConn());
        }
        System.out.println("初始化连接池，连接数："+pool.size());

    }

    /**
     * 从连接池获取连接（线程同步 synchronized）
     * @return
     */
    public static synchronized Connection getConnection(){
        int last_index = pool.size() - 1;
        Connection conn = pool.get(last_index);
        pool.remove(last_index);
        return conn;
    }

    /**
     * 将连接放回连接池
     * @param conn
     */
    public static synchronized void closePool(Connection conn){
        if(pool.size()>=POOL_MAX_SIZE){
            try {
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            pool.add(conn);
        }
    }

    //构造方法初始化
    public DBConnPool(){
        initPool();
    }
}
