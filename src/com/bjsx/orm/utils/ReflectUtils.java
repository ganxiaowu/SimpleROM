package com.bjsx.orm.utils;

import com.bjsx.orm.bean.ColumnInfo;
import com.bjsx.orm.bean.TableInfo;
import com.bjsx.orm.core.TableContext;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 封装了反射常用操作
 */
public class ReflectUtils {

    public static Object invokeGet(Object object) {
        //反射方式
        Class c = object.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPrikey();
        try {
            Method m =c.getMethod("get"+StringUtils.fistChar2Upper(onlyPriKey.getName().toString()), null);
            return m.invoke(object, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object invokeGet(Object object,String cloumn) {
        //反射方式
        Class c = object.getClass();
        try {
            Method m =c.getMethod("get"+StringUtils.fistChar2Upper(cloumn), null);
            return m.invoke(object, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object invokeSet(Object object,String colunmName,Object colunmValue) {
        //反射方式
        try {
            Method m = object.getClass().getDeclaredMethod("set" + StringUtils.fistChar2Upper(colunmName),colunmValue.getClass());
            return m.invoke(object, colunmValue);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
