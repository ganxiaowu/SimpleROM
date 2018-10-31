package com.bjsx.orm.utils;

import com.bjsx.orm.bean.ColumnInfo;
import com.bjsx.orm.bean.Configuration;
import com.bjsx.orm.bean.JavaFileGetSet;
import com.bjsx.orm.bean.TableInfo;
import com.bjsx.orm.core.DBManage;
import com.bjsx.orm.core.MySqlConvertor;
import com.bjsx.orm.core.TableContext;
import com.bjsx.orm.core.TypeConvertor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/9.
 */
public class JavaFielUtiles {
    /**
     * 根据表信息生成java类源代码
     *
     * @param tableInfo 表信息
     * @param convertor 数据类型转换器
     * @return java类型源代码
     */
    public static String creatJavaSrc(TableInfo tableInfo, TypeConvertor convertor) {
        StringBuilder src = new StringBuilder();
        List<JavaFileGetSet> javaFileGetSets = new ArrayList<>();

        //获取表
        Map<String, ColumnInfo> columns = tableInfo.getColumns();
        for (ColumnInfo c : columns.values()) {
            javaFileGetSets.add(creatFieldGetSetSRC(c, convertor));
        }
        src.append("package " + DBManage.getConf().getPoPackage()+";\n\n");
        src.append("import java.sql.*;\n");
        src.append("import java.util.*;\n\n");
        src.append("public class " + StringUtils.fistChar2Upper(tableInfo.getTname()) + "{\n\n");
        for(JavaFileGetSet f:javaFileGetSets){
            src.append(f.getFielInfo());
            src.append(f.getGetFieldInfo());
            src.append(f.getSetInfo());
        }
        src.append("}\n");
        return src.toString();
    }


    /**
     *  根据字段信息生成java属性信息：如vachar -username-->private String username;已结get/set方法
     *  @param colunm 字段信息
     *  @param convertor 类型转换器
     *  @return java属性和get/set方法源码
     *
     * */
    public static JavaFileGetSet creatFieldGetSetSRC(ColumnInfo colunm, TypeConvertor convertor){

        JavaFileGetSet jfgs = new JavaFileGetSet();
        String javaFiledType = convertor.datebaseType2JavaType(colunm.getDataType());
        jfgs.setFielInfo("\tprivate "+javaFiledType+" "+colunm.getName()+";\n" );
        //public String getUsername(){return userName}
        StringBuilder getSrc = new StringBuilder();
        getSrc.append("\tpublic "+javaFiledType+" get"+StringUtils.fistChar2Upper(colunm.getName())+"(){\n");
        getSrc.append("\t\treturn "+colunm.getName()+";\n");
        getSrc.append("\t}\n");

        jfgs.setGetFieldInfo(getSrc.toString());
        //set方法
        StringBuilder setSrc = new StringBuilder();
        setSrc.append("\tpublic void set"+StringUtils.fistChar2Upper(colunm.getName())+"(");
        setSrc.append(javaFiledType+" "+colunm.getName()+"){\n");
        setSrc.append("\t\tthis."+colunm.getName()+"="+ colunm.getName()+";\n");
        setSrc.append("\t}\n");
        jfgs.setSetInfo(setSrc.toString());
        return jfgs;
    }

    public static void createJavaPOFile(TableInfo tableInfo, TypeConvertor convertor){
        String javaSrc = creatJavaSrc(tableInfo, convertor);
        Configuration conf = DBManage.getConf();

        String url = conf.getSrcPath()+"\\"+conf.getPoPackage().replaceAll("\\.","\\\\")+"\\"+StringUtils.fistChar2Upper(tableInfo.getTname())+".java";
        File fil = new File(url);
        if(fil.exists()){
            fil.mkdirs();
        }
        //字符流写入文件
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(fil));
            bw.write(javaSrc);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }





    public static void main(String[] args) {
        /*ColumnInfo ci = new ColumnInfo("userName", "vachar", 0);
        JavaFileGetSet f = creatFieldGetSetSRC(ci, new MySqlConvertor());
        System.out.println(f.toString());*/

       /* Map<String,TableInfo> talbles  = TableContext.tables;
        TableInfo table = talbles.get("music_info");
        System.out.println(creatJavaSrc(table,new MySqlConvertor()));*/

        Map<String,TableInfo> talbles  = TableContext.tables;
        TableInfo table = talbles.get("music_info");
        createJavaPOFile(table, new MySqlConvertor());

    }







}
