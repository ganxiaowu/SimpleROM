package com.bjsx.orm.core;

/**
 * 创建Query对象工厂类
 * Created by Administrator on 2018/10/8.
 */
public class QueryFactory {
    //克隆模式 初始化创建
    private static Query prototypeObj;
    static {
        try {
            Class c = Class.forName(DBManage.getConf().getQueryClass());
            prototypeObj = (Query)c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //构造器
    private QueryFactory(){}
    public static Query creatQuery(){
        try {
            return (Query) prototypeObj.clone();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
