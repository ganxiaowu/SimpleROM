package com.bjsx.orm.core;

import com.bjsx.orm.bean.ColumnInfo;
import com.bjsx.orm.bean.TableInfo;
import com.bjsx.orm.pool.DBConnPool;
import com.bjsx.orm.utils.JDBUtils;
import com.bjsx.orm.utils.ReflectUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 负责查询
 * @author ganwu
 */
public abstract class Query implements Cloneable,Serializable{

    /**
     * 采用模板方法模式将JDBC操作封装成模板，便于重用
     * @param sql
     * @param params
     * @param clazz 要封装的Java类
     * @param back  回调方法
     * @return
     */
    public Object excuteQueryTemplate(String sql,Object[] params,Class clazz,CallBack back){
        Connection conn = DBManage.getConn();
        //从连接池获取连接
        //Connection conn = DBConnPool.getConnection();
        ResultSet rs;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            JDBUtils.handleParams(ps,params);
            rs = ps.executeQuery();
            return back.doExecute(conn,ps,rs);
           /* ResultSetMetaData metaData = rs.getMetaData();
            //多行
            while(rs.next()){
                if(list ==null){
                    list = new ArrayList();
                }
                Object rowObj = clazz.newInstance();
                //多列
                for (int i=0;i<metaData.getColumnCount();i++) {
                    String colunmName = metaData.getColumnLabel(i + 1);
                    Object colunmValue = rs.getObject(i + 1);
                    //调用rowObj对象的setUsername方法，将colunmval值设置进去
                    ReflectUtils.invokeSet(rowObj, colunmName, colunmValue);
                }
                list.add(rowObj);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBManage.close(ps,conn);
        }

        return null;
    }


    /**
     *  查询执行一个DML语句
     *  @param sql sql 语句
     *  @param params 参数
     *  @return 执行sql语句后影响的行数
     */
    public int executDml(String sql, Object[] params){
        Connection conn = DBManage.getConn();
        int count = 0;
        PreparedStatement ps = null;
        try {
        ps = conn.prepareStatement(sql);
        JDBUtils.handleParams(ps,params);

        count = ps.executeUpdate();
        } catch (Exception e) {
        e.printStackTrace();
        }finally {
        DBManage.close(ps, conn);
        }
        return count;
    };
    /**
     *  将一个对象存储到数据库中
     *  @param object 需要存储的对象
     */
    public void insert(Object object){
        //obj   insert int 表名 (...,) values (？？)
        Class c = object.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        //String sql = "insert into " + tableInfo.getTname() + "(" + ") values (" + ")";
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("insert into "+tableInfo.getTname()+" (");
        Field[] fs = c.getDeclaredFields();
        int countNotNullField = 0;
        for (Field f : fs) {
        String fieldName = f.getName();
        Object fieldValue = ReflectUtils.invokeGet(object,fieldName);
        if(fieldValue!=null){
        countNotNullField++;
        sql.append(fieldName + ",");
        params.add(fieldValue);
        }
        }
        sql.setCharAt(sql.length()-1,')');
        sql.append(" values (");
        for(int i=0;i<countNotNullField;i++){
        sql.append("?,");
        }
        sql.setCharAt(sql.length()-1,')');
        executDml(sql.toString(), params.toArray());
    }
    /**
     *
     *  @param clazz 和表对应的类的class对象
     *  @param id 主键值
     */
    public void delete(Class clazz, Object id) {
        TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPrikey();
        String sql = "delete from " + tableInfo.getTname() + " where " + onlyPriKey.getName() + "=?";
        executDml(sql, new Object[]{id});
    }

    /**
     *  通过对象删除数据
     * @param object
     */
    public void delete(Object object) {
        delete(object.getClass(), ReflectUtils.invokeGet(object));
    }

    /**
     *  更新对象记录，并且只更新指定字段
     *  @param object 所需更新对象
     *  @param fileNames 需要更新属性列表
     *  @return 执行sql语句后影响的行数
     */
    public int update(Object object, String[] fileNames) {
        //只修改有改动的数据
        //obj   update 表名 set uname=?,pwd=? where id=?
        Class c = object.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPrikey();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("update "+tableInfo.getTname()+" set ");
        for(String f:fileNames){
            Object fieldValue = ReflectUtils.invokeGet(object,f);
            if(fieldValue!=null){
                sql.append(f + "=?,");
                params.add(fieldValue);
            }
        }
        sql.setCharAt(sql.length()-1,' ');
        sql.append("where ");
        sql.append(onlyPriKey.getName() + "=?");
        params.add(ReflectUtils.invokeGet(object,onlyPriKey.getName()));
        return executDml(sql.toString(),params.toArray());
    }

    /**
     *  查询多行记录
     *  @param sql sql 语句
     *  @param clazz 封装数据的javabean类的Class对象
     *  @param params sql参数
     *  @return 查询结果
     */
    public List queryRows(String sql,final Class clazz, Object[] params) {
       return  (List)excuteQueryTemplate(sql, params, clazz, new CallBack() {
            @Override
            public Object doExecute(Connection coon,PreparedStatement ps, ResultSet rs) {
                List list = null;
                try {
                    ResultSetMetaData metaData = rs.getMetaData();
                    //多行
                    while(rs.next()){
                        if(list == null){
                            list = new ArrayList();
                        }
                        Object rowObj = clazz.newInstance();
                        //多列
                        for (int i=0;i<metaData.getColumnCount();i++) {
                            String colunmName = metaData.getColumnLabel(i + 1);
                            Object colunmValue = rs.getObject(i + 1);
                            //调用rowObj对象的setUsername方法，将colunmval值设置进去
                            ReflectUtils.invokeSet(rowObj, colunmName, colunmValue);
                        }
                        list.add(rowObj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return list;
            }
        });

      /*  Connection conn = DBManage.getConn();
        ResultSet rs;
        PreparedStatement ps = null;
        List list = null;
       */

    }
    /**
     *  查询一行记录
     *  @param sql sql 语句
     *  @param clazz 封装数据的javabean类的Class对象
     *  @param params sql参数
     *  @return 查询结果
     */
    public Object queryUniqueRows(String sql,final Class clazz, Object[] params) {
        List list = queryRows(sql, clazz, params);
        return list != null && list.size()>0 ? list.get(0) :null;
    }
    /**
     *  查询返回一个值
     *  @param sql sql 语句
     *  @param params sql参数
     *  @return 查询结果
     */
    public Object queryValue(String sql, Object[] params) {
        return  excuteQueryTemplate(sql, params, null, new CallBack() {

            @Override
            public Object doExecute(Connection coon,PreparedStatement ps, ResultSet rs) {
                Object value = null;
                try {
                    //多行
                    while(rs.next()){
                        value = rs.getObject(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return value;
            }
        });

       /* Connection conn = DBManage.getConn();
        ResultSet rs;
        PreparedStatement ps = null;
        Object value = null;
        try {
        ps = conn.prepareStatement(sql);
        JDBUtils.handleParams(ps,params);
        rs = ps.executeQuery();
        //多行
        while(rs.next()){
        value = rs.getObject(1);
        }
        } catch (Exception e) {
        e.printStackTrace();
        }finally {
        DBManage.close(ps,conn);
        }
        return value;*/
    }
    /**
     *  查询返回一个数字
     *  @param sql sql 语句
     *  @param params sql参数
     *  @return 查询结果
     */
    public Number queryNumber(String sql, Object[] params) {
        return (Number)queryValue(sql,params);
    }

    /**
     * 分页查询
     * @param pageNum 第几页
     * @param size  每页显示条数
     * @return
     */
    public abstract Object qureyPagenate(int pageNum, int size);

    @Override
    protected Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

}
